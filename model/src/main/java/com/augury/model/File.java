package com.augury.model;

import org.joda.time.DateTime;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label="File")
public class File extends DirectoryNode
{
	@SuppressWarnings("unused")
	private File(){}
	public File(String n, String p)
	{
		name = n;
		path = p;
		setLastUpdate(new DateTime());
	}
	@Override
	public String getName() 
	{
		return name;
	}

	@Override
	public String getPathToNode() 
	{
		return path;
	}
	@Override
	public String getFullPath() 
	{
		return path+"/"+name;
	}
	
	@Override
	public boolean isDirectory() 
	{
		return false;
	}
}
