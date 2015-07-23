package com.nanuvem.lom.api;

import java.util.List;
import java.util.Map;

import com.nanuvem.lom.api.dao.DaoFactory;

public interface Facade {

	// EntityType

	EntityType create(EntityType entityType);

	EntityType findEntityTypeById(Long id);

	EntityType findEntityTypeByFullName(String fullName);

	List<EntityType> listAllEntitiesTypes();

	List<EntityType> listEntitiesTypesByFullName(String fragment);

	EntityType update(EntityType entityType);

	void deleteEntityType(Long id);

	// PropertyType

	PropertyType create(PropertyType propertyType);

	PropertyType findPropertyTypeById(Long id);

	PropertyType findPropertyTypeByNameAndFullnameEntityType(String name,
			String fullnameEntityType);

	List<PropertyType> findPropertiesTypesByFullNameEntityType(
			String fullnameEntityTypeParameter);

	PropertyType update(PropertyType propertyType);

	// Instance

	Entity create(Entity entity);

	Entity update(Entity entity);

	Entity findEntityById(Long id);

	List<Entity> findEntitiesByEntityTypeId(Long entityTypeId);

	List<Entity> findEntityByNameOfPropertiesTypeAndByValueOfProperties(
			String fullnameEntityType,
			Map<String, String> nomesDasPropertiesTypesEValoresDasProperties);

	DaoFactory getDaoFactory();
}
