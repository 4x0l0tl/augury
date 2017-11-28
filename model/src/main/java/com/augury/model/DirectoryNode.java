package com.augury.model;

import org.joda.time.DateTime;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;


/**
 * A tree node used for the representation of a hierarchical data structure. Namely a file directory hierachy tree.
 */
@NodeEntity(label = "DirectoryNode")
public abstract class DirectoryNode extends Entity
{
	protected String name;
	protected String path;
//	@GraphProperty(propertyType = Long.class)
	@Convert(JodaTimeConverter.class)
	private DateTime lastUpdate;
	protected DirectoryNode () {}
	protected DirectoryNode (String name, String path) {
		this.name = name;
		this.path = path;
		lastUpdate = new DateTime();
	}
	public String getName() 
	{
		return name;
	}

	public String getPathToNode() 
	{
		return path;
	}
	public String getFullPath() 
	{
		return path+"/"+name;
	}
	public DateTime getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(DateTime lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	public abstract boolean isDirectory();
}
