package com.augury.rest.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.augury.core.service.FileService;
import com.augury.core.service.Service;
import com.augury.model.File;

public class FileController extends Controller<File> {
	@Autowired
	FileService fileService;
	@Override
	public Service<File> getService() {
		return fileService;
	}
	
//	@RequestMapping(value="/files/{id}/metrics)",  method = RequestMethod.GET)
//	public Map<String, Object> getBasicFileMetrics(@PathVariable Long id)
//	{
//		return fileService.getBasicFileMetrics();
//		
//	}

}
