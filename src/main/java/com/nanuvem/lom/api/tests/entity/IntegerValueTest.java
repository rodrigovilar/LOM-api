package com.nanuvem.lom.api.tests.entity;

import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.tests.LomTestCase;

import static com.nanuvem.lom.api.tests.propertytype.AttributeHelper.*;
import static com.nanuvem.lom.api.tests.entity.InstanceHelper.*;
import static com.nanuvem.lom.api.tests.entitytype.EntityHelper.*;

public abstract class IntegerValueTest extends LomTestCase {

	@Test
	public void instanceWithValidValuesForTheConfigurationOfAttributesInteger() {
		createEntity("abc", "a");
		createOneAttribute("abc.a", null, "number", Type.INTEGER,
				"{\"default\": 1}");
		createAndVerifyOneInstance("abc.a", (String) null);

		createEntity("abc", "b");
		createOneAttribute("abc.b", null, "number", Type.INTEGER,
				"{\"minvalue\": 1}");
		createAndVerifyOneInstance("abc.b", "1");

		createEntity("abc", "c");
		createOneAttribute("abc.c", null, "number", Type.INTEGER,
				"{\"maxvalue\": 1}");
		createAndVerifyOneInstance("abc.c", "1");

		createEntity("abc", "d");
		createOneAttribute("abc.d", null, "number", Type.INTEGER,
				"{\"minvalue\": 1, \"maxvalue\": 1}");
		createAndVerifyOneInstance("abc.d", "1");

		createEntity("abc", "e");
		createOneAttribute("abc.e", null, "number", Type.INTEGER,
				"{\"default\": 1, \"minvalue\": 1, \"maxvalue\": 1}");
		createAndVerifyOneInstance("abc.e", (String) null);

		createEntity("abc", "f");
		createOneAttribute("abc.f", null, "number", Type.INTEGER,
				"{\"mandatory\": true,\"default\": 1, \"minvalue\": 1, \"maxvalue\": 1}");
		createAndVerifyOneInstance("abc.f", (String) null);
	}

	@Test
	public void instanceWithInvalidValuesForIntegerTypeAttributeWithoutConfiguration() {
		String messageException = "Invalid value for the Instance. The value for the 'name' attribute must be an int";

		createEntity("abc", "a");
		createOneAttribute("abc.a", null, "name", Type.INTEGER, "");

		expectExceptionOnCreateInvalidInstance("abc.a", messageException,
				"false");

		expectExceptionOnCreateInvalidInstance("abc.a", messageException,
				"true");

		expectExceptionOnCreateInvalidInstance("abc.a", messageException, "pa");

		expectExceptionOnCreateInvalidInstance("abc.a", messageException, "3.2");

		expectExceptionOnCreateInvalidInstance("abc.a", messageException,
				"0.75");

		expectExceptionOnCreateInvalidInstance("abc.a", messageException,
				"-3.2");
	}

	@Test
	public void instanceWithInvalidValuesForConfigurationOfIntegerAttributes() {
		createEntity("abc", "a");
		createEntity("abc", "b");
		createEntity("abc", "c");
		createEntity("abc", "d");
		createEntity("abc", "e");
		createEntity("abc", "f");
		createEntity("abc", "g");

		invalidValueForInstance(
				"abc.a",
				null,
				"nameA",
				Type.INTEGER,
				"{\"mandatory\" : true}",
				null,
				"Invalid value for the Instance. The value for the 'nameA' attribute is mandatory");
		invalidValueForInstance(
				"abc.b",
				null,
				"nameB",
				Type.INTEGER,
				"{\"minvalue\" : 3}",
				"2",
				"Invalid value for the Instance. The value for 'nameB' must be greater or equal to 3");
		invalidValueForInstance(
				"abc.c",
				null,
				"nameC",
				Type.INTEGER,
				"{\"mandatory\" : true, \"minvalue\" : 3}",
				null,
				"Invalid value for the Instance. The value for the 'nameC' attribute is mandatory");
		invalidValueForInstance(
				"abc.d",
				null,
				"nameD",
				Type.INTEGER,
				"{\"maxvalue\" : 3}",
				"4",
				"Invalid value for the Instance. The value for 'nameD' must be smaller or equal to 3");
		invalidValueForInstance(
				"abc.e",
				null,
				"nameE",
				Type.INTEGER,
				"{\"mandatory\" : true, \"maxvalue\" : 3}",
				null,
				"Invalid value for the Instance. The value for the 'nameE' attribute is mandatory");
		invalidValueForInstance(
				"abc.f",
				null,
				"nameF",
				Type.INTEGER,
				"{\"minvalue\" : 3, \"maxvalue\" : 3}",
				"4",
				"Invalid value for the Instance. The value for 'nameF' must be smaller or equal to 3");
		invalidValueForInstance(
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

		updateOneValueOfInstanceAndVerifyOneException("abc", "a", "counter",
				Type.INTEGER, null, "0", null, null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "b", "counter",
				Type.INTEGER, "{\"default\": 0}", null, "1", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "c", "counter",
				Type.INTEGER, "{\"minvalue\": 0}", "1", "0", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "d", "counter",
				Type.INTEGER, "{\"maxvalue\": 10}", "0", "10", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "e", "counter",
				Type.INTEGER, "{\"mandatory\": true}", "0", "1", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "f", "counter",
				Type.INTEGER, "{\"mandatory\": false}", "0", "1", null);

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"g",
				"counter",
				Type.INTEGER,
				"{\"default\": 0, \"mandatory\": true, \"minvalue\": 0, \"maxvalue\": 10}",
				"2", "3", null);
	}

	@Test
	public void expectAnExceptionWhenUpdatingAttributeValueWithInvalidValue() {

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"a",
				"counter",
				Type.INTEGER,
				"{\"minvalue\": 5}",
				"6",
				"0",
				"Invalid value for the Instance. The value for 'counter' must be greater or equal to 5");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"b",
				"counter",
				Type.INTEGER,
				"{\"maxvalue\": 5}",
				"2",
				"6",
				"Invalid value for the Instance. The value for 'counter' must be smaller or equal to 5");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"c",
				"counter",
				Type.INTEGER,
				"{\"mandatory\": true}",
				"2",
				null,
				"Invalid value for the Instance. The value for the 'counter' attribute is mandatory");

		updateOneValueOfInstanceAndVerifyOneException(
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