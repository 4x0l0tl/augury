package com.augury.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.joda.time.DateTime;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;
import org.neo4j.ogm.annotation.typeconversion.Convert;

@NodeEntity
@NoArgsConstructor(force = true, access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Setter
@Getter
public class Issue extends Entity
{
    public String REFERENCE = "ref";
    public String TYPE ="type";
	private  String issueReference; 
	private String issueType;
	private String description;
	@Convert(JodaTimeConverter.class)
	private DateTime creationDate;
	@Relationship(type="RELATED_TO")
	private Component component;
	@Relationship(type="REPORTED_BY", direction=Relationship.OUTGOING)
	private Reporter reporter;
	@Relationship(type="CURRENTLY_ASSIGNED_TO",direction=Relationship.OUTGOING)
	private Assignee currentAssignee;
	@Relationship(type="PREVIOUS_ASSIGNED_TO",direction=Relationship.OUTGOING)
	Set<Assignee> assignees = new HashSet<Assignee>();
	
	public Map<String, Object> getAsParameters()
    {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put(REFERENCE, this.issueReference);
        parameters.put(TYPE, this.issueType);
        return parameters;
    }
}
