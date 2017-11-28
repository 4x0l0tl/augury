package com.augury.model;

import java.util.HashSet;
import java.util.Set;

import org.joda.time.DateTime;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import lombok.experimental.Tolerate;

@NodeEntity
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(exclude = "branch")
public class Commit extends Entity {
	@Tolerate
	private Commit(){};
	private @NonNull String revision;
	@Convert(value=JodaTimeConverter.class)
	private @NonNull DateTime commitDate;
	private @NonNull String message;
	@Relationship(type = "AUTHORED", direction=Relationship.INCOMING)
	private Author author;
	
	@Relationship(type = "COMMITTED_TO", direction=Relationship.OUTGOING)
	private @NonNull Branch branch;
	
	@Relationship(type="PREVIOUS",direction=Relationship.OUTGOING)
	Commit previousCommit;
	
	@Relationship(direction = Relationship.OUTGOING)
	private Set<Affected> affectedFile = new HashSet<Affected>();
	
	@Relationship(type="RELATING_TO",direction=Relationship.OUTGOING)
	private Set<Issue> relatedIssue = new HashSet<Issue>();
	
	public void affected(DirectoryNode node, Change change) {
		Affected affected = Affected.builder()
								.changedType(change.getChangedType())
								.linesAdded(change.getLinesAdded())
								.linesDeleted(change.getLinesDeleted())
								.linesModified(change.getLinesModified())
								.commit(this)
								.node(node)
								.build();
//		Affected affected = new Affected(this, node,change.getChangedType(),change.get);
		affectedFile.add(affected);
	}
	
	public void relatedTo(Issue issue) {
		relatedIssue.add(issue);
	}
}
