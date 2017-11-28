package com.augury.core.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Directory;
import com.augury.model.DirectoryNode;

public interface DirectoryNodeRepository extends GraphRepository<DirectoryNode>{
	@Query("START directory = node ({0}) MATCH (directory)-[:PARENT_OF]-(n) RETURN n")
	public List<DirectoryNode> getChildNode(Directory dir);

	@Query("START node = node({0}) MATCH (directory:Directory)-[:PARENT_OF]->(node) RETURN directory")
	public Directory getParentDirectory(DirectoryNode node);
}
