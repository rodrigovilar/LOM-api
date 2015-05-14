package com.nanuvem.lom.api.dao;

import java.util.List;

import com.nanuvem.lom.api.EntityType;

public interface EntityTypeDao {

	EntityType create(EntityType entity);

	List<EntityType> listAll();

	EntityType findById(Long id);

	List<EntityType> listByFullName(String fragment);

	EntityType findByFullName(String fullName);

	EntityType update(EntityType entity);

	void delete(Long id);
}
