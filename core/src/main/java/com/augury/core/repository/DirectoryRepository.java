package com.augury.core.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Directory;
import com.augury.model.DirectoryNode;

public interface DirectoryRepository extends GraphRepository<Directory> {
	@Query("START node = node({0}) MATCH (directory:Directory)-[:CONTAINS]->(node) RETURN directory")
	public Directory getParentDirectory(DirectoryNode node);
}
