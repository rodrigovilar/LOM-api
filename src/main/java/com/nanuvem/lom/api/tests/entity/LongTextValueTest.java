package com.nanuvem.lom.api.tests.entity;

import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.tests.LomTestCase;

import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.*;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.*;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.*;

public abstract class LongTextValueTest extends LomTestCase {
	@Test
	public void instanceWithValidValuesForTheConfigurationOfAttributesLongText() {
		createEntityType("abc", "a");
		createOnePropertyType("abc.a", null, "name", Type.LONGTEXT,
				"{\"mandatory\": true}");
		createAndVerifyOneEntity("abc.a", "Jose");

		createEntityType("abc", "b");
		createOnePropertyType("abc.b", null, "name", Type.LONGTEXT,
				"{\"default\" : \"default longtext\"}");
		createAndVerifyOneEntity("abc.b", (String) null);

		createEntityType("abc", "c");
		createOnePropertyType("abc.c", null, "name", Type.LONGTEXT,
				"{\"default\" : \"default longtext\"}");
		createAndVerifyOneEntity("abc.c", "Jose");

		createEntityType("abc", "d");
		createOnePropertyType("abc.d", null, "name", Type.LONGTEXT,
				"{\"minlength\" : 6}");
		createAndVerifyOneEntity("abc.d", "Johson");

		createEntityType("abc", "e");
		createOnePropertyType("abc.e", null, "name", Type.LONGTEXT,
				"{\"mandatory\" : true, \"minlength\" : 6}");
		createAndVerifyOneEntity("abc.e", "Johson");

		createEntityType("abc", "f");
		createOnePropertyType("abc.f", null, "name", Type.LONGTEXT,
				"{\"mandatory\" : true, \"default\":\"Johson\", \"minlength\" : 6}");
		createAndVerifyOneEntity("abc.f", (String) null);

		createEntityType("abc", "g");
		createOnePropertyType("abc.g", null, "name", Type.LONGTEXT,
				"{\"mandatory\" : true, \"default\":\"Johson\", \"minlength\" : 6}");
		createAndVerifyOneEntity("abc.g", "abccab");

		createEntityType("abc", "h");
		createOnePropertyType("abc.h", null, "name", Type.LONGTEXT,
				"{\"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.h", (String) null);

		createEntityType("abc", "i");
		createOnePropertyType("abc.i", null, "name", Type.LONGTEXT,
				"{\"mandatory\" : true, \"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.i", "Johson");

		createEntityType("abc", "j");
		createOnePropertyType("abc.j", null, "name", Type.LONGTEXT,
				"{\"mandatory\" : true, \"default\" : \"Johson\", \"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.j", (String) null);

		createEntityType("abc", "k");
		createOnePropertyType(
				"abc.k",
				null,
				"name",
				Type.LONGTEXT,
				"{\"mandatory\" : true, \"default\" : \"Johson\", \"minlength\" : 6, \"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.k", (String) null);

		createEntityType("abc", "l");
		createOnePropertyType(
				"abc.l",
				null,
				"name",
				Type.LONGTEXT,
				"{\"mandatory\" : true, \"default\" : \"Johson\", \"minlength\" : 6, \"maxlength\" : 6}");
		createAndVerifyOneEntity("abc.l", "abccba");
	}

	@Test
	public void instanceWithInvalidValuesForConfigurationOfLongTextAttributes() {
		createEntityType("abc", "a");
		createEntityType("abc", "b");
		createEntityType("abc", "ba");
		createEntityType("abc", "c");
		createEntityType("abc", "ca");
		createEntityType("abc", "d");
		createEntityType("abc", "e");

		invalidValueForEntity(
				"abc.a",
				null,
				"nameA",
				Type.LONGTEXT,
				"{\"mandatory\" : true}",
				null,
				"Invalid value for the Instance. The value for the 'nameA' attribute is mandatory");
		invalidValueForEntity(
				"abc.b",
				null,
				"nameB",
				Type.LONGTEXT,
				"{\"minlength\" : 5}",
				"abcd",
				"Invalid value for the Instance. The value for 'nameB' must have a minimum length of 5 characters");
		invalidValueForEntity(
				"abc.ba",
				null,
				"nameBA",
				Type.LONGTEXT,
				"{\"mandatory\" : true, \"minlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameBA' attribute is mandatory, "
						+ "The value for 'nameBA' must have a minimum length of 5 characters");
		invalidValueForEntity(
				"abc.c",
				null,
				"nameC",
				Type.LONGTEXT,
				"{\"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameC' must have a maximum length of 5 characters");
		invalidValueForEntity(
				"abc.ca",
				null,
				"nameCA",
				Type.LONGTEXT,
				"{\"mandatory\" : true, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameCA' attribute is mandatory");
		invalidValueForEntity(
				"abc.d",
				null,
				"nameD",
				Type.LONGTEXT,
				"{\"minlength\" : 5, \"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameD' must have a maximum length of 5 characters");
		invalidValueForEntity(
				"abc.e",
				null,
				"nameE",
				Type.LONGTEXT,
				"{\"mandatory\" : true, \"minlength\" : 5, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameE' attribute is mandatory, "
						+ "The value for 'nameE' must have a minimum length of 5 characters");
	}

	@Test
	public void updateValueOfAttributeValueWithValidValuesAndDifferentAttributeConfigurations() {

		updateOneValueOfEntityAndVerifyOneException("abc", "a",
				"descryption", Type.LONGTEXT, null, null,
				"Here must contain a description, written by a long text", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "b",
				"descryption", Type.LONGTEXT,
				"{\"default\": \"Nothing to say\"}", null,
				"Here must contain a description, written by a long text", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "c",
				"descryption", Type.LONGTEXT, "{\"minlength\": 10}",
				null,
				"Here must contain a description, written by a long text", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "d",
				"descryption", Type.LONGTEXT, "{\"maxlength\": 100}",
				null,
				"Here must contain a description, written by a long text", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "e",
				"descryption", Type.LONGTEXT,
				"{\"maxlength\": 55, \"minlength\":55}", null,
				"Here must contain a description, written by a long text", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "f",
				"descryption", Type.LONGTEXT, "{\"mandatory\": true}",
				"Nothing to say",
				"Here must contain a description, written by a long text", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "g",
				"descryption", Type.LONGTEXT,
				"{\"mandatory\": false}", null,
				"Here must contain a description, written by a long text", null);

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"h",
				"descryption",
				Type.LONGTEXT,
				"{\"mandatory\": false, \"minlength\": 11, \"maxlength\": 300}",
				"Nothing to say",
				"Here must contain a description, written by a long text", null);
	}

	@Test
	public void expectAnExceptionWhenUpdatingAttributeValueWithInvalidValue() {

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"a",
				"descryption",
				Type.LONGTEXT,
				"{\"minlength\": 50}",
				null,
				"Some long text",
				"Invalid value for the Instance. The value for 'descryption' must have a minimum length of 50 characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"b",
				"descryption",
				Type.LONGTEXT,
				"{\"maxlength\": 10}",
				null,
				"Some long text",
				"Invalid value for the Instance. The value for 'descryption' must have a maximum length of 10 characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"c",
				"descryption",
				Type.LONGTEXT,
				"{\"mandatory\": true}",
				"Nothing to say",
				null,
				"Invalid value for the Instance. The value for the 'descryption' attribute is mandatory");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"d",
				"descryption",
				Type.LONGTEXT,
				"{\"minlength\":14, \"maxlength\": 14, \"mandatory\":true}",
				"Nothing to say",
				"Here must contain a description, written by a long text",
				"Invalid value for the Instance. The value for 'descryption' must have a maximum length of 14 characters");
	}

}