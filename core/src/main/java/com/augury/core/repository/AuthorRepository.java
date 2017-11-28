package com.augury.core.repository;

import java.util.List;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

import com.augury.model.Author;
import com.augury.model.Commit;

public interface AuthorRepository extends GraphRepository<Author> {
	@Query("MATCH (a:Author {name:{0}}) RETURN a")
	public Author getAuthorByName(String name);
	
	@Query("START author = node({0}) MATCH (author)-[:AUTHORED]->(commit) RETURN commit")
	public List<Commit> getCommitForAuthor(Author author);
}
