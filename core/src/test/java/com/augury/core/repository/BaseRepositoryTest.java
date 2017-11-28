package com.augury.core.repository;

import javax.inject.Inject;

import org.junit.After;
import org.junit.runner.RunWith;
import org.neo4j.ogm.session.Session;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.augury.core.config.Neo4jTestConfiguration;
import com.google.common.collect.Maps;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=com.augury.core.Application.class,webEnvironment=WebEnvironment.NONE)
@ActiveProfiles("test")
@Import(Neo4jTestConfiguration.class)
public abstract class BaseRepositoryTest {
	@Inject Session db;
	
	@After
	public void tearDown(){
		db.query("MATCH (n) OPTIONAL MATCH (n)-[r]-() DELETE r, n", Maps.newHashMap());
	}
}
