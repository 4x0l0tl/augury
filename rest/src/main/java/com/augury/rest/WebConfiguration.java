package com.augury.rest;

import javax.annotation.Resource;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.template.Neo4jOperations;
import org.springframework.data.neo4j.template.Neo4jTemplate;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;
import org.springframework.plugin.core.OrderAwarePluginRegistry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.augury.plugin.it.IssueTrackerPlugin;

@EnableTransactionManagement
@EnableScheduling
@EnableAutoConfiguration
@ComponentScan({"com.augury.core","com.augury.web.controller"})
@Configuration
@PropertySource(value = { "/application.properties" })
@EnableNeo4jRepositories(basePackages="com.augury.core.repository")
public class WebConfiguration  {
	@Autowired
	OrderAwarePluginRegistry<IssueTrackerPlugin,String> registry;

	@Resource
	Environment env;
    public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://neo4j:movies@localhost:7474";

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
        	.setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver")
        	.setURI(URL);
        return config;
    }

    @Bean
    public Neo4jTransactionManager transactionManager() {
        return new Neo4jTransactionManager(sessionFactory());
    }
    
    public SessionFactory sessionFactory() {
        return new SessionFactory("com.augury.model");
    }
}