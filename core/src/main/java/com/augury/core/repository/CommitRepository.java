package com.augury.core.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Branch;
import com.augury.model.Commit;
import com.augury.model.File;
import com.augury.model.Issue;

public interface CommitRepository extends GraphRepository<Commit>{
	@Query("START issue = node({0}) MATCH (issue)<-[:RELATED_TO]-(c:Commit)")
	public List<Commit> getCommitsRelatingToIssue(Issue issue);
	
	@Query("START file =  node({0}) MATCH (c:Commit)-[:AFFECTED]->(file) RETURN c")
	public List<Commit> getCommitsThatAffectFile(File file);
	
	@Query("MATCH (c:Commit {revision:{0}}) RETURN c")
	public Commit getCommitForRevision(String revision);

	@Query("START branch = node({0}) MATCH (c:Commit)-[:COMMITTED_TO]->(branch) RETURN c")
	public List<Commit> getCommitsForBranch(Branch ret);
	
	@Query("START branch = node({0}) MATCH (c:Commit)<-[:LATEST_COMMIT]-(branch) RETURN c")
	public Commit getLatestCommitForBranch(Branch branch);
}
