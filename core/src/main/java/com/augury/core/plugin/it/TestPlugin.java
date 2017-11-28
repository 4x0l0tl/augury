package com.augury.core.plugin.it;

import java.util.List;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.augury.model.Issue;
import com.augury.plugin.it.IssueTrackerPlugin;

@Order(999)
@Component
public class TestPlugin implements IssueTrackerPlugin {

	@Override
	public void initialise() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supports(String delimiter) {
		// TODO Auto-generated method stub
		return "Test".equalsIgnoreCase(delimiter);
	}

	@Override
	public String getIssueDescription(String issueReference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIssueType(String issueReference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getIssueComponent(String issueReference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Issue getIssue(String issueReference) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Issue> getIssuesForProject(String project) {
		// TODO Auto-generated method stub
		return null;
	}

}
