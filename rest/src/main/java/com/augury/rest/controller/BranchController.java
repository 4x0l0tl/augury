package com.augury.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.augury.core.service.BranchServiceImpl;
import com.augury.core.service.Service;
import com.augury.model.Branch;

public class BranchController extends Controller<Branch>{
	@Autowired
	BranchServiceImpl branchService;
	@Override
	public Service<Branch> getService() {
		return branchService;
	}

	@RequestMapping(value="/projects/{name}/branches/{branch}", method = RequestMethod.GET)
	public Branch getBranchByName(@PathVariable String project, @PathVariable String branch) {
		return branchService.getBranchesForProject(project).iterator().next();
	}
}
