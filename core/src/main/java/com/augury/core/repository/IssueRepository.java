package com.augury.core.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Branch;
import com.augury.model.Issue;

public interface IssueRepository extends GraphRepository<Issue>{
	
	@Query("MATCH (i:Issue {ref:{0}}) RETURN i")
	public Issue getIssueForReference(String ref);
	
	@Query("START branch = node({0}) MATCH (i:Issue)-[:RELATING_TO]-(c:Commit)-[:COMMITTED_TO]->(branch)")
	public List<Issue> getIssuesForBranch(Branch branch);

}
