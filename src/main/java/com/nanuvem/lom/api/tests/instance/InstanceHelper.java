package com.nanuvem.lom.api.tests.instance;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import org.codehaus.jackson.JsonNode;

import com.nanuvem.lom.api.Attribute;
import com.nanuvem.lom.api.AttributeType;
import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.Instance;
import com.nanuvem.lom.api.MetadataException;
import com.nanuvem.lom.api.tests.attribute.AttributeHelper;
import com.nanuvem.lom.api.util.JsonNodeUtil;

public class InstanceHelper {

	private static Facade facade;
	
    public static void setFacade(Facade facade) {
        InstanceHelper.facade = facade;
    }

    public static Instance createOneInstance(String entityFullName,
			String... values) {

		Entity entity = null;
		if (entityFullName != null) {
			entity = facade.findEntityByFullName(entityFullName);
		}

		Instance instance = new Instance();
		instance.setEntity(entity);

		for (int i = 0; i < values.length; i++) {
			AttributeValue attributeValue = new AttributeValue();
			attributeValue.setValue(values[i]);
			
			if (entity != null) {
				attributeValue.setAttribute(entity.getAttributes().get(i));
			}
			
			attributeValue.setInstance(instance);
			instance.getValues().add(attributeValue);
		}
		return facade.create(instance);
	}

	public static void expectExceptionOnCreateInvalidInstance(String entityFullName,
			String exceptedMessage, String... values) {

		try {
			createOneInstance(entityFullName, values);
			fail();
		} catch (MetadataException metadataException) {
			Assert.assertEquals(exceptedMessage, metadataException.getMessage());
		}
	}

	public static void createAndVerifyOneInstance(String entityFullName,
			String... values) {

		Instance createdInstance = createOneInstance(entityFullName, values);

		Assert.assertNotNull(createdInstance.getId());
		Assert.assertEquals(new Integer(0), createdInstance.getVersion());
		Assert.assertEquals(createdInstance,
				facade.findInstanceById(createdInstance.getId()));
		Assert.assertEquals(entityFullName, createdInstance.getEntity()
				.getFullName());

		verifyAllAttributesValues(createdInstance, values);
	}

	private static void verifyAllAttributesValues(Instance createdInstance,
			String... values) {

		for (int i = 0; i < values.length; i++) {
			String value = values[i];
			AttributeValue createdValue = createdInstance.getValues().get(i);

			Assert.assertNotNull("Id was null", createdValue.getId());
			
			if (usesDefaultConfiguration(value, createdValue)) {
				validateThatDefaultConfigurationWasAppliedToValue(createdValue);
			} else {
				Assert.assertEquals(value, createdValue.getValue());
			}
		}
	}

	private static boolean usesDefaultConfiguration(
			String value, AttributeValue createdValue) {

		return ((value == null) || value.isEmpty())
				&& (createdValue.getAttribute().getConfiguration() != null)
				&& (createdValue.getAttribute().getConfiguration()
						.contains(Attribute.DEFAULT_CONFIGURATION_NAME));
	}

	public static AttributeValue newAttributeValue(String attributeName,
			String entityFullName, String value) {

		AttributeValue attributeValue = new AttributeValue();
		attributeValue.setAttribute(facade
				.findAttributeByNameAndEntityFullName(attributeName,
						entityFullName));
		attributeValue.setValue(value);
		return attributeValue;
	}

	private static void validateThatDefaultConfigurationWasAppliedToValue(
			AttributeValue attributeValue) {
		JsonNode jsonNode = null;
		try {
			jsonNode = JsonNodeUtil.validate(attributeValue.getAttribute()
					.getConfiguration(), null);
		} catch (Exception e) {
			fail();
			throw new RuntimeException(
					"Json configuration is in invalid format");
		}
		String defaultField = jsonNode
				.get(Attribute.DEFAULT_CONFIGURATION_NAME).asText();
		Assert.assertEquals(attributeValue.getValue(), defaultField);
	}

	static AttributeValue attributeValue(String attributeName, String objValue) {
		Attribute attribute = new Attribute();
		attribute.setName(attributeName);
		AttributeValue value = new AttributeValue();
		value.setValue(objValue);
		value.setAttribute(attribute);
		return value;
	}

	public static void invalidValueForInstance(String entityName,
			Integer sequence, String attributeName, AttributeType type,
			String configuration, String value, String expectedMessage) {

        AttributeHelper.createOneAttribute(entityName, sequence, attributeName,
				type, configuration);

		InstanceHelper.expectExceptionOnCreateInvalidInstance(entityName,
				expectedMessage, value);

	}
}