package com.augury.core.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.augury.core.repository.IssueRepository;
import com.augury.core.repository.ReporterRepository;
import com.augury.model.Issue;
import com.augury.model.Reporter;
import com.augury.plugin.it.IssueTrackerPlugin;

@Service
public class IssueService {
	private static final String ISSUE_QUERY = "MATCH (i:Issue {issueReference:{reference}}) RETURN i";
	private static final String REFERENCE = "reference";
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject
	Session template;
	@Inject
	private IssueRepository issueRepository;
	@Inject
	private ReporterRepository reporterRepository;

	private IssueTrackerPlugin query;
	
	public static Pattern pattern;
	public static List<String> projectIssueReferences = new ArrayList<String>();
	
	public void intialiseIssueTrackerPluginImplementation(IssueTrackerPlugin plugin) {
		query = plugin;
		query.initialise();
	}
	
	@Transactional
	public Issue getIssueByReference(String issueReference){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(REFERENCE, issueReference);
		return template.queryForObject(Issue.class,ISSUE_QUERY, params);
	}

	public void setProjectIssueReference(String issueRef)
	{
		logger.debug("Adding issue prefix [" + issueRef + "] to regex");
		projectIssueReferences.add(issueRef);
		String issueRegex =".*(" + issueRef + "-\\d+).*";
		pattern = Pattern.compile(issueRegex,Pattern.CASE_INSENSITIVE);
	}
	public void addProjectIssueReference(String issueRef)
	{
		logger.debug("Adding issue prefix [" + issueRef + "] to regex");
		projectIssueReferences.add(issueRef);
		
		StringBuilder regex = new StringBuilder();
		regex.append(".*(");
		regex.append(projectIssueReferences.get(0));
		for(String ir: projectIssueReferences.subList(1, projectIssueReferences.size()))
		{
			regex.append("|");
			regex.append(ir);
		}
		regex.append("-\\d+).*");
		pattern = Pattern.compile(regex.toString(),Pattern.CASE_INSENSITIVE);
		
	}
	
	@Transactional
	public Issue parseCommitMessageForIssue(String message)
	{
		
		Matcher matcher = pattern.matcher(message);
		if(matcher.find())
		{
			String issueReference = matcher.group(1);
			logger.debug("Found issue reference:" + issueReference);
			 
			Issue issue = issueRepository.getIssueForReference(issueReference);
			if(issue == null) {
				logger.info("Issue does not exist in graph attempting to retrieve from issue tracker...");
				Issue draftIssue = null;
				try { 
					draftIssue = query.getIssue(issueReference);
				} catch (Exception e){
					logger.error(e.getMessage());
				}
				if(draftIssue != null) {
					Reporter reporter = reporterRepository.getReporterByName(draftIssue.getReporter().getName());
					if(reporter != null){
						draftIssue.setReporter(reporter);
					}		
					return issueRepository.save(draftIssue);
				}	
			}
		}
		return null;
	}
	
	public String parseCommitForIssueReference(String message) {
		Matcher matcher = pattern.matcher(message);
		if(matcher.find())
		{
			String issueReference = matcher.group(1);
			return issueReference;
		}
		return null;
	}
	
	public Issue getIssueFromIssueTracker(String issueReference) {
		try { 
			return query.getIssue(issueReference);
		} catch (Exception e){
			logger.error(e.getMessage());
		} 
		return null;
	}

	public void saveIssue(Issue issue) {
		issueRepository.save(issue);
	}
}
