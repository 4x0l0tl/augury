package com.augury.core.repository;

import static org.junit.Assert.assertNull;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;

import com.augury.core.repository.IssueRepository;
import com.augury.model.Issue;

public class IssueRepositoryTest extends BaseRepositoryTest {

	@Inject
	IssueRepository issueRepository;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		Issue issue = issueRepository.getIssueForReference("ABC-123");
		assertNull(issue);
	}

}
