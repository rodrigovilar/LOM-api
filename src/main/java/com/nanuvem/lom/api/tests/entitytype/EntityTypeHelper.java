package com.nanuvem.lom.api.tests.entitytype;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;

import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.MetadataException;

public class EntityTypeHelper {

	private static Facade facade;

	public static EntityType createEntityType(String namespace, String name) {
		EntityType entityType = new EntityType();
		entityType.setName(name);
		entityType.setNamespace(namespace);
		entityType = facade.create(entityType);
		return entityType;
	}

	public static void expectExceptionOnInvalidFindEntityTypeByFullName(
			String fullName, String expectedMessage) {
		try {
			facade.findEntityTypeByFullName(fullName);
			fail();
		} catch (MetadataException me) {
			Assert.assertEquals(expectedMessage, me.getMessage());
		}
	}

	public static void expectExceptionOnInvalidEntityTypeList(String fragment,
			String expectedMessage, String... args) {
		try {
			facade.listEntitiesTypesByFullName(fragment);
			fail();
		} catch (MetadataException e) {
			String formatedMessage = String.format(expectedMessage,
					(Object[]) args);
			Assert.assertEquals(formatedMessage, e.getMessage());
		}
	}

	public static void expectExceptionOnInvalidEntityTypeUpdate(
			EntityType entityType, String secondnamespace, String secondname,
			String expectedMessage, String... args) {

		try {
			entityType.setNamespace(secondnamespace);
			entityType.setName(secondname);
			facade.update(entityType);
			fail();
		} catch (MetadataException e) {
			String formatedMessage = String.format(expectedMessage,
					(Object[]) args);
			Assert.assertEquals(formatedMessage, e.getMessage());
		}
	}

	public static void expectExceptionOnCreateInvalidEntityType(
			String namespace, String name, String expectedMessage,
			String... args) {
		try {
			createAndVerifyOneEntityType(namespace, name);
			fail();
		} catch (MetadataException e) {
			String formatedMessage = String.format(expectedMessage,
					(Object[]) args);
			Assert.assertEquals(formatedMessage, e.getMessage());
		}
	}

	public static void createUpdateAndVerifyOneEntityType(String firstNamespace,
			String firstName, String secondNamespace, String secondName) {

		EntityType entityType = new EntityType();
		entityType.setNamespace(firstNamespace);
		entityType.setName(firstName);
		entityType = facade.create(entityType);

		Assert.assertNotNull(entityType.getId());
		Assert.assertEquals((Integer) 0, entityType.getVersion());

		EntityType updatedEntityType = new EntityType();
		updatedEntityType.setNamespace("secondNamespace");
		updatedEntityType.setName("secondName");
		updatedEntityType.setId(entityType.getId());
		updatedEntityType.setVersion(entityType.getVersion() + 1);

		EntityType entityType1 = facade.update(updatedEntityType);

		List<EntityType> allEntitiesTypes = facade.listAllEntitiesTypes();
		EntityType entityTypeFound = allEntitiesTypes.get(0);

		Assert.assertEquals((Integer) 1, entityType1.getVersion());
		Assert.assertNotSame(entityType, entityTypeFound);
		facade.deleteEntityType(entityType.getId());
	}

	public static void createAndVerifyTwoEntitiesTypes(String entityType1namespace,
			String entityType1name, String entityType2namespace, String entityType2name) {
		
		EntityType entityType1 = new EntityType();
		entityType1.setNamespace(entityType1namespace);
		entityType1.setName(entityType1name);
		entityType1 = facade.create(entityType1);

		EntityType entityType2 = new EntityType();
		entityType2.setNamespace(entityType2namespace);
		entityType2.setName(entityType2name);
		entityType2 = facade.create(entityType2);

		Assert.assertNotNull(entityType1.getId());
		Assert.assertNotNull(entityType2.getId());

		Assert.assertEquals((Integer) 0, entityType1.getVersion());
		Assert.assertEquals((Integer) 0, entityType2.getVersion());

		List<EntityType> entitiesTypes = facade.listAllEntitiesTypes();
		Assert.assertEquals(2, entitiesTypes.size());
		Assert.assertEquals(entityType1, entitiesTypes.get(0));
		Assert.assertEquals(entityType2, entitiesTypes.get(1));

		facade.deleteEntityType(entityType1.getId());
		facade.deleteEntityType(entityType2.getId());
	}

	public static EntityType createAndSaveOneEntityType(String namespace,
			String name) {
		
		EntityType entityType = new EntityType();
		entityType.setNamespace(namespace);
		entityType.setName(name);
		entityType = facade.create(entityType);

		Assert.assertNotNull(entityType.getId());
		Assert.assertEquals((Integer) 0, entityType.getVersion());
		return entityType;
	}

	public static void createAndVerifyOneEntityType(String namespace, String name) {
		EntityType entityType = new EntityType();
		entityType.setNamespace(namespace);
		entityType.setName(name);
		entityType = facade.create(entityType);

		Assert.assertNotNull(entityType.getId());
		Assert.assertEquals((Integer) 0, entityType.getVersion());

		List<EntityType> entitiesTypes = facade.listAllEntitiesTypes();
		Assert.assertEquals(1, entitiesTypes.size());
		Assert.assertEquals(entityType, entitiesTypes.get(0));

		facade.deleteEntityType(entityType.getId());
	}

	public static void setFacade(Facade facade) {
		EntityTypeHelper.facade = facade;
	}

}
