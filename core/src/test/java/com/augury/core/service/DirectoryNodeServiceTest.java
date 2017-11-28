package com.augury.core.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.augury.core.repository.BaseRepositoryTest;
import com.augury.core.repository.DirectoryRepository;
import com.augury.core.service.DirectoryNodeService;
import com.augury.model.Directory;
import com.augury.model.DirectoryNode;

public class DirectoryNodeServiceTest extends BaseRepositoryTest{
	@Inject
	DirectoryNodeService service;
	@Inject
	DirectoryRepository dirRepo;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		DirectoryNode node = service.getNode("a/b/c.java");
		assertNotNull(node);
		assertEquals("c.java",node.getName());
		assertEquals("a/b/c.java",node.getPathToNode());
		
		Directory dir = dirRepo.getParentDirectory(node);
		assertNotNull(dir);
		assertEquals("b", dir.getName());
		assertEquals("a/b/",dir.getPathToNode());
		
		Directory parentDir =  dirRepo.getParentDirectory(dir);
		assertNotNull(parentDir);
		assertEquals("a",parentDir.getName());
		assertEquals("a/",parentDir.getPathToNode());	
	}
}
