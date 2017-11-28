package com.augury.core.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.neo4j.ogm.session.Session;

import com.augury.model.Branch;
import com.augury.model.Commit;
import com.augury.model.Project;

public class BranchRepositoryTest extends BaseRepositoryTest {
	@Inject
	Session db;
	@Inject
	BranchRepository branchRepository;
	@Inject
	CommitRepository commitRepository;
	@Inject
	ProjectRepository projectRepository;
	
	DateTime dateBranched;
	@Before
	public void setUp() {
//		Branch trunk = Branch.builder().name("trunk").commit(commit).build();
		Branch trunk = new Branch("trunk");
		
//		Commit commit = Commit.builder().revision("1").commitDate(new DateTime()).build();
		Commit commit = new Commit("1", new DateTime(), "", trunk);
		commitRepository.save(commit);
//		Branch branch = Branch.builder().name("branch").branchedFrom(trunk).build();
		Branch branch = new Branch("branch");
		branch.setBranchedFrom(trunk);
		
		trunk.getCommits().add(commit);
//		Project project = Project.builder().name("Test").description("This is a test").build();
		Project project = new Project("Test", "This is a test");
		project.addBranch(trunk);
		project.getBranches().add(branch);
//		relationshipRepository.has(project, trunk);
		
		projectRepository.save(project);
//		branchRepository.save(branch);
//		branchRepository.save(trunk);
//		relationshipRepository.has(project,branch);
		dateBranched = new DateTime();
//		relationshipRepository.branched(trunk,branch,dateBranched);
		
//		relationshipRepository.committedTo(commit, trunk);
 	}
	
	@Test
	public void testGetBranchByName() {
		Branch branch = branchRepository.getBranchByName("trunk");
		assertNotNull(branch);
		assertEquals(branch.getName(),"trunk");
	}
	
	@Test
	public void testGetNumCommits() {
		Branch branch = branchRepository.getBranchByName("trunk");
		long numberOfCommits =  branchRepository.getNumCommits(branch);
		assertNotNull(numberOfCommits);
		assertEquals(1, numberOfCommits);
	}
	
	@Test
	public void testGetBranchesForProject(){
//		assertEquals(1L,projectRepository.count());
		Project project = projectRepository.getProjectByName("Test");
		assertNotNull(project);
		List<Branch> branches = branchRepository.getBranchesForProject(project);
		
		assertNotNull(branches);
		assertEquals(2,branches.size());
	}
	
	@Test
	public void testGetBranchForCommit(){
		Commit commit = commitRepository.getCommitForRevision("1");
		Branch branch = branchRepository.getBranchForCommit(commit);
		assertNotNull(branch);
		assertEquals("trunk", branch.getName());
	}
//	
//	@Test
//	public void testDateBranched() {
//		Branch branch = branchRepository.getBranchByName("branch");
//		assertNotNull(branch);
//		long date = branchRepository.getDateBranched(branch);
//		assertNotNull(date);
//		assertEquals(dateBranched.getMillis(), date);
//	}
//	
	@Test
	public void testGetParentBranch() {
		Branch branch = branchRepository.getBranchByName("branch");
		assertNotNull(branch);
		Branch parentBranch  = branchRepository.getParentBranch(branch);
		assertNotNull(parentBranch);
		assertEquals("trunk",parentBranch.getName());
		
	}
}
