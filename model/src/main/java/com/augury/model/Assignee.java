package com.augury.model;

import org.joda.time.DateTime;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.typeconversion.Convert;

@NodeEntity(label="Assignee")
public class Assignee extends Person {
	@Convert(JodaTimeConverter.class)
	DateTime assignedDate;

	@SuppressWarnings("unused")
	private Assignee () {}
	public Assignee (String name, DateTime assignedDate)
	{
		super(name);
		this.assignedDate = assignedDate;
	}
	public DateTime getAssignedDate() {
		return assignedDate;
	}

	public void setAssignedDate(DateTime assignedDate) {
		this.assignedDate = assignedDate;
	}
}
