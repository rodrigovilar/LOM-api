package com.nanuvem.lom.api.dao;

public interface DaoFactory {

	EntityTypeDao createEntityTypeDao();

	PropertyTypeDao createPropertyTypeDao();

	EntityDao createEntityDao();

	PropertyDao createPropertyDao();

	void createDatabaseSchema();

	void dropDatabaseSchema();
}
