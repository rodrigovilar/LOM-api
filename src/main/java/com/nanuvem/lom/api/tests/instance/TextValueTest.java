package com.nanuvem.lom.api.tests.instance;

import org.junit.Test;

import com.nanuvem.lom.api.AttributeType;
import com.nanuvem.lom.api.tests.LomTestCase;

import static com.nanuvem.lom.api.tests.instance.InstanceHelper.*;
import static com.nanuvem.lom.api.tests.attribute.AttributeHelper.*;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.*;

public abstract class TextValueTest extends LomTestCase {

	@Test
	public void instanceWithValidValuesForConfigurationOfTextAttributes() {
		createEntity("abc", "a");
		createOneAttribute("abc.a", null, "name", AttributeType.TEXT,
				"{\"mandatory\": true}");
		createAndVerifyOneInstance("abc.a", "Jose");

		createEntity("abc", "a1");
		createOneAttribute("abc.a1", null, "name", AttributeType.TEXT, null);
		createAndVerifyOneInstance("abc.a1", "Jose");

		createEntity("abc", "b");
		createOneAttribute("abc.b", null, "name", AttributeType.TEXT,
				"{\"mandatory\": true, \"default\": \"Michael\"}");
		createAndVerifyOneInstance("abc.b", (String) null);

		createEntity("abc", "c");
		createOneAttribute("abc.c", null, "name", AttributeType.TEXT,
				"{\"mandatory\": true, \"default\": \"Johson\", \"minlength\": 6}");
		createAndVerifyOneInstance("abc.c", (String) null);

		createEntity("abc", "d");
		createOneAttribute("abc.d", null, "name", AttributeType.TEXT,
				"{\"mandatory\": true, \"minlength\": 6}");
		createAndVerifyOneInstance("abc.d", "Johson");

		createEntity("abc", "e");
		createOneAttribute("abc.e", null, "name", AttributeType.TEXT,
				"{\"mandatory\": true, \"default\": \"Johson\", \"maxlength\" : 6}");
		createAndVerifyOneInstance("abc.e", (String) null);

		createEntity("abc", "f");
		createOneAttribute("abc.f", null, "name", AttributeType.TEXT,
				"{\"mandatory\": true, \"maxlength\" : 6}");
		createAndVerifyOneInstance("abc.f", "Johson");

		createEntity("abc", "g");
		createOneAttribute(
				"abc.g",
				null,
				"name",
				AttributeType.TEXT,
				"{\"mandatory\": true, \"default\": \"Johson\", \"minlength\": 6, \"maxlength\" : 6}");
		createAndVerifyOneInstance("abc.g", (String) null);

		createEntity("abc", "i");
		createOneAttribute("abc.i", null, "name", AttributeType.TEXT,
				"{\"mandatory\": true, \"minlength\": 6, \"maxlength\" : 6}");
		createAndVerifyOneInstance("abc.i", "Johson");

		createEntity("abc", "j");
		createOneAttribute(
				"abc.j",
				null,
				"name",
				AttributeType.TEXT,
				"{\"mandatory\": true, \"default\": \"Johson\", \"minlength\": 3, \"maxlength\" : 8}");
		createAndVerifyOneInstance("abc.j", (String) null);

		createEntity("abc", "k");
		createOneAttribute("abc.k", null, "name", AttributeType.TEXT,
				"{\"mandatory\": true, \"minlength\": 3, \"maxlength\" : 8}");
		createAndVerifyOneInstance("abc.k", "Johson");

		createEntity("abc", "l");
		createOneAttribute(
				"abc.l",
				null,
				"name",
				AttributeType.TEXT,
				"{\"mandatory\": true, \"default\": \"abc@abc.com\", \"minlength\": 3, \"maxlength\" : 15, "
						+ "\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}");
		createAndVerifyOneInstance("abc.l", (String) null);

		createEntity("abc", "m");
		createOneAttribute(
				"abc.m",
				null,
				"name",
				AttributeType.TEXT,
				"{\"mandatory\": true, \"minlength\": 3, \"maxlength\" : 15, "
						+ "\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}");
		createAndVerifyOneInstance("abc.m", "abc@abc.com");
	}

	@Test
	public void instanceWithInvalidValuesForConfigurationOfTextAttributes() {
		createEntity("abc", "a");
		createEntity("abc", "b");
		createEntity("abc", "ba");
		createEntity("abc", "c");
		createEntity("abc", "ca");
		createEntity("abc", "d");
		createEntity("abc", "da");

		invalidValueForInstance(
				"abc.a",
				null,
				"nameA",
				AttributeType.TEXT,
				"{\"mandatory\" : true}",
				null,
				"Invalid value for the Instance. The value for the 'nameA' attribute is mandatory");
		invalidValueForInstance(
				"abc.b",
				null,
				"nameB",
				AttributeType.TEXT,
				"{\"minlength\" : 5}",
				"abcd",
				"Invalid value for the Instance. The value for 'nameB' must have a minimum length of 5 characters");
		invalidValueForInstance(
				"abc.ba",
				null,
				"nameBA",
				AttributeType.TEXT,
				"{\"mandatory\" : true, \"minlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameBA' attribute is mandatory, "
						+ "The value for 'nameBA' must have a minimum length of 5 characters");
		invalidValueForInstance(
				"abc.c",
				null,
				"nameC",
				AttributeType.TEXT,
				"{\"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameC' must have a maximum length of 5 characters");
		invalidValueForInstance(
				"abc.ca",
				null,
				"nameCA",
				AttributeType.TEXT,
				"{\"mandatory\" : true, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameCA' attribute is mandatory");
		invalidValueForInstance(
				"abc.d",
				null,
				"nameD",
				AttributeType.TEXT,
				"{\"minlength\" : 5, \"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameD' must have a maximum length of 5 characters");
		invalidValueForInstance(
				"abc.da",
				null,
				"nameDA",
				AttributeType.TEXT,
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

		updateOneValueOfInstanceAndVerifyOneException("abc", "a", "email",
				AttributeType.TEXT, null, null, "cba@cba.com", null);

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"b",
				"email",
				AttributeType.TEXT,
				"{\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}",
				"abc@abc.com", "cba@cba.com", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "c", "email",
				AttributeType.TEXT, "{\"default\": \"someone@nanuvem.com\"}",
				null, "fernando@nanuvem.com", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "d", "email",
				AttributeType.TEXT, "{\"minlength\": 10}", null,
				"fernando@nanuvem.com", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "e", "email",
				AttributeType.TEXT, "{\"maxlength\": 20}", null,
				"fernando@nanuvem.com", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "f", "email",
				AttributeType.TEXT, "{\"maxlength\": 20, \"minlength\":20}",
				null, "fernando@nanuvem.com", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "g", "email",
				AttributeType.TEXT, "{\"mandatory\": true}",
				"someone@nanuvem.com", "fernando@nanuvem.com", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "h", "email",
				AttributeType.TEXT, "{\"mandatory\": false}", null,
				"fernando@nanuvem.com", null);

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"i",
				"email",
				AttributeType.TEXT,
				"{\"mandatory\": false, \"maxlength\": 11, \"minlength\":11, "
						+ "\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}",
				"abc@abc.com", "cba@cba.com", null);
	}

	@Test
	public void expectAnExceptionWhenUpdatingAttributeValueWithInvalidValue() {

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"a",
				"email",
				AttributeType.TEXT,
				"{\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\"}",
				"abc@abc.com",
				"someone@nanuvem.com",
				"Invalid value for the Instance. The value for the 'email' attribute does not meet the defined regular expression");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"b",
				"email",
				AttributeType.TEXT,
				"{\"minlength\": 50}",
				null,
				"fernando@nanuvem.com",
				"Invalid value for the Instance. The value for 'email' must have a minimum length of 50 characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"c",
				"email",
				AttributeType.TEXT,
				"{\"maxlength\": 10}",
				null,
				"fernando@nanuvem.com",
				"Invalid value for the Instance. The value for 'email' must have a maximum length of 10 characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"d",
				"email",
				AttributeType.TEXT,
				"{\"mandatory\": true}",
				"fernando@nanuvem.com",
				null,
				"Invalid value for the Instance. The value for the 'email' attribute is mandatory");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"e",
				"email",
				AttributeType.TEXT,
				"{\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\", \"minlength\":11, \"maxlength\": 11, \"mandatory\":true}",
				"abc@abc.com",
				"some.one@nanuvem.com",
				"Invalid value for the Instance. "
						+ "The value for the 'email' attribute does not meet the defined regular expression, "
						+ "The value for 'email' must have a maximum length of 11 characters");
	}
}