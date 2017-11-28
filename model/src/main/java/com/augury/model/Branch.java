package com.augury.model;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.Tolerate;

@NodeEntity
@Data
@EqualsAndHashCode(exclude={"commits","latestCommit"})
public class Branch extends Entity {
	@Tolerate
	private Branch(){}
	@NonNull private String name;
	
	@Relationship(type="BRANCHED_FROM")
	private Branch branchedFrom;

	@Relationship(type="LATEST_COMMIT",direction=Relationship.OUTGOING)
	Commit latestCommit;
	
	@Relationship(type="COMMITTED_TO",direction=Relationship.INCOMING)
	Set<Commit> commits = new HashSet<Commit>();	
}
