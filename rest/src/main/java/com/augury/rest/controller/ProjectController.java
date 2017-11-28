package com.augury.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.augury.core.service.ProjectServiceImpl;
import com.augury.core.service.Service;
import com.augury.model.Project;

@RestController
public class ProjectController extends Controller<Project> {
	@Autowired
	ProjectServiceImpl projectService;
	@Override
	public Service<Project> getService() {
		return projectService;
	}
	
	@RequestMapping(value="/projects", method = RequestMethod.GET)
    public Iterable<Project> list() {
        return super.list();
    }
	
	@RequestMapping(value="/projects/{id}", method = RequestMethod.GET)
	public Project find(@PathVariable Long id) {
		return super.find(id);
	}
	
//	@RequestMapping(value="/projects/{id}/branches", method = RequestMethod.GET)
//	public Iterable<String> getBranchesForProject(@PathVariable Long id) {
//		Project project =  super.find(id);
//		return projectService.getBranchesForProject(project);
//	}
	
	@RequestMapping(value="/projects/{name}", method = RequestMethod.GET)
	public Project findByName(@PathVariable String name) {
		return projectService.findProjectByName(name);
	}

//	@RequestMapping(value="/projects/{name}/branches", method = RequestMethod.GET)
//	public Iterable<String> getBranchesForProject(@PathVariable String name) {
//		Project project =  projectService.findProjectByName(name);
//		return projectService.getBranchesForProject(project);
//	}
	
	@RequestMapping(value="/projects/{project}/branches", method = RequestMethod.GET)
	public @ResponseBody Iterable<Map<String, Object>> getBranchListForProject(@PathVariable String project){
		return projectService.getBranchesForProject(project);
	}
	
	
}
