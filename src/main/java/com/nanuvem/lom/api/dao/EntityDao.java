package com.nanuvem.lom.api.dao;

import java.util.List;
import java.util.Map;

import com.nanuvem.lom.api.Entity;

public interface EntityDao {

	Entity create(Entity entity);

	Entity findEntityById(Long id);

	List<Entity> findEntitiesByEntityTypeId(Long entityTypeId);

	Entity update(Entity entity);

	void delete(Long id);

	List<Entity> findEntityByNameOfPropertiesTypeAndByValueOfProperties(
			String fullnameEntityType,
			Map<String, String> nameByPropertiesTypesAndValuesOfProperties);

}
