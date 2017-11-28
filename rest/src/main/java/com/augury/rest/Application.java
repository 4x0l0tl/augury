package com.augury.rest;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
	private final static Logger logger = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) 
    {
        ApplicationContext ctx = SpringApplication.run(WebConfiguration.class, args);
        logger.debug("Beans provided by Spring Boot:");

        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) 
        {
            logger.debug(beanName);
        }		
    }
}