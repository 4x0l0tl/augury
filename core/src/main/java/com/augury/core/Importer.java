package com.augury.core;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.plugin.core.OrderAwarePluginRegistry;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.augury.core.config.ConfigConstants;
import com.augury.core.repository.BranchRepository;
import com.augury.core.repository.RepoRepository;
import com.augury.core.service.AuthorService;
import com.augury.core.service.CommitService;
import com.augury.core.service.DirectoryNodeService;
import com.augury.core.service.IssueService;
import com.augury.core.service.ProjectService;
import com.augury.model.Author;
import com.augury.model.Branch;
import com.augury.model.Change;
import com.augury.model.Commit;
import com.augury.model.DirectoryNode;
import com.augury.model.Issue;
import com.augury.model.Project;
import com.augury.model.Repository;
import com.augury.plugin.PluginConstants;
import com.augury.plugin.it.IssueTrackerPlugin;
import com.augury.plugin.scm.LogEntry;
import com.augury.plugin.scm.SourceControlPlugin;

@Component
@Profile("prod")
public class Importer implements CommandLineRunner {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private SourceControlPlugin scmQuery;
	@Inject
	private BranchRepository branchRepository;
	@Inject
	RepoRepository repoRepository;
	@Inject
	ProjectService projectService;
	@Inject
	private AuthorService authorService;
	@Inject
	private CommitService commitService;
	@Inject
	private DirectoryNodeService directoryNodeService;
	@Inject
	private IssueService issueService;
	@Inject
	Environment env;
	@Inject
	Session db;
	@Inject
	@Qualifier("issueTrackerPluginRegistry")
	OrderAwarePluginRegistry<IssueTrackerPlugin, String> itPlugins;
	@Inject
	@Qualifier("sourceControlPluginRegistry")
	OrderAwarePluginRegistry<SourceControlPlugin, String> scmPlugins;

	boolean linkIssues = false;
	
	@Override
	public void run(String... args) throws Exception {
		for (String arg : args) {
			logger.debug(arg);
		}

		scmQuery = scmPlugins.getPluginFor(env.getProperty(PluginConstants.SCM_PROVIDER));
		scmQuery.initialise();
		logger.debug("SCM Implementation Selected:"+scmQuery.getClass().getName());

		String issuePrefix = env.getProperty(PluginConstants.ISSUE_TRACKING_ISSUE_PREFIX);
		if(!StringUtils.isEmpty(issuePrefix)) {
			logger.info("Issue Prefix:"+issuePrefix);
			issueService.setProjectIssueReference(issuePrefix);
			issueService.intialiseIssueTrackerPluginImplementation(
					itPlugins.getPluginFor(env.getProperty(PluginConstants.ISSUE_TRACKING_PROVIDER)));
			linkIssues = true;
			
		}
		String branch = env.getProperty(PluginConstants.SCM_BRANCH);
		String runType = env.getProperty(ConfigConstants.RUN_TYPE, "import");
		if (runType.equals(ConfigConstants.UPDATE)) {
			String revision = env.getProperty(PluginConstants.SCM_REVISION);
			updateBranchHistory(branch, revision);
		} else {
			String repository = env.getProperty(PluginConstants.SCM_URL);
			String project = env.getProperty(PluginConstants.SCM_PROJECT);

			DateTimeFormatter formatter = DateTimeFormat.forPattern(env.getProperty(ConfigConstants.DATE_FORMAT));
			DateTime from = formatter.parseDateTime(env.getProperty(ConfigConstants.FROM_DATE));
			DateTime to = formatter.parseDateTime(env.getProperty(ConfigConstants.TO_DATE));
			importRepository(repository, project, branch, from, to);
		}
	}

	@Transactional
	public void importRepository(String repo, String proj, String targetBranch, DateTime from, DateTime to) {
		String provider = env.getProperty(PluginConstants.SCM_PROVIDER);
		Repository repository = repoRepository.getRepositoryByName(repo);
		if(repository == null) {
			Project p = new Project(proj, "");
			repository = Repository.builder().provider(provider).url(repo).project(p).build();
			repoRepository.save(repository);
		}

		Project project = null;
		for(Project p : repository.getProjects()) {
			if (p.getName().equals(proj)) {
				project = p;
				break;
			}
		}
		if(project == null) {
			project = new Project(proj, "");
			repository.addProject(project);
			repoRepository.save(repository);
		}

		if (targetBranch != null) {
			List<String> branches = scmQuery.getBranchList();
			for (String branchName : branches) {
				if (branchName.equalsIgnoreCase(targetBranch) || targetBranch.equals(ConfigConstants.RUN_ALL_BRANCHES)) {
					importBranchHistory(from, to, project, branchName);
				}
			}
		} else {
			if (provider.equalsIgnoreCase("GITHUB")) {
				importBranchHistory(from, to, project, ConfigConstants.GITHUB_DEFAULT_BRANCH);
			} else if (provider.equalsIgnoreCase("SVN")) {
				importBranchHistory(from, to, project, ConfigConstants.SVN_DEFAULT_BRANCH);
			}
		}
	}

	public void updateBranchHistory(String branchName, String revision) {
		Branch branch = branchRepository.getBranchByName(branchName);
		List<LogEntry> commitEntries = scmQuery.getRepoLogForRevision(branchName, revision);
		updateBranchHistoryForCommits(branch, commitEntries);
	}

	private void importBranchHistory(DateTime from, DateTime to, Project project, String branchName) {
		Branch branch = null;
		for(Branch b : project.getBranches()) {
			if (b.getName().equalsIgnoreCase(branchName)) {
				branch = b;
			}
		}
		if(branch == null){
			branch = new Branch(branchName);
		}
		project.addBranch(branch);
		projectService.createOrUpdate(project);

		List<LogEntry> commitEntries = scmQuery.getRepoLogBetweenDates(branchName, from, to);
		updateBranchHistoryForCommits(branch, commitEntries);
	}

	@Transactional
	private void updateBranchHistoryForCommits(Branch branch, List<LogEntry> commitEntries) {
		branch = branchRepository.findOne(branch.getId());
		logger.info("Commits to process for branch " + branch.getName() + ":" + commitEntries.size());
		for (LogEntry entry : commitEntries) {
			Commit previousLatestCommit = branch.getLatestCommit();
			Author author = authorService.getAuthorByName(entry.getAuthor());
			if (author == null) {
				author = authorService.createOrUpdate(new Author(entry.getAuthor()));
			} 
			Commit commit = commitService.getCommitByRevision(entry.getRevision());
			if (commit == null) {
				commit = new Commit(entry.getRevision(), entry.getCommitDate(), entry.getMessage(), branch);
				commit.setAuthor(author);
				branch.getCommits().add(commit);
			}
			commit.setPreviousCommit(previousLatestCommit);
			branch.setLatestCommit(commit);
			author.authored(commit);
			
			if(linkIssues) {
				Issue issue = issueService.parseCommitMessageForIssue(commit.getMessage());
				if (issue != null) {
					commit.getRelatedIssue().add(issue);
				}
			}

			List<Change> changedPaths = entry.getChanges();
			for (Change change : changedPaths) {
				DirectoryNode dirNode = directoryNodeService.getNode(change.getChangePath());
				dirNode.setLastUpdate(new DateTime());
				commit.affected(dirNode, change);
			}
		}
		branchRepository.save(branch);
	}

}
