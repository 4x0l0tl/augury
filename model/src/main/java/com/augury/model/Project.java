package com.augury.model;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.Tolerate;


@NodeEntity
@Data
public class Project extends Entity {
	@Tolerate
	private Project (){}
	private @NonNull String name;
	private @NonNull String description;
	
	@Relationship(type = "CONTAINS")
	Set<Branch> branches = new HashSet<Branch>();
	
	public void addBranch(Branch branch) {
		branches.add(branch);
	}

}
