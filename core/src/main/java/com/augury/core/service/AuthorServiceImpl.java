package com.augury.core.service;

import javax.inject.Inject;

import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Service;

import com.augury.core.repository.AuthorRepository;
import com.augury.model.Author;

@Service
public class AuthorServiceImpl extends GenericService<Author> implements AuthorService {
	@Inject private AuthorRepository repository;
	public Author getAuthorByName(String name) {
		return repository.getAuthorByName(name);
	}

	@Override
	public Class<Author> getEntityType() {
		return Author.class;
	}

	@Override
	public GraphRepository<Author> getRepository() {
		return repository;
	}
}
