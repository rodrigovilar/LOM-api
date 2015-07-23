package com.nanuvem.lom.api.dao;

import java.util.List;

import com.nanuvem.lom.api.PropertyType;

public interface PropertyTypeDao {

	PropertyType create(PropertyType propertyType);

	PropertyType findPropertyTypeById(Long id);

	PropertyType findPropertyTypeByNameAndEntityTypeFullName(String propertyTypeName,
			String entityTypeFullName);

	List<PropertyType> findPropertiesTypesByFullNameEntityType(String fullnameEntityType);

	PropertyType update(PropertyType propertyType);

}
