package com.nanuvem.lom.api.tests.entity;

import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.tests.LomTestCase;

import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.*;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.*;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.*;

public abstract class TextValueTest extends LomTestCase {

	@Test
	public void instanceWithValidValuesForConfigurationOfTextAttributes() {
		createEntityType("abc", "a");
		createOnePropertyType("abc.a", null, "name", Type.TEXT,
				"{\"mandatory\": true}");
		createAndVerifyOneEntity("abc.a", "Jose");

		createEntityType("abc", "a1");
		createOnePropertyType("abc.a1", null, "name", Type.TEXT, null);
		createAndVerifyOneEntity("abc.a1", "Jose");

		createEntityType("abc", "b");
		createOnePropertyType("abc.b", null, "name", Type.TEXT,
				"{\"mandatory\": true, \"default\": \"Michael\"}");
		createAndVerifyOneEntity("abc.b", (String) null);

		createEntityType("abc", "c");
		createOnePropertyType("abc.c", null, "name", Type.TEXT,
				"{\"mandatory\": true, \"default\": \"Johson\", \"minlength\": 6}");
		createAndVerifyOneEntity("abc.c", (String) null);

		createEntityType("abc", "d");
		createOnePropertyType("abc.d", null, "name", Type.TEXT,
				"{\"mandatory\": true, \"minlength\": 6}");
		createAndVerifyOneEntity("abc.d", "Johson");

		createEntityType("abc", "e");
		createOnePropertyType("abc.e", null, "name", Type.TEXT,
				"{\"mandatory\": true, \"default\": \"Johson\", \"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.e", (String) null);

		createEntityType("abc", "f");
		createOnePropertyType("abc.f", null, "name", Type.TEXT,
				"{\"mandatory\": true, \"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.f", "Johson");

		createEntityType("abc", "g");
		createOnePropertyType(
				"abc.g",
				null,
				"name",
				Type.TEXT,
				"{\"mandatory\": true, \"default\": \"Johson\", \"minlength\": 6, \"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.g", (String) null);

		createEntityType("abc", "i");
		createOnePropertyType("abc.i", null, "name", Type.TEXT,
				"{\"mandatory\": true, \"minlength\": 6, \"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.i", "Johson");

		createEntityType("abc", "j");
		createOnePropertyType(
				"abc.j",
				null,
				"name",
				Type.TEXT,
				"{\"mandatory\": true, \"default\": \"Johson\", \"minlength\": 3, \"maxlength\" : 8}");
		createAndVerifyOneEntity("abc.j", (String) null);

		createEntityType("abc", "k");
		createOnePropertyType("abc.k", null, "name", Type.TEXT,
				"{\"mandatory\": true, \"minlength\": 3, \"maxlength\" : 8}");
		createAndVerifyOneEntity("abc.k", "Johson");

		createEntityType("abc", "l");
		createOnePropertyType(
				"abc.l",
				null,
				"name",
				Type.TEXT,
				"{\"mandatory\": true, \"default\": \"abc@abc.com\", \"minlength\": 3, \"maxlength\" : 15, "
						+ "\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}");
		createAndVerifyOneEntity("abc.l", (String) null);

		createEntityType("abc", "m");
		createOnePropertyType(
				"abc.m",
				null,
				"name",
				Type.TEXT,
				"{\"mandatory\": true, \"minlength\": 3, \"maxlength\" : 15, "
						+ "\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}");
		createAndVerifyOneEntity("abc.m", "abc@abc.com");
	}

	@Test
	public void instanceWithInvalidValuesForConfigurationOfTextAttributes() {
		createEntityType("abc", "a");
		createEntityType("abc", "b");
		createEntityType("abc", "ba");
		createEntityType("abc", "c");
		createEntityType("abc", "ca");
		createEntityType("abc", "d");
		createEntityType("abc", "da");

		invalidValueForEntity(
				"abc.a",
				null,
				"nameA",
				Type.TEXT,
				"{\"mandatory\" : true}",
				null,
				"Invalid value for the Instance. The value for the 'nameA' attribute is mandatory");
		invalidValueForEntity(
				"abc.b",
				null,
				"nameB",
				Type.TEXT,
				"{\"minlength\" : 5}",
				"abcd",
				"Invalid value for the Instance. The value for 'nameB' must have a minimum length of 5 characters");
		invalidValueForEntity(
				"abc.ba",
				null,
				"nameBA",
				Type.TEXT,
				"{\"mandatory\" : true, \"minlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameBA' attribute is mandatory, "
						+ "The value for 'nameBA' must have a minimum length of 5 characters");
		invalidValueForEntity(
				"abc.c",
				null,
				"nameC",
				Type.TEXT,
				"{\"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameC' must have a maximum length of 5 characters");
		invalidValueForEntity(
				"abc.ca",
				null,
				"nameCA",
				Type.TEXT,
				"{\"mandatory\" : true, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameCA' attribute is mandatory");
		invalidValueForEntity(
				"abc.d",
				null,
				"nameD",
				Type.TEXT,
				"{\"minlength\" : 5, \"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameD' must have a maximum length of 5 characters");
		invalidValueForEntity(
				"abc.da",
				null,
				"nameDA",
				Type.TEXT,
				"{\"mandatory\" : true, \"minlength\" : 5, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameDA' attribute is mandatory, "
						+ "The value for 'nameDA' must have a minimum length of 5 characters");

		// invalidValueForInstance(
		// "abc.a",
		// null,
		// "nameE",
		// AttributeType.TEXT,
		// "{\"regex\" :\"\\d\\d\\d([,\\s])?\\d\\d\\d\\d\"}",
		// "12345678",
		// "Invalid value for the Instance. The value of the 'nameE' attribute does not meet the defined regular expression");
		// InstanceHelper
		// .invalidValueForInstance(
		// facade,
		// "abc.a",
		// null,
		// "nameF",
		// AttributeType.TEXT,
		// "{\"mandatory\" : true, \"minlength\" : 4, \"maxlength\" : 4, \"regex\" : \"\\d\\d\\d\\s\\d\\d\\d\\s\\d\\d\\d\\s\\d\\d\"}",
		// "123 456 789",
		// "Invalid value for the Instance. The value for 'nameF' must have a maximum length 4 characters, The value of the 'nameF' attribute does not meet the defined regular expression");
	}

	@Test
	public void updateValueOfAttributeValueWithValidValuesAndDifferentAttributeConfigurations() {

		updateOneValueOfEntityAndVerifyOneException("abc", "a", "email",
				Type.TEXT, null, null, "cba@cba.com", null);

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"b",
				"email",
				Type.TEXT,
				"{\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}",
				"abc@abc.com", "cba@cba.com", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "c", "email",
				Type.TEXT, "{\"default\": \"someone@nanuvem.com\"}",
				null, "fernando@nanuvem.com", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "d", "email",
				Type.TEXT, "{\"minlength\": 10}", null,
				"fernando@nanuvem.com", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "e", "email",
				Type.TEXT, "{\"maxlength\": 20}", null,
				"fernando@nanuvem.com", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "f", "email",
				Type.TEXT, "{\"maxlength\": 20, \"minlength\":20}",
				null, "fernando@nanuvem.com", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "g", "email",
				Type.TEXT, "{\"mandatory\": true}",
				"someone@nanuvem.com", "fernando@nanuvem.com", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "h", "email",
				Type.TEXT, "{\"mandatory\": false}", null,
				"fernando@nanuvem.com", null);

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"i",
				"email",
				Type.TEXT,
				"{\"mandatory\": false, \"maxlength\": 11, \"minlength\":11, "
						+ "\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}",
				"abc@abc.com", "cba@cba.com", null);
	}

	@Test
	public void expectAnExceptionWhenUpdatingAttributeValueWithInvalidValue() {

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"a",
				"email",
				Type.TEXT,
				"{\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}",
				"abc@abc.com",
				"someone@nanuvem.com",
				"Invalid value for the Instance. The value for the 'email' attribute does not meet the defined regular expression");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"b",
				"email",
				Type.TEXT,
				"{\"minlength\": 50}",
				null,
				"fernando@nanuvem.com",
				"Invalid value for the Instance. The value for 'email' must have a minimum length of 50 characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"c",
				"email",
				Type.TEXT,
				"{\"maxlength\": 10}",
				null,
				"fernando@nanuvem.com",
				"Invalid value for the Instance. The value for 'email' must have a maximum length of 10 characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"d",
				"email",
				Type.TEXT,
				"{\"mandatory\": true}",
				"fernando@nanuvem.com",
				null,
				"Invalid value for the Instance. The value for the 'email' attribute is mandatory");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"e",
				"email",
				Type.TEXT,
				"{\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\", \"minlength\":11, \"maxlength\": 11, \"mandatory\":true}",
				"abc@abc.com",
				"some.one@nanuvem.com",
				"Invalid value for the Instance. "
						+ "The value for the 'email' attribute does not meet the defined regular expression, "
						+ "The value for 'email' must have a maximum length of 11 characters");
	}
}