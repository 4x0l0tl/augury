package com.augury.model;

import java.util.HashMap;
import java.util.Map;

public class Change 
{
	private final String changePath;
	private final String  changedType;
	private int linesAdded,linesDeleted,linesModified;
	
	public Change(String cT, String cP)
	{
		changedType = cT;
		changePath = cP;
	}
	
	public Change(String changeType, String changePath, int linesAdded, int linesDeleted, int linesModified)
	{
		this(changeType,changePath);
		this.linesAdded = linesAdded;
		this.linesDeleted = linesDeleted;
		this.linesModified = linesModified;
	}
	
	public Map<String,Object> getPropertyMap(){
		Map<String,Object> changeInfo = new HashMap<String, Object>();
		changeInfo.put("type", changedType);
		changeInfo.put("linesAdded" ,linesAdded);
		changeInfo.put("linesDeleted", linesDeleted);
		changeInfo.put("linesModified", linesModified);
		return changeInfo;
	}

	public String getChangedType() {
		return changedType;
	}

	public String getChangePath() {
		return changePath;
	}

	public int getLinesAdded() {
		return linesAdded;
	}

	public void setLinesAdded(int linesAdded) {
		this.linesAdded = linesAdded;
	}

	public int getLinesDeleted() {
		return linesDeleted;
	}

	public void setLinesDeleted(int linesDeleted) {
		this.linesDeleted = linesDeleted;
	}

	public int getLinesModified() {
		return linesModified;
	}

	public void setLinesModified(int linesModified) {
		this.linesModified = linesModified;
	}
}