package com.augury.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

@RelationshipEntity(type="AFFECTED")
@Builder
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor
@Getter
public class Affected {
	private @GraphId Long id;
	private @StartNode Commit commit;
	private @EndNode DirectoryNode node;
	private String  changedType;
	private int linesAdded,linesDeleted,linesModified;
}
