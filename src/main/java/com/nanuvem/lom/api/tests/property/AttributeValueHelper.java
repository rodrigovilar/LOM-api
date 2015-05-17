package com.nanuvem.lom.api.tests.property;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import com.nanuvem.lom.api.Property;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.MetadataException;

public class AttributeValueHelper {

	public static void addAndVerifyOneOrMoreValidAttributesValues(
			Facade facade, Entity instance, Property... newAttributesValues) {

		for (Property value : newAttributesValues) {
			instance.getProperties().add(value);
		}

		Entity updatedInstance = facade.update(instance);
		Assert.assertEquals(instance.getVersion() + 1, updatedInstance
				.getVersion().intValue());

		for (Property newAttributeValue : newAttributesValues) {
			for (Property updatedAttributeValue : instance.getProperties()) {
				if (newAttributesValues.equals(updatedAttributeValue)) {
					Assert.assertEquals(0, newAttributeValue.getVersion()
							.intValue());
					Assert.assertEquals(newAttributeValue.getEntity(),
							updatedInstance);
				}
			}
		}
		Assert.assertEquals(instance.getProperties().size(), updatedInstance
				.getProperties().size());
	}

	public static Entity removeAndVerifyAttributeValue(Facade facade,
			Entity instance, String attributeName, String expectedErrorMessage) {

		Entity updatedInstance = null;

		for (Property av : instance.getProperties()) {
			if (av.getPropertyType().getName().equals(attributeName)) {
				instance.getProperties().remove(av);

				try {
					updatedInstance = facade.update(instance);
					validateRemovalAttributeValue(attributeName,
							updatedInstance);

				} catch (MetadataException e) {
					validateExceptionMessageIfExceptedMessagesWasNotNull(
							expectedErrorMessage, e);
				}
				break;
			}
		}
		return updatedInstance;
	}

	private static void validateExceptionMessageIfExceptedMessagesWasNotNull(
			String expectedErrorMessage, MetadataException e) {

		if (expectedErrorMessage != null && !expectedErrorMessage.isEmpty()) {
			Assert.assertEquals(expectedErrorMessage, e.getMessage());
		} else {
			fail();
		}
	}

	private static void validateRemovalAttributeValue(String attributeName,
			Entity updatedInstance) {

		boolean valueWasRemoved = true;

		for (Property value : updatedInstance.getProperties()) {
			if (value.getPropertyType().getName().equals(attributeName)) {
				valueWasRemoved = false;
			}
		}
		Assert.assertTrue("Attribute Value wasn't removed in update",
				valueWasRemoved);
	}
}
