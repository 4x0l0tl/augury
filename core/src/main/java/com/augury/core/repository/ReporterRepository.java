package com.augury.core.repository;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Reporter;

public interface ReporterRepository extends GraphRepository<Reporter>{
	@Query("MATCH (r:Reporter {name:{0}}) RETURN r ")
	public Reporter getReporterByName(String name);

}
