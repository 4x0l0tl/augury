package com.augury.core.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Project;

public interface ProjectRepository extends GraphRepository<Project>{
	@Query("MATCH (p:Project) RETURN p")
	public List<Project> getProjects();

	@Query("MATCH (p:Project {name:{0}}) RETURN p")
	public Project getProjectByName(String name);

	@Query("START project = node({0}) MATCH (project)-[:HAS]->(b:Branch) RETURN b.name")
	public Iterable<String> getBranchNamesForProject(Project project);
	
	@Query("MATCH (project {name:{0}})-[:HAS]->(b:Branch)<-[:COMMITTED_TO]-(c:Commit) RETURN id(b), b.name as name, count(c) as commitCount ORDER BY commitCount DESC")
	public Iterable<Map<String, Object>> getBranchesWithCommitCount(String project);
}
