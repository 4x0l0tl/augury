package com.augury.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.joda.time.DateTime;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;
import org.neo4j.ogm.annotation.typeconversion.Convert;

@RelationshipEntity(type="ASSIGNED_TO")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
public class AssignedTo {
	private @GraphId Long id;
	private final @StartNode Issue issue;
	private final @EndNode Assignee assignee;
	
	@Convert(JodaTimeConverter.class)
	private final DateTime date;
}
