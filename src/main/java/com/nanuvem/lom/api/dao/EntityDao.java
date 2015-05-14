package com.nanuvem.lom.api.dao;

import java.util.List;

import com.nanuvem.lom.api.Entity;


public interface EntityDao {

	Entity create(Entity instance);

	Entity findInstanceById(Long id);
	
    List<Entity> findInstancesByEntityId(Long entityId);

	Entity update(Entity instance);

	void delete(Long id);
	
}
