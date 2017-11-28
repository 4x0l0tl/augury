package com.augury.core.structure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import com.augury.model.Directory;
import com.augury.model.DirectoryNode;
import com.augury.model.File;

public class DirectoryNodeTest 
{
	Directory root; 
	@Before
	public void setUp() throws Exception 
	{
		root = new Directory("root", "/");
	}

	@Test
	public void testAdd() 
	{
		assertNotNull(root);
		assertEquals("root",root.getName());
		
		root.add(new Directory("A", root.getPathToNode()+root.getName()+"/"));
		Set<DirectoryNode> children = root.getChildren();
		assertNotNull(children);
		assertEquals(1,children.size());
		
		root.add(new File("alpha",root.getPathToNode()+root.getName()+"/"));
		assertEquals(2,children.size());
		
	}
	
	@Test
	public void testFile()
	{
		File file = new File("alpha", root.getPathToNode()+root.getName()+"/");
		assertEquals(false, file.isDirectory());
		root.add(file);
		
		Set<DirectoryNode> children = root.getChildren();
		assertEquals(1,children.size());
		
		File temp =  (File) children.iterator().next();
		assertEquals(false, temp.isDirectory());
		assertEquals(file.getName(), temp.getName());
		assertEquals(file.getPathToNode(), temp.getPathToNode());	
	}
	
	@Test
	public void testDirectory()
	{
		Directory dir = new Directory("A",root.getPathToNode()+root.getName()+"/");
		assertEquals(true, dir.isDirectory());
		root.add(dir);	
		
		Directory temp = (Directory) root.getChildren().iterator().next();
		assertEquals(true, temp.isDirectory());
		assertEquals(dir.getName(), temp.getName());
		assertEquals(dir.getPathToNode(), temp.getPathToNode());	
	}
	
	@Test(expected = ClassCastException.class)
	public void testFileCastToDirectoryException()
	{
		File file = new File("alpha", root.getPathToNode()+root.getName()+"/");
		assertEquals(false, file.isDirectory());
		root.add(file);
		
		Directory temp = (Directory) root.getChildren().iterator().next();
		assertEquals(true, temp.isDirectory()); //Will not be executed
	}
	
	@Test(expected = ClassCastException.class)
	public void testDirectoryCastToFileException()
	{
		Directory dir = new Directory("A",root.getPathToNode()+root.getName()+"/");
		assertEquals(true, dir.isDirectory());
		root.add(dir);	
		
		File temp = (File) root.getChildren().iterator().next();
		assertEquals(false, temp.isDirectory()); //Will not be executed
	}

}
