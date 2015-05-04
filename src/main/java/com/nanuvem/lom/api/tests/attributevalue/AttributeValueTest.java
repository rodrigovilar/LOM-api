package com.nanuvem.lom.api.tests.attributevalue;

import static com.nanuvem.lom.api.tests.attribute.AttributeHelper.createOneAttribute;
import static com.nanuvem.lom.api.tests.attributevalue.AttributeValueHelper.addAndVerifyOneOrMoreValidAttributesValues;
import static com.nanuvem.lom.api.tests.attributevalue.AttributeValueHelper.removeAndVerifyAttributeValue;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.createAndSaveOneEntity;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.createAndVerifyOneInstance;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.newAttributeValue;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.nanuvem.lom.api.AttributeType;
import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Instance;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class AttributeValueTest extends LomTestCase {

	@Test
	public void addAttributeValueNotPresentOnInstance() {
		createAndSaveOneEntity("abc", "a");
		createOneAttribute("abc.a", null, "pa", AttributeType.TEXT, null);
		createOneAttribute("abc.a", null, "pb", AttributeType.LONGTEXT, null);
		createAndVerifyOneInstance("abc.a", "value for pa attribute");
		Instance createdInstance1 = facade.findInstanceById(1L);
		AttributeValue newValue1 = newAttributeValue("pb", "abc.a",
				"long value for pb attribute", createdInstance1);
		addAndVerifyOneOrMoreValidAttributesValues(this.facade,
				createdInstance1, newValue1);

		createAndSaveOneEntity("abc", "b");
		createOneAttribute("abc.b", null, "pa", AttributeType.LONGTEXT, null);
		createOneAttribute("abc.b", null, "pb", AttributeType.TEXT, null);
		createOneAttribute("abc.b", null, "pc", AttributeType.INTEGER, null);
		createOneAttribute("abc.b", null, "pd", AttributeType.PASSWORD, null);

		createAndVerifyOneInstance("abc.b", "long value for pa attribute",
				"value for pb attribute");
		Instance createdInstance2 = facade.findInstanceById(2L);
		AttributeValue newValue2 = newAttributeValue("pc", "abc.b", "32",
				createdInstance2);
		AttributeValue newValue3 = newAttributeValue("pd", "abc.b",
				"ebdcc58318529b385455002860eafbd8", createdInstance2);
		AttributeValueHelper.addAndVerifyOneOrMoreValidAttributesValues(
				this.facade, createdInstance2, newValue2, newValue3);
	}

	@Test
	@Ignore
	public void removeOneAttributeValuePresentedOnInstance() {
		createAndSaveOneEntity("abc", "a");
		createOneAttribute("abc.a", null, "pa", AttributeType.TEXT, null);
		createAndVerifyOneInstance("abc.a", "value for pa attribute");

		Instance createdInstance = facade.findInstanceById(1L);
		Instance updatedInstance = removeAndVerifyAttributeValue(facade,
				createdInstance, "pa", null);
		Assert.assertNull(updatedInstance.getValues().get(0));
	}

	@Test
	@Ignore
	public void removeOneMandatoryAttributeValueOnInstance() {
		createAndSaveOneEntity("abc", "a");
		createOneAttribute("abc.a", null, "pa", AttributeType.TEXT,
				"{\"mandatory\": true}");
		createAndVerifyOneInstance("abc.a", "value for pa attribute");

		Instance createdInstance = facade.findInstanceById(1L);
		createdInstance.getValues().remove(0);

		removeAndVerifyAttributeValue(facade, createdInstance, "pa",
				"Invalid value for the Instance. The value for the 'pa' attribute is mandatory");

	}
}
