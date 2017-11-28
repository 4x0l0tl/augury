package com.augury.core.repository;

import java.util.Map;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.File;

public interface FileRepository extends GraphRepository<File> {
	@Query("START file = node({0}) MATCH (file)<-[:AFFECTED]-(c:Commit) RETURN count(c) as CommitCount")
	Map<String, Object> getBasicMetrics(File file);

}
