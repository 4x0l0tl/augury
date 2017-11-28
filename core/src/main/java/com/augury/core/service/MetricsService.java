package com.augury.core.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
	
	@Inject
	Session metrics;
	public Iterable<Map<String, Object>> getProjectMetrics(String project) {
		
		return null;
	}

	public Iterable<Map<String, Object>> getMostCommittedToFilesForWeek(String project, Long startDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("project",project);
//		params.put("date",startDate);
		return metrics.query("MATCH (project {name:{project}})-[:HAS]->(b:Branch)<-[:COMMITTED_TO]-(c:Commit)-[:AFFECTED]->(f:File) WITH f,count(c) AS commitCount RETURN id(f) as id, f.name as name,commitCount ORDER BY commitCount DESC LIMIT 25", params).queryResults();		
	}

}
