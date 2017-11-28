package com.augury.core.service;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import org.neo4j.ogm.session.Session;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.augury.model.Directory;
import com.augury.model.DirectoryNode;
import com.augury.model.File;

@Service
public class DirectoryNodeService {
	@Inject
	Session template;
	
	@Transactional
	public DirectoryNode getNode(String filePath) {
		DirectoryNode node = getOrCreateNode(filePath);
		String parentPath = getParentPath(node.getPathToNode());
		if(filePath.length() < 3 || parentPath.length() == 0 || parentPath.equals("/")) {
			return node;
		} 
		else {
			Directory parent = getOrCreateParentDirectory(parentPath);
			if(!parent.getChildren().contains(node)){
				parent.add(node);
				template.save(parent);
//				relationshipRepository.contains(parent, node);
			}
			
		}
		return node;
	}
	
	private Directory getOrCreateParentDirectory(String path) {
		String parentPath = getParentPath(path);
		Directory directory = getParentDirectory(path);
		
		if(parentPath.length() == 0 || parentPath.equals("/")) {
			return directory;
		}
		else {
			Directory parent = getOrCreateParentDirectory(parentPath);
			if(!parent.getChildren().contains(directory)) {
				parent.add(directory);
				template.save(parent);
//				relationshipRepository.contains(parent, directory);
			}
			
		}
		return directory;
	}
	
	private DirectoryNode getOrCreateNode(String filePath)
	{
		String [] nodes = filePath.split("/");

		Map <String,Object> parameters = new HashMap<String, Object>();
		parameters.put("path", filePath);
		parameters.put("name",  nodes[nodes.length-1]);
		
		if(filePath.endsWith("/")){
			return template.queryForObject(Directory.class, "MERGE (f:Directory:_Directory {path:{path}}) ON CREATE SET f.path = {path} , f.name = {name} ON MATCH SET f.lastUpdate = timestamp() RETURN f", parameters);
		}
		else {
			return template.queryForObject(File.class,"MERGE (f:File:_File {path:{path}}) ON CREATE SET f.path = {path} , f.name = {name} ON MATCH SET f.lastUpdate = timestamp() RETURN f", parameters);
		}
	}
	
	private String getParentPath(String filePath)
	{
		String [] nodes = filePath.split("/");
		int substringEndIndex;
		if(filePath.endsWith("/"))
			substringEndIndex= filePath.length()-nodes[nodes.length-1].length()-1;
		else
			substringEndIndex= filePath.length()-nodes[nodes.length-1].length();
		return filePath.substring(0, substringEndIndex);
	}	
	
	private Directory getParentDirectory(String path) {
		return template.queryForObject(Directory.class,"MERGE (f:Directory:_Directory {path:{path}}) ON CREATE SET f.path = {path} , f.name = {name} ON MATCH SET f.lastUpdate = timestamp() RETURN f", getQueryParameters(path));
	}
	
	private Map<String,Object> getQueryParameters(String path) {
		String [] nodes = path.split("/");
		Map <String,Object> parameters = new HashMap<String, Object>();
		parameters.put("path", path);
		if(nodes.length > 1){
			parameters.put("name", nodes[nodes.length-1]);
		}
		else {
			parameters.put("name", nodes[0]);
		}	
		return parameters;
	}
}

