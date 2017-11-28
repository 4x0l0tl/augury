package com.augury.model;

public abstract class Person extends Entity {
	String name;
	protected Person() {}
	public Person(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
}
