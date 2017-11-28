package com.augury.core.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Assignee;

public interface AssigneeRepository extends GraphRepository<Assignee>{
	@Query("MERGE (a:Assignee name:{0}) RETURN a")
	public Assignee getAssigneeByName(String name);

}
