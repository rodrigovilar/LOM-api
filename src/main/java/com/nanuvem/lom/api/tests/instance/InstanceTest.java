package com.nanuvem.lom.api.tests.instance;

import static com.nanuvem.lom.api.tests.attribute.AttributeHelper.createOneAttribute;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.createEntity;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.createAndVerifyOneInstance;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.createOneInstance;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.expectExceptionOnCreateInvalidInstance;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.updateAndVerifyValues;

import org.junit.Test;

import com.nanuvem.lom.api.Attribute;
import com.nanuvem.lom.api.AttributeType;
import com.nanuvem.lom.api.AttributeValue;
import com.nanuvem.lom.api.Entity;
import com.nanuvem.lom.api.Instance;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class InstanceTest extends LomTestCase {

	@Test
	public void unknownClass() {
		expectExceptionOnCreateInvalidInstance("a", "Entity not found: a", "30");

		expectExceptionOnCreateInvalidInstance("abc.a",
				"Entity not found: abc.a", "30");
	}

	@Test
	public void nullClass() {
		expectExceptionOnCreateInvalidInstance(null,
				"Invalid value for Instance entity: The entity is mandatory",
				"30");
	}

	@Test
	public void entityWithoutAttributes() {
		createEntity("abc", "a");
		createAndVerifyOneInstance("abc.a");

		createEntity("abc", "b");
		createAndVerifyOneInstance("abc.b");

		createEntity("", "a");
		createAndVerifyOneInstance("a");

		createEntity("", "b");
		createAndVerifyOneInstance("b");
	}

	@Test
	public void entityWithKnownAttributesAndWithoutConfiguration() {
		createEntity("system", "Client");
		createOneAttribute("system.Client", null, "pa", AttributeType.TEXT,
				null);

		createEntity("system", "User");
		createOneAttribute("system.User", null, "pa", AttributeType.TEXT, null);
		createOneAttribute("system.User", null, "pb", AttributeType.LONGTEXT,
				null);

		createEntity("system", "Organization");
		createOneAttribute("system.Organization", null, "pa",
				AttributeType.TEXT, null);
		createOneAttribute("system.Organization", null, "pb",
				AttributeType.LONGTEXT, null);
		createOneAttribute("system.Organization", null, "pc",
				AttributeType.INTEGER, null);

		createEntity("system", "Category");
		createOneAttribute("system.Category", null, "pa", AttributeType.TEXT,
				null);
		createOneAttribute("system.Category", null, "pb",
				AttributeType.LONGTEXT, null);
		createOneAttribute("system.Category", null, "pc",
				AttributeType.INTEGER, null);
		createOneAttribute("system.Category", null, "pd",
				AttributeType.PASSWORD, null);

		createAndVerifyOneInstance("system.client");
		createAndVerifyOneInstance("system.client", "va");
		createAndVerifyOneInstance("system.user");
		createAndVerifyOneInstance("system.user", "va", "vb");
		createAndVerifyOneInstance("system.organization");
		createAndVerifyOneInstance("system.organization", "va", "vb", "3");
		createAndVerifyOneInstance("system.category");
		createAndVerifyOneInstance("system.category", "va", "vb", "3", "vd");
	}

	@Test
	public void updateValueOfTheAttributeValueForOtherValidValues() {
		createEntity("abc", "a");

		Attribute attribute1 = createOneAttribute("abc.a", null, "email",
				AttributeType.TEXT,
				"{\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}");
		Instance instance1 = InstanceHelper.createOneInstance(
				attribute1.getEntity(), "abc@abc.com");
		AttributeValue value1 = instance1.getValues().get(0);
		value1.setValue("cba@cba.com");
		updateAndVerifyValues(instance1, value1);

		Attribute attribute2 = createOneAttribute("abc.a", null, "description",
				AttributeType.LONGTEXT,
				"{\"default\": \"Nothing to say\", \"maxlength\": 100}");
		Instance instance2 = createOneInstance(attribute2.getEntity(),
				"Here must contain a description, written by a long text");
		AttributeValue value2 = instance2.getValues().get(0);
		value2.setValue("It's a personal like that codes");
		updateAndVerifyValues(instance2, value2);

		Attribute attribute3 = createOneAttribute("abc.a", null, "counter",
				AttributeType.INTEGER, "{\"default\": \"0\", \"minvalue\": 0}");
		Instance instance3 = createOneInstance(attribute3.getEntity(), "1");
		AttributeValue value3 = instance3.getValues().get(0);
		value3.setValue("3");
		updateAndVerifyValues(instance3, value3);

		Attribute attribute4 = createOneAttribute("abc.a", null, "secretKey",
				AttributeType.PASSWORD, "{\"default\": \"password\"}");
		Instance instance4 = createOneInstance(attribute4.getEntity(),
				"5f6eca57fc12718a639e3433bb02a7c5");
		AttributeValue value4 = instance4.getValues().get(0);
		value4.setValue("9e107d9d372bb6826bd81d3542a419d6");
		updateAndVerifyValues(instance4, value4);
	}
}