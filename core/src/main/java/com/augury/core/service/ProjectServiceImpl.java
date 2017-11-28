package com.augury.core.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

import com.augury.core.repository.ProjectRepository;
import com.augury.model.Project;

@Service
public class ProjectServiceImpl extends GenericService<Project> implements ProjectService{
	@Autowired
	ProjectRepository projectRepository;

	public Project findProjectByName(String name) {
		return projectRepository.getProjectByName(name);
	}
	public Iterable<String> getBranchesForProject(Project project) {
		return (Iterable<String>) projectRepository.getBranchNamesForProject(project);
	}
	
	public Iterable<Map<String, Object>> getBranchesForProject(String project) {
		return projectRepository.getBranchesWithCommitCount(project);
	}
	@Override
	public Class<Project> getEntityType() {
		return Project.class;
	}
	@Override
	public GraphRepository<Project> getRepository() {
		return projectRepository;
	}
}
