package com.nanuvem.lom.api.tests.property;

import static com.nanuvem.lom.api.tests.entity.InstanceHelper.createAndVerifyOneInstance;
import static com.nanuvem.lom.api.tests.entity.InstanceHelper.newAttributeValue;
import static com.nanuvem.lom.api.tests.entitytype.EntityHelper.createAndSaveOneEntity;
import static com.nanuvem.lom.api.tests.property.AttributeValueHelper.addAndVerifyOneOrMoreValidAttributesValues;
import static com.nanuvem.lom.api.tests.property.AttributeValueHelper.removeAndVerifyAttributeValue;
import static com.nanuvem.lom.api.tests.propertytype.AttributeHelper.createOneAttribute;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.Property;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class AttributeValueTest extends LomTestCase {

	@Test
	public void addAttributeValueNotPresentOnInstance() {
		createAndSaveOneEntity("abc", "a");
		createOneAttribute("abc.a", null, "pa", Type.TEXT, null);
		createOneAttribute("abc.a", null, "pb", Type.LONGTEXT, null);
		createAndVerifyOneInstance("abc.a", "value for pa attribute");
		Entity createdInstance1 = facade.findEntityById(1L);
		Property newValue1 = newAttributeValue("pb", "abc.a",
				"long value for pb attribute", createdInstance1);
		addAndVerifyOneOrMoreValidAttributesValues(this.facade,
				createdInstance1, newValue1);

		createAndSaveOneEntity("abc", "b");
		createOneAttribute("abc.b", null, "pa", Type.LONGTEXT, null);
		createOneAttribute("abc.b", null, "pb", Type.TEXT, null);
		createOneAttribute("abc.b", null, "pc", Type.INTEGER, null);
		createOneAttribute("abc.b", null, "pd", Type.PASSWORD, null);

		createAndVerifyOneInstance("abc.b", "long value for pa attribute",
				"value for pb attribute");
		Entity createdInstance2 = facade.findEntityById(2L);
		Property newValue2 = newAttributeValue("pc", "abc.b", "32",
				createdInstance2);
		Property newValue3 = newAttributeValue("pd", "abc.b",
				"ebdcc58318529b385455002860eafbd8", createdInstance2);
		AttributeValueHelper.addAndVerifyOneOrMoreValidAttributesValues(
				this.facade, createdInstance2, newValue2, newValue3);
	}

	@Test
	@Ignore
	public void removeOneAttributeValuePresentedOnInstance() {
		createAndSaveOneEntity("abc", "a");
		createOneAttribute("abc.a", null, "pa", Type.TEXT, null);
		createAndVerifyOneInstance("abc.a", "value for pa attribute");

		Entity createdInstance = facade.findEntityById(1L);
		Entity updatedInstance = removeAndVerifyAttributeValue(facade,
				createdInstance, "pa", null);
		Assert.assertNull(updatedInstance.getProperties().get(0));
	}

	@Test
	@Ignore
	public void removeOneMandatoryAttributeValueOnInstance() {
		createAndSaveOneEntity("abc", "a");
		createOneAttribute("abc.a", null, "pa", Type.TEXT,
				"{\"mandatory\": true}");
		createAndVerifyOneInstance("abc.a", "value for pa attribute");

		Entity createdInstance = facade.findEntityById(1L);
		createdInstance.getProperties().remove(0);

		removeAndVerifyAttributeValue(facade, createdInstance, "pa",
				"Invalid value for the Instance. The value for the 'pa' attribute is mandatory");

	}
}
