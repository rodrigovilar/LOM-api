package com.nanuvem.lom.api.tests.entitytype;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Assert;

import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.MetadataException;

public class EntityHelper {

	private static Facade facade;

	public static EntityType createEntity(String namespace, String name) {
		EntityType entity = new EntityType();
		entity.setName(name);
		entity.setNamespace(namespace);
		entity = facade.create(entity);
		return entity;
	}

	public static void expectExceptionOnInvalidFindEntityByFullName(
			String fullName, String expectedMessage) {
		try {
			facade.findEntityTypeByFullName(fullName);
			fail();
		} catch (MetadataException me) {
			Assert.assertEquals(expectedMessage, me.getMessage());
		}
	}

	public static void expectExceptionOnInvalidEntityList(String fragment,
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

	public static void expectExceptionOnInvalidEntityUpdate(EntityType entity,
			String secondnamespace, String secondname, String expectedMessage,
			String... args) {

		try {
			entity.setNamespace(secondnamespace);
			entity.setName(secondname);
			facade.update(entity);
			fail();
		} catch (MetadataException e) {
			String formatedMessage = String.format(expectedMessage,
					(Object[]) args);
			Assert.assertEquals(formatedMessage, e.getMessage());
		}
	}

	public static void expectExceptionOnCreateInvalidEntity(String namespace,
			String name, String expectedMessage, String... args) {
		try {
			createAndVerifyOneEntity(namespace, name);
			fail();
		} catch (MetadataException e) {
			String formatedMessage = String.format(expectedMessage,
					(Object[]) args);
			Assert.assertEquals(formatedMessage, e.getMessage());
		}
	}

	public static void createUpdateAndVerifyOneEntity(String firstNamespace,
			String firstName, String secondNamespace, String secondName) {

		EntityType entity = new EntityType();
		entity.setNamespace(firstNamespace);
		entity.setName(firstName);
		entity = facade.create(entity);

		Assert.assertNotNull(entity.getId());
		Assert.assertEquals((Integer) 0, entity.getVersion());

		EntityType updateEntity = new EntityType();
		updateEntity.setNamespace("secondNamespace");
		updateEntity.setName("secondName");
		updateEntity.setId(entity.getId());
		updateEntity.setVersion(entity.getVersion() + 1);

		EntityType entity1 = facade.update(updateEntity);

		List<EntityType> allEntities = facade.listAllEntitiesTypes();
		EntityType entityFound = allEntities.get(0);

		Assert.assertEquals((Integer) 1, entity1.getVersion());
		Assert.assertNotSame(entity, entityFound);
		facade.deleteEntityType(entity.getId());
	}

	public static void createAndVerifyTwoEntities(String entity1namespace,
			String entity1name, String entity2namespace, String entity2name) {
		EntityType entity1 = new EntityType();
		entity1.setNamespace(entity1namespace);
		entity1.setName(entity1name);
		entity1 = facade.create(entity1);

		EntityType entity2 = new EntityType();
		entity2.setNamespace(entity2namespace);
		entity2.setName(entity2name);
		entity2 = facade.create(entity2);

		Assert.assertNotNull(entity1.getId());
		Assert.assertNotNull(entity2.getId());

		Assert.assertEquals((Integer) 0, entity1.getVersion());
		Assert.assertEquals((Integer) 0, entity2.getVersion());

		List<EntityType> entities = facade.listAllEntitiesTypes();
		Assert.assertEquals(2, entities.size());
		Assert.assertEquals(entity1, entities.get(0));
		Assert.assertEquals(entity2, entities.get(1));

		facade.deleteEntityType(entity1.getId());
		facade.deleteEntityType(entity2.getId());
	}

	public static EntityType createAndSaveOneEntity(String namespace, String name) {
		EntityType entity = new EntityType();
		entity.setNamespace(namespace);
		entity.setName(name);
		entity = facade.create(entity);

		Assert.assertNotNull(entity.getId());
		Assert.assertEquals((Integer) 0, entity.getVersion());
		return entity;
	}

	public static void createAndVerifyOneEntity(String namespace, String name) {
		EntityType entity = new EntityType();
		entity.setNamespace(namespace);
		entity.setName(name);
		entity = facade.create(entity);

		Assert.assertNotNull(entity.getId());
		Assert.assertEquals((Integer) 0, entity.getVersion());

		List<EntityType> entities = facade.listAllEntitiesTypes();
		Assert.assertEquals(1, entities.size());
		Assert.assertEquals(entity, entities.get(0));

		facade.deleteEntityType(entity.getId());
	}

	public static void setFacade(Facade facade) {
		EntityHelper.facade = facade;
	}

}
