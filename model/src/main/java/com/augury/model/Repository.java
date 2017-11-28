package com.augury.model;

import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.Builder;
import lombok.Getter;
import lombok.Singular;
import lombok.experimental.Tolerate;

@NodeEntity
@Getter
@Builder
public class Repository extends Entity{
	String provider;
	private String url;
	@Relationship(type="MAINTAINS",direction=Relationship.OUTGOING)
	@Singular
	Set<Project> projects;
	
	@Tolerate
	private Repository(){}
	
	public void addProject(Project project) {
		projects.add(project);
	}
}
