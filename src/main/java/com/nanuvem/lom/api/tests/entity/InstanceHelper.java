package com.nanuvem.lom.api.tests.entity;

import static com.nanuvem.lom.api.tests.entitytype.EntityHelper.createEntity;
import static com.nanuvem.lom.api.tests.propertytype.AttributeHelper.createOneAttribute;
import static org.junit.Assert.fail;

import java.util.List;

import junit.framework.Assert;

import org.codehaus.jackson.JsonNode;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.Property;
import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.MetadataException;
import com.nanuvem.lom.api.tests.propertytype.AttributeHelper;
import com.nanuvem.lom.api.util.JsonNodeUtil;

public class InstanceHelper {

	private static Facade facade;

	public static void setFacade(Facade facade) {
		InstanceHelper.facade = facade;
	}

	public static Entity createOneInstance(EntityType entity, String... values) {

		Entity instance = new Entity();
		instance.setEntityType(entity);

		for (int i = 0; i < values.length; i++) {
			Property attributeValue = new Property();
			attributeValue.setValue(values[i]);

			if (entity != null) {
				attributeValue.setPropertyType(entity.getPropertiesTypes().get(i));
			}

			attributeValue.setEntity(instance);
			instance.getProperties().add(attributeValue);
		}
		return facade.create(instance);
	}

	public static void expectExceptionOnCreateInvalidInstance(
			String entityFullName, String exceptedMessage, String... values) {

		try {
			EntityType entity = null;
			if (entityFullName != null) {
				entity = facade.findEntityTypeByFullName(entityFullName);
			}

			createOneInstance(entity, values);
			fail();
		} catch (MetadataException metadataException) {
			Assert.assertEquals(exceptedMessage, metadataException.getMessage());
		}
	}

	public static void createAndVerifyOneInstance(String entityFullName,
			String... values) {

		EntityType entity = null;
		if (entityFullName != null) {
			entity = facade.findEntityTypeByFullName(entityFullName);
		}

		int numberOfInstances = facade.findEntitiesByEntityTypeId(entity.getId())
				.size();

		Entity newInstance = createOneInstance(entity, values);

		Assert.assertNotNull(newInstance.getId());
		Assert.assertEquals(new Integer(0), newInstance.getVersion());

		Entity createdInstance = facade.findEntityById(newInstance.getId());
		Assert.assertEquals(newInstance, createdInstance);
		Assert.assertEquals(entityFullName, createdInstance.getEntityType()
				.getFullName());

		verifyAllAttributesValues(createdInstance, values);

		List<Entity> instances = facade.findEntitiesByEntityTypeId(entity
				.getId());

		Assert.assertEquals(numberOfInstances + 1, instances.size());
		Entity listedInstance = instances.get(numberOfInstances);
		Assert.assertEquals(newInstance, listedInstance);
		Assert.assertEquals(entityFullName, listedInstance.getEntityType()
				.getFullName());

		verifyAllAttributesValues(listedInstance, values);

	}

	private static void verifyAllAttributesValues(Entity createdInstance,
			String... values) {

		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			Property createdValue = createdInstance.getProperties().get(i);

			Assert.assertNotNull("Id was null", createdValue.getId());

			if (usesDefaultConfiguration(value, createdValue)) {
				validateThatDefaultConfigurationWasAppliedToValue(createdValue);
			} else {
				Assert.assertEquals(value, createdValue.getValue());
			}
		}
	}

	private static boolean usesDefaultConfiguration(String value,
			Property createdValue) {

		return ((value == null) || value.isEmpty())
				&& (createdValue.getPropertyType().getConfiguration() != null)
				&& (createdValue.getPropertyType().getConfiguration()
						.contains(PropertyType.DEFAULT_CONFIGURATION_NAME));
	}

	public static Property newAttributeValue(String attributeName,
			String entityFullName, String value, Entity instance) {

		Property attributeValue = new Property();
		attributeValue.setPropertyType(facade
				.findPropertyTypeByNameAndFullnameEntityType(attributeName,
						entityFullName));
		attributeValue.setValue(value);
		attributeValue.setEntity(instance);
		return attributeValue;
	}

	private static void validateThatDefaultConfigurationWasAppliedToValue(
			Property attributeValue) {
		JsonNode jsonNode = null;
		try {
			jsonNode = JsonNodeUtil.validate(attributeValue.getPropertyType()
					.getConfiguration(), null);
		} catch (Exception e) {
			fail();
			throw new RuntimeException(
					"Json configuration is in invalid format");
		}
		String defaultField = jsonNode
				.get(PropertyType.DEFAULT_CONFIGURATION_NAME).asText();
		Assert.assertEquals(attributeValue.getValue(), defaultField);
	}

	static Property attributeValue(String attributeName, String objValue) {
		PropertyType attribute = new PropertyType();
		attribute.setName(attributeName);
		Property value = new Property();
		value.setValue(objValue);
		value.setPropertyType(attribute);
		return value;
	}

	public static void invalidValueForInstance(String entityName,
			Integer sequence, String attributeName, Type type,
			String configuration, String value, String expectedMessage) {

		AttributeHelper.createOneAttribute(entityName, sequence, attributeName,
				type, configuration);

		InstanceHelper.expectExceptionOnCreateInvalidInstance(entityName,
				expectedMessage, value);

	}

	static void updateOneValueOfInstanceAndVerifyOneException(
			String namespaceEntity, String nameEntity, String attributeName,
			Type type, String configuration, String valueOfCreate,
			String valueOfUpdate, String expectedExceptionMessage) {

		try {
			createEntity(namespaceEntity, nameEntity);
			PropertyType attribute = createOneAttribute(namespaceEntity + "."
					+ nameEntity, null, attributeName, type, configuration);

			Entity instance = InstanceHelper.createOneInstance(
					attribute.getEntityType(), valueOfCreate);

			Property value = instance.getProperties().get(0);
			value.setValue(valueOfUpdate);

			facade.update(instance);

		} catch (MetadataException e) {
			Assert.assertEquals(expectedExceptionMessage, e.getMessage());
		}
	}
}