package com.augury.core.service;

import javax.inject.Inject;

import org.neo4j.ogm.session.Session;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.augury.model.Entity;

@Component
public abstract class GenericService<T> implements Service<T> {

	 private static final int DEPTH_LIST = 0;
	 private static final int DEPTH_ENTITY = 1;
	 @Inject
	 protected Session session;

	 @Override
	 public Iterable<T> findAll() {
	     return session.loadAll(getEntityType(), DEPTH_LIST);
	 }

	 @Override
	 public T find(Long id) {
	     return session.load(getEntityType(), id, DEPTH_ENTITY);
	 }

	 @Override
	 public void delete(Long id) {
	     session.delete(session.load(getEntityType(), id));
	 }

	 @Override
	 @Transactional
	 public T createOrUpdate(T entity) {
	     session.save(entity, DEPTH_ENTITY);
	     return find(((Entity) entity).getId());
	 }

	 public abstract Class<T> getEntityType();
	 public abstract GraphRepository<T> getRepository();
}
