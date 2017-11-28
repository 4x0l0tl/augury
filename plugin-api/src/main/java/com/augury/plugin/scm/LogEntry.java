package com.augury.plugin.scm;

import java.util.List;

import org.joda.time.DateTime;

import com.augury.model.Change;

public class LogEntry
{
	private final String author,message;
	private String changeType;
	private final String revision;
	private final DateTime commitDate;
	private List<String> relatedFilesForCommit;
	private List<ChangedPath> changePaths;
	private List<Change> fileChanges;
	
	public LogEntry(String rev,DateTime d, String author,String message,List<String>rFFC)
	{
		this.revision = rev;
		this.commitDate = d;
		this.author = author;
		this.message = message;
		this.relatedFilesForCommit = rFFC;
	}

	public LogEntry(String revision, DateTime commitDate, String author, String message, List<String> relatedFilesForCommit, List<Change> changes) 
	{
		this.revision = revision;
		this.commitDate = commitDate;
		this.author = author;
		this.message = message;
		this.relatedFilesForCommit = relatedFilesForCommit;
		this.setChanges(changes);		
	}

	public String getAuthor() {
		return author;
	}

	public String getMessage() {
		return message;
	}

	public void setChangeType(String cT)
	{
		this.changeType = cT;
	}
	public String getChangeType() {
		return changeType;
	}

	public String getRevision() {
		return revision;
	}

	public DateTime getCommitDate() {
		return commitDate;
	}

	public List<String> getRelatedFilesForCommit() 
	{
		return relatedFilesForCommit;
	}
	
	public void setRelatedFilesForCommit(List<String> files)
	{
		relatedFilesForCommit = files;
	}

	public List<ChangedPath> getChangePaths() {
		return changePaths;
	}

	public void setChangePaths(List<ChangedPath> changePaths) {
		this.changePaths = changePaths;
	}

	public List<Change> getChanges() {
		return fileChanges;
	}

	public void setChanges(List<Change> fileChanges) {
		this.fileChanges = fileChanges;
	}
}
