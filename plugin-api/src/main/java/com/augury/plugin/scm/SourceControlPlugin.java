package com.augury.plugin.scm;

import java.util.List;

import org.joda.time.DateTime;

import com.augury.plugin.AuguryPlugin;
/**
 * Interface used for retrieving both structural and revision information from SCM system.
 * 
 */
public interface SourceControlPlugin extends AuguryPlugin
{
	/**
	 * Returns a List of {@link LogEntry} object containing log the information of a given file between specified dates
	 * 
	 * @param url 				Target URL (file path + file name) to retrieve logs from
	 * @param from				Start date to retrieve logs from
	 * @param to				End date to stop retrieving logs from
	 * @param scmQueryOptions	Generic object containing addition SCM query options
	 * @return 
	 * 
	 * @see org.dcu.bpm.core.quer.scm.LogEntry
	 */
	public List<LogEntry> getRepoLogForRevision(String url, String revision);
	public List<LogEntry> getRepoLogBetweenDates(String url, DateTime from, DateTime to);
	/**
	 * Return a List of {@link LogEntry} object containing the entire history for every path affected below and including url path 
	 * @param url
	 * @return
	 */
	public List<LogEntry> getRepoLogs(String url);
	public List<String> getBranchList();
}
