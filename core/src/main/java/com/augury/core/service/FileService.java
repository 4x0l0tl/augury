package com.augury.core.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.core.repository.FileRepository;
import com.augury.model.File;

public class FileService extends GenericService<File> implements Service<File>{
	@Autowired
	FileRepository fileRepository;
	public Map<String, Object> getBasicFileMetrics(Long id) {
		File file = this.find(id);
		return fileRepository.getBasicMetrics(file);
	}
	@Override
	public Class<File> getEntityType() {
		return File.class;
	}
	@Override
	public GraphRepository<File> getRepository() {
		return fileRepository;
	}
}
