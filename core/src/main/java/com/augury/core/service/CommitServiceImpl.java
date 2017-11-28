package com.augury.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

import com.augury.core.repository.AuthorRepository;
import com.augury.core.repository.BranchRepository;
import com.augury.core.repository.CommitRepository;
import com.augury.core.repository.DirectoryNodeRepository;
import com.augury.model.Author;
import com.augury.model.Branch;
import com.augury.model.Commit;
import com.augury.model.File;
import com.augury.model.Issue;
import com.augury.plugin.scm.LogEntry;

@Service
public class CommitServiceImpl extends GenericService<Commit> implements CommitService{
	@Autowired
	CommitRepository commitRepository;
	@Autowired
	AuthorRepository authorRepository;
	@Autowired
	BranchRepository branchRepository;
	@Autowired
	DirectoryNodeRepository directoryNodeRepository;
	
	public void saveAuthor(Author author) {
		authorRepository.save(author);
	}
	
	public void saveCommit(Commit commit) {
		commitRepository.save(commit);
	}
	
	public void saveFile(File file) {
		directoryNodeRepository.save(file);
	}
	
	public void saveBranch(Branch branch) {
		branchRepository.save(branch);
	}
	
	public int getNumCommits(Branch branch) {
		return (int) branchRepository.getNumCommits(branch);
	}
	
	public List<Commit> getCommitsRelatingToIssue(Issue issue) {
		return commitRepository.getCommitsRelatingToIssue(issue);
	}
	
	public Commit getCommitForLogEntry(LogEntry entry) {
		Commit commit = new Commit(entry.getRevision(), entry.getCommitDate(), entry.getMessage(), null);
		return commit;
	}

	public Commit getLatestCommitForBranch(Branch branch) {
		return commitRepository.getLatestCommitForBranch(branch);
	}

	@Override
	public Class<Commit> getEntityType() {
		return Commit.class;
	}

	@Override
	public GraphRepository<Commit> getRepository() {
		return commitRepository;
	}

	@Override
	public Commit getCommitByRevision(String revision) {
		return commitRepository.getCommitForRevision(revision);
	}
}
