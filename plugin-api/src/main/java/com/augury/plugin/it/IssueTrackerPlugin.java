package com.augury.plugin.it;

import java.util.List;

import com.augury.model.Issue;
import com.augury.plugin.AuguryPlugin;

public interface IssueTrackerPlugin extends AuguryPlugin
{
	public String getIssueDescription(String issueReference);
	public String getIssueType(String issueReference);
	public String getIssueComponent(String issueReference);
	public Issue getIssue(String issueReference);
	public List<Issue> getIssuesForProject(String project); 
}
