package com.augury.core.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.augury.model.Author;

public class AuthorRepositoryTest extends BaseRepositoryTest{
	
	@Autowired
	AuthorRepository repo;
	
	@Before
	public void setUp() {
	}
	@Test
	public void test() {
		Author author = new Author("Adam");
		author.setEmail("adam@test.com");
		assertEquals(0,repo.count());
		repo.save(author);
		assertEquals(1,repo.count());
	}

}
