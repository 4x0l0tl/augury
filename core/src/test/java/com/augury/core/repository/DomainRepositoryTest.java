package com.augury.core.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import com.augury.model.Author;
import com.augury.model.Branch;
import com.augury.model.Commit;
import com.augury.model.Issue;
import com.augury.model.Project;
import com.augury.model.Repository;
import com.google.common.collect.Maps;
import com.augury.core.repository.BaseRepositoryTest;

public class DomainRepositoryTest extends BaseRepositoryTest {
	@Inject
	AuthorRepository authorRepository;
	@Inject
	BranchRepository branchRepository;
	@Inject
	CommitRepository commitRepository;
	@Inject
	DirectoryNodeRepository directoryNodeRepository;
	@Inject
	ProjectRepository projectRepository;
	@Inject
	IssueRepository issueRepository;
	@Inject
	RepoRepository repoRepository;

	@Before 
	@Transactional
	public void setUp() {
		Project project= new Project("Test","This is a test");
		Repository repo =  Repository.builder().provider("Test").url("test").project(project).build();
//		repo.addProject(project);
		repoRepository.save(repo);

		Repository r = repoRepository.getRepositoryByName("test");
		
		Branch trunk = new Branch("trunk");
		Branch branch = new Branch("branch");
		branch.setBranchedFrom(trunk);
		project.getBranches().add(trunk);
		project.getBranches().add(branch);
		
		Author adam = new Author("Adam");
		adam.setEmail("adam@test.com");
		authorRepository.save(adam);
		
		Commit c1 = new Commit("1", new DateTime(), "commit",trunk);
		c1.setAuthor(adam);
		trunk.getCommits().add(c1);		
		
		Author bob = new Author("Bob");
		bob.setEmail("bob@test.com");
		authorRepository.save(bob);
		
		Commit c2 = new Commit("2", new DateTime(), "second commit",branch);
		c2.setAuthor(bob);
		branch.getCommits().add(c2);
		
		Issue issue1 = Issue.builder().creationDate(new DateTime()).description("Test Issue").issueReference("A-1").issueType("Bug").build();
		c2.relatedTo(issue1);
		
		projectRepository.save(project);
 	}
	
	@After
	public void tearDown(){
		db.query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE r, n", Maps.newHashMap());
	}
	
	@Test 
	@DirtiesContext
	public void testGetBranchByName() {
		Branch branch = branchRepository.getBranchByName("trunk");
		assertNotNull(branch);
		assertEquals(branch.getName(),"trunk");
	}
	
	@Test
	@DirtiesContext
	public void testGetNumCommits() {
		Branch branch = branchRepository.getBranchByName("trunk");
		long numberOfCommits =  branchRepository.getNumCommits(branch);
		assertNotNull(numberOfCommits);
		assertTrue(1==numberOfCommits);	
	}
	
	@Test 
	@DirtiesContext
	public void testGetBranchesForProject(){
		Project project = projectRepository.getProjectByName("Test");
		List<Branch> branches = branchRepository.getBranchesForProject(project);
		
		assertNotNull(branches);
		assertEquals(2,branches.size());
	}
	
	@Test 
	@DirtiesContext
	public void testGetBranchForCommit(){
		Commit commit = commitRepository.getCommitForRevision("2");
		Branch branch = branchRepository.getBranchForCommit(commit);
		assertNotNull(branch);
		assertEquals("branch", branch.getName());
	}
	
	@Test 
	@DirtiesContext
	public void testGetAuthorByName() {
		Author author = authorRepository.getAuthorByName("Adam");
		assertNotNull(author);
		assertEquals("Adam", author.getName());
		assertEquals("adam@test.com",author.getEmail());
	}
	
	@Test 
	@DirtiesContext
	public void testCommitsForAuthor(){
		Author author = authorRepository.getAuthorByName("Adam");
		assertNotNull(author);
		List<Commit> commits = authorRepository.getCommitForAuthor(author);
		assertNotNull(commits);
		assertEquals(1,commits.size());
	}
	
	@Test
	public void test(){
		Repository r = repoRepository.getRepositoryByName("test");
		assertNotNull(r);
	}
}
