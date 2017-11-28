package com.augury.core.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@ComponentScan({ "com.augury.core" })
@Configuration
@EnableNeo4jRepositories(basePackages = "com.augury.core.repository")
@PropertySource(value = { "/application.properties" })
@Profile({ "embedded", "test" })
public class Neo4jTestConfiguration {
	@Bean
	public SessionFactory sessionFactory() {
		return new SessionFactory("com.augury.model");
	}

	@Bean
	public Neo4jTransactionManager transactionManager() {
		return new Neo4jTransactionManager(sessionFactory());
	}
}
