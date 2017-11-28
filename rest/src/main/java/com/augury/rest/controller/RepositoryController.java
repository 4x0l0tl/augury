package com.augury.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.augury.core.service.Service;
import com.augury.model.Repository;

public class RepositoryController extends Controller<Repository>{
	@Autowired
	@Override
	public Service<Repository> getService() {
		return null;
	}

}
