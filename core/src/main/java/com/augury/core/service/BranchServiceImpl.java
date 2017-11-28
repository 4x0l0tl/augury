package com.augury.core.service;

import javax.inject.Inject;

import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.core.repository.BranchRepository;
import com.augury.model.Branch;

public class BranchServiceImpl extends GenericService<Branch> implements BranchService{
	@Inject private BranchRepository repository;
	public Iterable<Branch> getBranchesForProject(String project) {
		return null;
	}
	@Override
	public Class<Branch> getEntityType() {
		return Branch.class;
	}
	@Override
	public GraphRepository<Branch> getRepository() {
		return repository;
	}
}
