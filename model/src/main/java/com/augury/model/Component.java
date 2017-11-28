package com.augury.model;

import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label="Component")
public class Component extends Entity
{
	String name;
	String description;
	
	@SuppressWarnings("unused")
	private Component(){}
	public Component(String name, String description) 
	{
		this.name = name;
		this.description = description;
	}
}
