package com.nanuvem.lom.api.tests.property;

import static com.nanuvem.lom.api.tests.entity.EntityHelper.createAndVerifyOneEntity;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.newProperty;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createAndSaveOneEntityType;
import static com.nanuvem.lom.api.tests.property.PropertyHelper.addAndVerifyOneOrMoreValidProperties;
import static com.nanuvem.lom.api.tests.property.PropertyHelper.removeAndVerifyProperty;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.createOnePropertyType;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.Property;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class PropertyTest extends LomTestCase {

	@Test
	public void addAttributeValueNotPresentOnInstance() {
		createAndSaveOneEntityType("abc", "a");
		createOnePropertyType("abc.a", null, "pa", Type.TEXT, null);
		createOnePropertyType("abc.a", null, "pb", Type.LONGTEXT, null);
		createAndVerifyOneEntity("abc.a", "value for pa attribute");
		Entity createdInstance1 = facade.findEntityById(1L);
		Property newValue1 = newProperty("pb", "abc.a",
				"long value for pb attribute", createdInstance1);
		addAndVerifyOneOrMoreValidProperties(this.facade,
				createdInstance1, newValue1);

		createAndSaveOneEntityType("abc", "b");
		createOnePropertyType("abc.b", null, "pa", Type.LONGTEXT, null);
		createOnePropertyType("abc.b", null, "pb", Type.TEXT, null);
		createOnePropertyType("abc.b", null, "pc", Type.INTEGER, null);
		createOnePropertyType("abc.b", null, "pd", Type.PASSWORD, null);

		createAndVerifyOneEntity("abc.b", "long value for pa attribute",
				"value for pb attribute");
		Entity createdInstance2 = facade.findEntityById(2L);
		Property newValue2 = newProperty("pc", "abc.b", "32",
				createdInstance2);
		Property newValue3 = newProperty("pd", "abc.b",
				"ebdcc58318529b385455002860eafbd8", createdInstance2);
		PropertyHelper.addAndVerifyOneOrMoreValidProperties(
				this.facade, createdInstance2, newValue2, newValue3);
	}

	@Test
	@Ignore
	public void removeOneAttributeValuePresentedOnInstance() {
		createAndSaveOneEntityType("abc", "a");
		createOnePropertyType("abc.a", null, "pa", Type.TEXT, null);
		createAndVerifyOneEntity("abc.a", "value for pa attribute");

		Entity createdInstance = facade.findEntityById(1L);
		Entity updatedInstance = removeAndVerifyProperty(facade,
				createdInstance, "pa", null);
		Assert.assertNull(updatedInstance.getProperties().get(0));
	}

	@Test
	@Ignore
	public void removeOneMandatoryAttributeValueOnInstance() {
		createAndSaveOneEntityType("abc", "a");
		createOnePropertyType("abc.a", null, "pa", Type.TEXT,
				"{\"mandatory\": true}");
		createAndVerifyOneEntity("abc.a", "value for pa attribute");

		Entity createdInstance = facade.findEntityById(1L);
		createdInstance.getProperties().remove(0);

		removeAndVerifyProperty(facade, createdInstance, "pa",
				"Invalid value for the Instance. The value for the 'pa' attribute is mandatory");

	}
}
