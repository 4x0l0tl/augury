package com.augury.model;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class Author extends Person {
	private String email;

	@SuppressWarnings("unused")
	private Author(){}
	public Author(String name) {
		super(name);
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void authored(Commit commit) {
		// TODO Auto-generated method stub
		
	}
}
