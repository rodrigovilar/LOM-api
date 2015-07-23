package com.nanuvem.lom.api.tests.entity;

import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.tests.LomTestCase;

import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.*;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.*;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.*;

public abstract class IntegerValueTest extends LomTestCase {

	@Test
	public void instanceWithValidValuesForTheConfigurationOfAttributesInteger() {
		createEntityType("abc", "a");
		createOnePropertyType("abc.a", null, "number", Type.INTEGER,
				"{\"default\": 1}");
		createAndVerifyOneEntity("abc.a", (String) null);

		createEntityType("abc", "b");
		createOnePropertyType("abc.b", null, "number", Type.INTEGER,
				"{\"minvalue\": 1}");
		createAndVerifyOneEntity("abc.b", "1");

		createEntityType("abc", "c");
		createOnePropertyType("abc.c", null, "number", Type.INTEGER,
				"{\"maxvalue\": 1}");
		createAndVerifyOneEntity("abc.c", "1");

		createEntityType("abc", "d");
		createOnePropertyType("abc.d", null, "number", Type.INTEGER,
				"{\"minvalue\": 1, \"maxvalue\": 1}");
		createAndVerifyOneEntity("abc.d", "1");

		createEntityType("abc", "e");
		createOnePropertyType("abc.e", null, "number", Type.INTEGER,
				"{\"default\": 1, \"minvalue\": 1, \"maxvalue\": 1}");
		createAndVerifyOneEntity("abc.e", (String) null);

		createEntityType("abc", "f");
		createOnePropertyType("abc.f", null, "number", Type.INTEGER,
				"{\"mandatory\": true,\"default\": 1, \"minvalue\": 1, \"maxvalue\": 1}");
		createAndVerifyOneEntity("abc.f", (String) null);
	}

	@Test
	public void instanceWithInvalidValuesForIntegerTypeAttributeWithoutConfiguration() {
		String messageException = "Invalid value for the Instance. The value for the 'name' attribute must be an int";

		createEntityType("abc", "a");
		createOnePropertyType("abc.a", null, "name", Type.INTEGER, "");

		expectExceptionOnCreateInvalidEntity("abc.a", messageException,
				"false");

		expectExceptionOnCreateInvalidEntity("abc.a", messageException,
				"true");

		expectExceptionOnCreateInvalidEntity("abc.a", messageException, "pa");

		expectExceptionOnCreateInvalidEntity("abc.a", messageException, "3.2");

		expectExceptionOnCreateInvalidEntity("abc.a", messageException,
				"0.75");

		expectExceptionOnCreateInvalidEntity("abc.a", messageException,
				"-3.2");
	}

	@Test
	public void instanceWithInvalidValuesForConfigurationOfIntegerAttributes() {
		createEntityType("abc", "a");
		createEntityType("abc", "b");
		createEntityType("abc", "c");
		createEntityType("abc", "d");
		createEntityType("abc", "e");
		createEntityType("abc", "f");
		createEntityType("abc", "g");

		invalidValueForEntity(
				"abc.a",
				null,
				"nameA",
				Type.INTEGER,
				"{\"mandatory\" : true}",
				null,
				"Invalid value for the Instance. The value for the 'nameA' attribute is mandatory");
		invalidValueForEntity(
				"abc.b",
				null,
				"nameB",
				Type.INTEGER,
				"{\"minvalue\" : 3}",
				"2",
				"Invalid value for the Instance. The value for 'nameB' must be greater or equal to 3");
		invalidValueForEntity(
				"abc.c",
				null,
				"nameC",
				Type.INTEGER,
				"{\"mandatory\" : true, \"minvalue\" : 3}",
				null,
				"Invalid value for the Instance. The value for the 'nameC' attribute is mandatory");
		invalidValueForEntity(
				"abc.d",
				null,
				"nameD",
				Type.INTEGER,
				"{\"maxvalue\" : 3}",
				"4",
				"Invalid value for the Instance. The value for 'nameD' must be smaller or equal to 3");
		invalidValueForEntity(
				"abc.e",
				null,
				"nameE",
				Type.INTEGER,
				"{\"mandatory\" : true, \"maxvalue\" : 3}",
				null,
				"Invalid value for the Instance. The value for the 'nameE' attribute is mandatory");
		invalidValueForEntity(
				"abc.f",
				null,
				"nameF",
				Type.INTEGER,
				"{\"minvalue\" : 3, \"maxvalue\" : 3}",
				"4",
				"Invalid value for the Instance. The value for 'nameF' must be smaller or equal to 3");
		invalidValueForEntity(
				"abc.g",
				null,
				"nameG",
				Type.INTEGER,
				"{\"mandatory\" : true, \"minvalue\" : 3, \"maxvalue\" : 3}",
				null,
				"Invalid value for the Instance. The value for the 'nameG' attribute is mandatory");
	}

	@Test
	public void updateValueOfAttributeValueWithValidValuesAndDifferentAttributeConfigurations() {

		updateOneValueOfEntityAndVerifyOneException("abc", "a", "counter",
				Type.INTEGER, null, "0", null, null);

		updateOneValueOfEntityAndVerifyOneException("abc", "b", "counter",
				Type.INTEGER, "{\"default\": 0}", null, "1", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "c", "counter",
				Type.INTEGER, "{\"minvalue\": 0}", "1", "0", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "d", "counter",
				Type.INTEGER, "{\"maxvalue\": 10}", "0", "10", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "e", "counter",
				Type.INTEGER, "{\"mandatory\": true}", "0", "1", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "f", "counter",
				Type.INTEGER, "{\"mandatory\": false}", "0", "1", null);

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"g",
				"counter",
				Type.INTEGER,
				"{\"default\": 0, \"mandatory\": true, \"minvalue\": 0, \"maxvalue\": 10}",
				"2", "3", null);
	}

	@Test
	public void expectAnExceptionWhenUpdatingAttributeValueWithInvalidValue() {

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"a",
				"counter",
				Type.INTEGER,
				"{\"minvalue\": 5}",
				"6",
				"0",
				"Invalid value for the Instance. The value for 'counter' must be greater or equal to 5");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"b",
				"counter",
				Type.INTEGER,
				"{\"maxvalue\": 5}",
				"2",
				"6",
				"Invalid value for the Instance. The value for 'counter' must be smaller or equal to 5");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"c",
				"counter",
				Type.INTEGER,
				"{\"mandatory\": true}",
				"2",
				null,
				"Invalid value for the Instance. The value for the 'counter' attribute is mandatory");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"d",
				"counter",
				Type.INTEGER,
				"{\"mandatory\": true, \"minvalue\": 10, \"maxvalue\":10}",
				"10",
				"11",
				"Invalid value for the Instance. The value for 'counter' must be smaller or equal to 10");
	}
}