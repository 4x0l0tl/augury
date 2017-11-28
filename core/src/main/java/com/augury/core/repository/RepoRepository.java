package com.augury.core.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Repository;

public interface RepoRepository extends GraphRepository<Repository>{
	
	@Query("MATCH (r:Repository {url:{0}})-[mrel:MAINTAINS]->(p:Project)-[crel:CONTAINS]->(b:Branch) RETURN r,mrel,p,crel,b")
	public Repository getRepositoryByName(String name);

}
