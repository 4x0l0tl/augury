package com.augury.plugin.scm;

public class ChangedPath 
{
	private String changePath;
	private char  changedType;
	
	public ChangedPath(char cT, String cP)
	{
		changedType = cT;
		changePath = cP;
	}

	public char getChangedType() {
		return changedType;
	}

	public void setChangedType(char changedType) {
		this.changedType = changedType;
	}

	public String getChangePath() {
		return changePath;
	}

	public void setChangePath(String changePath) {
		this.changePath = changePath;
	}
}
