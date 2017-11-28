package com.augury.model;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity(label="Directory")
public class Directory extends DirectoryNode 
{	
	@Relationship(type = "CONTAINS", direction = Relationship.OUTGOING)
	private Set<DirectoryNode> children = new HashSet<DirectoryNode>();;
	@SuppressWarnings("unused")
	private Directory(){}
	public Directory(String n, String p)
	{
		super(n,p);
		
	}

	@Override
	public boolean isDirectory() 
	{
		return true;
	}
	public void add(DirectoryNode entry)
	{
		if(children == null)
		{
			children = new HashSet<DirectoryNode>();
		}
		children.add(entry);
	}
	public Set<DirectoryNode> getChildren()
	{
		return children;
	}
}
