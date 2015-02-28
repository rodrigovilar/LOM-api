package com.nanuvem.lom.api.tests.attributevalue;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.Instance;
import com.nanuvem.lom.api.MetadataException;

public class AttributeValueHelper {

	public static void addAndVerifyOneOrMoreValidAttributesValues(
			Facade facade, Instance instance,
			AttributeValue... newAttributesValues) {

		for (AttributeValue value : newAttributesValues) {
			instance.getValues().add(value);
		}

		Instance updatedInstance = facade.update(instance);
		Assert.assertEquals(instance.getVersion() + 1, updatedInstance
				.getVersion().intValue());

		for (AttributeValue newAttributeValue : newAttributesValues) {
			for (AttributeValue updatedAttributeValue : instance.getValues()) {
				if (newAttributesValues.equals(updatedAttributeValue)) {
					Assert.assertEquals(0, newAttributeValue.getVersion()
							.intValue());
					Assert.assertEquals(newAttributeValue.getInstance(),
							updatedInstance);
				}
			}
		}
		Assert.assertEquals(instance.getValues().size()
				+ newAttributesValues.length, updatedInstance.getValues()
				.size());
	}

	public static Instance removeAndVerifyAttributeValue(Facade facade,
			Instance instance, String attributeName, String expectedErrorMessage) {

		Instance updatedInstance = null;

		for (AttributeValue av : instance.getValues()) {
			if (av.getAttribute().getName().equals(attributeName)) {
				instance.getValues().remove(av);

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
			Instance updatedInstance) {

		boolean valueWasRemoved = true;

		for (AttributeValue value : updatedInstance.getValues()) {
			if (value.getAttribute().getName().equals(attributeName)) {
				valueWasRemoved = false;
			}
		}
		Assert.assertTrue("Attribute Value wasn't removed in update",
				valueWasRemoved);
	}
}
