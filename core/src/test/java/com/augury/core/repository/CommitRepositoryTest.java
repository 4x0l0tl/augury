package com.augury.core.repository;

import static org.junit.Assert.*;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.junit.Test;
import org.springframework.transaction.annotation.Transactional;

import com.augury.model.Branch;
import com.augury.model.Change;
import com.augury.model.Commit;
import com.augury.model.File;

public class CommitRepositoryTest extends BaseRepositoryTest {
	@Inject
	BranchRepository branchRepository;
	@Inject
	CommitRepository commitRepository;

	@Test
	@Transactional
	public void test() {
		Branch trunk = new Branch("trunk");
		trunk = branchRepository.save(trunk);
		File file = new File("A", "/");
		Commit c0 = new Commit("0", new DateTime(), "", trunk);
		commitRepository.save(c0);
		
		trunk.setLatestCommit(c0);
		trunk = branchRepository.save(trunk);
		
		Commit c1 = new Commit("1", new DateTime(), "", trunk);
		Change change = new Change("ADD", "A");
		c1.affected(file, change);
		c1.setPreviousCommit(c0);		
		commitRepository.save(c1);
		
		trunk.setLatestCommit(c1);
		trunk = branchRepository.save(trunk);
		
//		commitRepository.save(c1);

		Commit c = commitRepository.getLatestCommitForBranch(trunk);
		assertNotNull(c);
	}
}
