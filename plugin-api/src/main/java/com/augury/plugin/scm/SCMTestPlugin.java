package com.augury.plugin.scm;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(999)
@Component
public class SCMTestPlugin implements SourceControlPlugin{

	@Override
	public void initialise() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean supports(String arg0) {
		return "TEST".equalsIgnoreCase(arg0);
	}

	@Override
	public List<LogEntry> getRepoLogForRevision(String url, String revision) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogEntry> getRepoLogBetweenDates(String url, DateTime from, DateTime to) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<LogEntry> getRepoLogs(String url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getBranchList() {
		// TODO Auto-generated method stub
		return null;
	}

}
