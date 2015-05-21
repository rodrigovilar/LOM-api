package com.nanuvem.lom.api.dao;

import java.util.List;

import com.nanuvem.lom.api.Entity;


public interface EntityDao {

	Entity create(Entity entity);

	Entity findEntityById(Long id);
	
    List<Entity> findEntitiesByEntityTypeId(Long entityTypeId);

	Entity update(Entity entity);

	void delete(Long id);
	
}
