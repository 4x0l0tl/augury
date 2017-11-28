package com.augury.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity(label="Reporter")
public class Reporter extends Person {

	@SuppressWarnings("unused")
	private Reporter(){}
	public Reporter(String name) {
		super(name);
	}

}
