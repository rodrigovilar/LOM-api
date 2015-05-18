package com.nanuvem.lom.api.tests.entity;

import static com.nanuvem.lom.api.tests.entity.EntityHelper.createAndVerifyOneEntity;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.expectExceptionOnCreateInvalidEntity;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createEntityType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.createOnePropertyType;

import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class InstanceTest extends LomTestCase {

	@Test
	public void unknownClass() {
		expectExceptionOnCreateInvalidEntity("a", "Entity not found: a", "30");

		expectExceptionOnCreateInvalidEntity("abc.a",
				"Entity not found: abc.a", "30");
	}

	@Test
	public void nullClass() {
		expectExceptionOnCreateInvalidEntity(null,
				"Invalid value for Instance entity: The entity is mandatory",
				"30");
	}

	@Test
	public void entityWithoutAttributes() {
		createEntityType("abc", "a");
		createAndVerifyOneEntity("abc.a");

		createEntityType("abc", "b");
		createAndVerifyOneEntity("abc.b");

		createEntityType("", "a");
		createAndVerifyOneEntity("a");

		createEntityType("", "b");
		createAndVerifyOneEntity("b");
	}

	@Test
	public void entityWithKnownAttributesAndWithoutConfiguration() {
		createEntityType("system", "Client");
		createOnePropertyType("system.Client", null, "pa", Type.TEXT,
				null);

		createEntityType("system", "User");
		createOnePropertyType("system.User", null, "pa", Type.TEXT, null);
		createOnePropertyType("system.User", null, "pb", Type.LONGTEXT,
				null);

		createEntityType("system", "Organization");
		createOnePropertyType("system.Organization", null, "pa",
				Type.TEXT, null);
		createOnePropertyType("system.Organization", null, "pb",
				Type.LONGTEXT, null);
		createOnePropertyType("system.Organization", null, "pc",
				Type.INTEGER, null);

		createEntityType("system", "Category");
		createOnePropertyType("system.Category", null, "pa", Type.TEXT,
				null);
		createOnePropertyType("system.Category", null, "pb",
				Type.LONGTEXT, null);
		createOnePropertyType("system.Category", null, "pc",
				Type.INTEGER, null);
		createOnePropertyType("system.Category", null, "pd",
				Type.PASSWORD, null);

		createAndVerifyOneEntity("system.client");
		createAndVerifyOneEntity("system.client", "va");
		createAndVerifyOneEntity("system.user");
		createAndVerifyOneEntity("system.user", "va", "vb");
		createAndVerifyOneEntity("system.organization");
		createAndVerifyOneEntity("system.organization", "va", "vb", "3");
		createAndVerifyOneEntity("system.category");
		createAndVerifyOneEntity("system.category", "va", "vb", "3", "vd");
	}
}