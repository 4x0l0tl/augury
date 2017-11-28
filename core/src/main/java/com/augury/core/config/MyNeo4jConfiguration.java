package com.augury.core.config;

import javax.annotation.Resource;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@PropertySource(value = { "/application.properties" })
@EnableNeo4jRepositories(basePackages = "com.augury.core.repository")
@EnableTransactionManagement
@Profile({ "prod" })
public class MyNeo4jConfiguration {

	@Resource
	Environment env;

	@Bean
	public org.neo4j.ogm.config.Configuration getConfiguration() {
		org.neo4j.ogm.config.Configuration config;
		if (env.getProperty(ConfigConstants.DB_TYPE, "Embedded").equalsIgnoreCase("HTTP")) {
			config = new org.neo4j.ogm.config.Configuration();
			config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
					.setURI(env.getProperty(ConfigConstants.DB_URL, "http://120.0.0.1:7474"));
		} else {
			config = new org.neo4j.ogm.config.Configuration();
			config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver")
					.setURI(env.getProperty(ConfigConstants.DB_URL, "data/augury.db"));
		}
		return config;
	}

	@Bean
	public SessionFactory getSessionFactory() {
		// with domain entity base package(s)
		return new SessionFactory(getConfiguration(), "com.augury.model");
	}

	@Bean
	public Neo4jTransactionManager transactionManager() {
		return new Neo4jTransactionManager(getSessionFactory());
	}
}