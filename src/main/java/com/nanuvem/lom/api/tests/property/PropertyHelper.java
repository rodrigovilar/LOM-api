package com.nanuvem.lom.api.tests.property;

import static org.junit.Assert.fail;
import junit.framework.Assert;

import com.nanuvem.lom.api.Property;
import com.nanuvem.lom.api.Facade;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.MetadataException;

public class PropertyHelper {

	public static void addAndVerifyOneOrMoreValidProperties(
			Facade facade, Entity entity, Property... newProperties) {

		for (Property property : newProperties) {
			entity.getProperties().add(property);
		}

		Entity updatedEntity = facade.update(entity);
		Assert.assertEquals(entity.getVersion() + 1, updatedEntity
				.getVersion().intValue());

		for (Property newAProperty : newProperties) {
			for (Property updatedProperty : entity.getProperties()) {
				if (newProperties.equals(updatedProperty)) {
					Assert.assertEquals(0, newAProperty.getVersion()
							.intValue());
					Assert.assertEquals(newAProperty.getEntity(),
							updatedEntity);
				}
			}
		}
		Assert.assertEquals(entity.getProperties().size(), updatedEntity
				.getProperties().size());
	}

	public static Entity removeAndVerifyProperty(Facade facade,
			Entity entity, String propertyTypeName, String expectedErrorMessage) {

		Entity updatedEntity = null;

		for (Property property : entity.getProperties()) {
			if (property.getPropertyType().getName().equals(propertyTypeName)) {
				entity.getProperties().remove(property);

				try {
					updatedEntity = facade.update(entity);
					validateRemovalProperty(propertyTypeName,
							updatedEntity);

				} catch (MetadataException e) {
					validateExceptionMessageIfExceptedMessagesWasNotNull(
							expectedErrorMessage, e);
				}
				break;
			}
		}
		return updatedEntity;
	}

	private static void validateExceptionMessageIfExceptedMessagesWasNotNull(
			String expectedErrorMessage, MetadataException e) {

		if (expectedErrorMessage != null && !expectedErrorMessage.isEmpty()) {
			Assert.assertEquals(expectedErrorMessage, e.getMessage());
		} else {
			fail();
		}
	}

	private static void validateRemovalProperty(String propertyTypeName,
			Entity updatedEntity) {

		boolean propertyWasRemoved = true;

		for (Property property : updatedEntity.getProperties()) {
			if (property.getPropertyType().getName().equals(propertyTypeName)) {
				propertyWasRemoved = false;
			}
		}
		Assert.assertTrue("Attribute Value wasn't removed in update",
				propertyWasRemoved);
	}
}
