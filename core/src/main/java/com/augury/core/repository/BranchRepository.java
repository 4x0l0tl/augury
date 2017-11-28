package com.augury.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Branch;
import com.augury.model.Commit;
import com.augury.model.Project;

public interface BranchRepository extends GraphRepository<Branch>{
	@Query("START branch = node ({0}) MATCH (branch)<-[:COMMITTED_TO]-(c:Commit) RETURN count(c)")
	public long getNumCommits(Branch branch);
	
	@Query("MATCH (b:Branch {name:{0}}) RETURN b")
	public Branch getBranchByName(String name);
	
	@Query("START project = node({0}) MATCH (project)-[:CONTAINS]->(b:Branch) RETURN b")
	public List<Branch> getBranchesForProject(Project project);
	
	@Query("MATCH (project {name:{0}})-[:HAS]->(b:Branch) RETURN b")
	public List<Branch> getBranchesForProject(String project);
	
	@Query("START commit = node({0}) MATCH (branch:Branch)<-[:COMMITTED_TO]-(commit) RETURN branch")
	public Branch getBranchForCommit(Commit commit);
	
	@Query("START branch = node({0}) MATCH (branch)-[br:BRANCHED_FROM]->(:Branch) RETURN br.date")
	public Long getDateBranched(Branch branch);
	
	@Query("START branch = node({0}) MATCH (branch)-[:BRANCHED_FROM]->(parent:Branch) RETURN parent")
	public Branch getParentBranch(Branch branch);
	
	@Query("MATCH (project {name:{0}})-[:HAS]->(b:Branch)<-[:COMMITTED_TO]-(c:Commit) RETURN b, count(c) as commitCount ORDER BY commitCount DESC")
	public Iterable<Map<String, Object>> getBranchesWithCommitCount(String project);
}
