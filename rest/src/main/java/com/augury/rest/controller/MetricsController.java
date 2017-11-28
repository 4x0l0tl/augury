package com.augury.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.augury.core.service.MetricsService;

@RestController
@RequestMapping(value = "/metrics")
public class MetricsController {
	
	@Autowired
	MetricsService metricsService;
//	@RequestMapping(value="/api/metrics/{project}/", method = RequestMethod.GET)
//	public @ResponseBody Iterable<Map<String, Object>> getBranchListForProject(@PathVariable String project){
//		return metricsService.getProjectMetrics(project);
//	}
	
	@RequestMapping(value="/{project}/weeklyMostCommittedToFiles/{startDate}", method = RequestMethod.GET)
	public @ResponseBody Iterable<Map<String, Object>> getWeeklyMostCommittedToFiles(@PathVariable String project, @PathVariable Long startDate){
		return metricsService.getMostCommittedToFilesForWeek(project,startDate);
	}
	
}
