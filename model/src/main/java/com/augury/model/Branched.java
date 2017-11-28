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

@RelationshipEntity(type="BRANCHED_FROM")
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Getter
public class Branched {
	@GraphId
	private Long id;
	
	private final @StartNode Branch  parent;
	private final @EndNode Branch child;
	@Convert(JodaTimeConverter.class)
	private final DateTime date;	
}
