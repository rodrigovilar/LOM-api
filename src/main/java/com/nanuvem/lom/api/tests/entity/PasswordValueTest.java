package com.nanuvem.lom.api.tests.entity;

import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.tests.LomTestCase;

import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.*;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.*;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.*;

public abstract class PasswordValueTest extends LomTestCase {

	@Test
	public void instanceWithValidValuesForTheConfigurationOfAttributesPassword() {
		createEntityType("abc", "a");
		createOnePropertyType("abc.a", null, "secretKey", Type.PASSWORD,
				"{\"default\": \"password\"}");
		createAndVerifyOneEntity("abc.a", (String) null);

		createEntityType("abc", "b");
		createOnePropertyType("abc.b", null, "secretKey", Type.PASSWORD,
				"{\"minUppers\": 2}");
		createAndVerifyOneEntity("abc.b", "SecretKey");

		createEntityType("abc", "c");
		createOnePropertyType("abc.c", null, "secretKey", Type.PASSWORD,
				"{\"minNumbers\": 2}");
		createAndVerifyOneEntity("abc.c", "1secretkey2");

		createEntityType("abc", "d");
		createOnePropertyType("abc.d", null, "secretKey", Type.PASSWORD,
				"{\"minSymbols\": 2}");
		createAndVerifyOneEntity("abc.d", "&secretkey%");

		createEntityType("abc", "e");
		createOnePropertyType("abc.e", null, "secretKey", Type.PASSWORD,
				"{\"maxRepeat\": 2}");
		createAndVerifyOneEntity("abc.e", "secretkey");

		createEntityType("abc", "f");
		createOnePropertyType("abc.f", null, "secretKey", Type.PASSWORD,
				"{\"minlength\": 6}");
		createAndVerifyOneEntity("abc.f", "secret");

		createEntityType("abc", "g");
		createOnePropertyType("abc.g", null, "secretKey", Type.PASSWORD,
				"{\"maxlength\": 6}");
		createAndVerifyOneEntity("abc.g", "secret");

		createEntityType("abc", "h");
		createOnePropertyType("abc.h", null, "secretKey", Type.PASSWORD,
				"{\"mandatory\": true}");
		createAndVerifyOneEntity("abc.h", "secret");

		createEntityType("abc", "i");
		createOnePropertyType("abc.i", null, "secretKey", Type.PASSWORD,
				"{\"mandatory\": false}");
		createAndVerifyOneEntity("abc.i", (String) null);

		createEntityType("abc", "j");
		createOnePropertyType("abc.j", null, "secretKey", Type.PASSWORD,
				"{\"mandatory\": true, \"default\": \"mypassword\"}");
		createAndVerifyOneEntity("abc.j", (String) null);

		createEntityType("abc", "k");
		createOnePropertyType(
				"abc.k",
				null,
				"secretKey",
				Type.PASSWORD,
				"{\"mandatory\": true, \"default\": \"P@ss%W04\", \"minlength\": 6, "
						+ "\"maxlength\": 9, \"minUppers\": 2, \"minNumbers\": 2, "
						+ "\"minSymbols\": 2, \"maxRepeat\": 2}");
		createAndVerifyOneEntity("abc.k", "0tH3r@$Ss");
	}

	@Test
	public void instanceWithInvalidValuesForTheConfigurationOfAttributesPassword() {
		createEntityType("abc", "a");
		createEntityType("abc", "b");
		createEntityType("abc", "ba");
		createEntityType("abc", "c");
		createEntityType("abc", "ca");
		createEntityType("abc", "d");
		createEntityType("abc", "da");
		createEntityType("abc", "e");
		createEntityType("abc", "f");
		createEntityType("abc", "g");
		createEntityType("abc", "h");
		createEntityType("abc", "i");
		createEntityType("abc", "j");

		invalidValueForEntity(
				"abc.a",
				null,
				"nameA",
				Type.PASSWORD,
				"{\"mandatory\" : true}",
				null,
				"Invalid value for the Instance. The value for the 'nameA' attribute is mandatory");
		invalidValueForEntity(
				"abc.b",
				null,
				"nameB",
				Type.PASSWORD,
				"{\"minlength\" : 5}",
				"abcd",
				"Invalid value for the Instance. The value for 'nameB' must have a minimum length of 5 characters");
		invalidValueForEntity(
				"abc.ba",
				null,
				"nameBA",
				Type.PASSWORD,
				"{\"mandatory\" : true, \"minlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameBA' attribute is mandatory, "
						+ "The value for 'nameBA' must have a minimum length of 5 characters");
		invalidValueForEntity(
				"abc.c",
				null,
				"nameC",
				Type.PASSWORD,
				"{\"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameC' must have a maximum length of 5 characters");
		invalidValueForEntity(
				"abc.ca",
				null,
				"nameCA",
				Type.PASSWORD,
				"{\"mandatory\" : true, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameCA' attribute is mandatory");
		invalidValueForEntity(
				"abc.d",
				null,
				"nameD",
				Type.PASSWORD,
				"{\"minlength\" : 5, \"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameD' must have a maximum length of 5 characters");
		invalidValueForEntity(
				"abc.da",
				null,
				"nameDA",
				Type.PASSWORD,
				"{\"mandatory\" : true, \"minlength\" : 5, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameDA' attribute is mandatory, "
						+ "The value for 'nameDA' must have a minimum length of 5 characters");
		invalidValueForEntity(
				"abc.e",
				null,
				"nameE",
				Type.PASSWORD,
				"{\"minUppers\" : 3}",
				"ABcdef",
				"Invalid value for the Instance. The value for 'nameE' must have at least 3 uppercase characters");
		invalidValueForEntity(
				"abc.f",
				null,
				"nameF",
				Type.PASSWORD,
				"{\"minNumbers\" : 3}",
				"abc12def",
				"Invalid value for the Instance. The value for 'nameF' must be at least 3 numbers");
		invalidValueForEntity(
				"abc.g",
				null,
				"nameG",
				Type.PASSWORD,
				"{\"minSymbols\" : 3}",
				"ab%c12def*",
				"Invalid value for the Instance. The value for 'nameG' must have at least 3 symbol characters");
		invalidValueForEntity(
				"abc.h",
				null,
				"nameH",
				Type.PASSWORD,
				"{\"maxRepeat\" : 0}",
				"ab%c1a2e*",
				"Invalid value for the Instance. The value for 'nameH' must not have repeated characters");
		invalidValueForEntity(
				"abc.i",
				null,
				"nameI",
				Type.PASSWORD,
				"{\"maxRepeat\" : 1}",
				"ab%ac1a2e*",
				"Invalid value for the Instance. The value for 'nameI' must not have more than 2 repeated characters");
		invalidValueForEntity(
				"abc.j",
				null,
				"nameJ",
				Type.PASSWORD,
				"{\"mandatory\": true, \"minlength\": 4, \"maxlength\": 4, \"minUppers\" : 3, \"minNumbers\" : 2, "
						+ "\"minSymbols\" : 1, \"maxRepeat\" : 0}",
				"ab1CfdeFa",
				"Invalid value for the Instance. The value for 'nameJ' must have a maximum length of 4 characters, "
						+ "The value for 'nameJ' must have at least 3 uppercase characters, "
						+ "The value for 'nameJ' must be at least 2 numbers, "
						+ "The value for 'nameJ' must have at least 1 symbol character, "
						+ "The value for 'nameJ' must not have repeated characters");
	}

	@Test
	public void updateValueOfAttributeValueWithValidValuesAndDifferentAttributeConfigurations() {

		updateOneValueOfEntityAndVerifyOneException("abc", "a", "secretKey",
				Type.PASSWORD, null, null,
				"5f6eca57fc12718a639e3433bb02a7c5", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "b", "secretKey",
				Type.PASSWORD, "{\"mandatory\": false}",
				"5f6eca57fc12718a639e3433bb02a7c5", null, null);

		updateOneValueOfEntityAndVerifyOneException("abc", "c", "secretKey",
				Type.PASSWORD, "{\"mandatory\": true}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6asd", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "d", "secretKey",
				Type.PASSWORD,
				"{\"default\": \"5f6eca57fc12718a639e3433bb02a7c5\"}", null,
				"9e107d9d372bb6826bd81d3542a419d6asd", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "e", "secretKey",
				Type.PASSWORD, "{\"minlength\": 32}", null,
				"9e107d9d372bb6826bd81d3542a419d6asd", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "f", "secretKey",
				Type.PASSWORD, "{\"maxlength\": 32}", null,
				"9e107d9d372bb6826bd81d3542a419d6", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "g", "secretKey",
				Type.PASSWORD, "{\"minUppers\": 2}", null,
				"9e107d9d372bB6826bD81d3542a419d6", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "h", "secretKey",
				Type.PASSWORD, "{\"minNumbers\": 2}", null,
				"9e107d9d372bB6826bD81d3542a419d6", null);

		updateOneValueOfEntityAndVerifyOneException("abc", "i", "secretKey",
				Type.PASSWORD, "{\"maxRepeat\": 10}", null,
				"%9e107d9d372bB6826bD81d3542a419d6&", null);

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"j",
				"secretKey",
				Type.PASSWORD,
				"{\"mandatory\": true, \"minlength\": 32, \"maxlength\": 32, \"minNumbers\": 4, \"minSymbols\": 2, \"maxRepeat\": 10}",
				"5f6eca57FC12718a639e3433bb02a7&%",
				"%9e107d9d372bB66bD81d3542a419d6&", null);
	}

	@Test
	public void expectAnExceptionWhenUpdatingAttributeValueWithInvalidValue() {

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"a",
				"secretKey",
				Type.PASSWORD,
				"{\"mandatory\": true}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				null,
				"Invalid value for the Instance. The value for the 'secretKey' attribute is mandatory");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"b",
				"secretKey",
				Type.PASSWORD,
				"{\"minlength\": 34}",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"Invalid value for the Instance. The value for 'secretKey' must have a minimum length of 34 characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"c",
				"secretKey",
				Type.PASSWORD,
				"{\"maxlength\": 32}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"Invalid value for the Instance. The value for 'secretKey' must have a maximum length of 32 characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"d",
				"secretKey",
				Type.PASSWORD,
				"{\"minlength\": 30, \"maxlength\": 32}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"Invalid value for the Instance. The value for 'secretKey' must have a maximum length of 32 characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"e",
				"secretKey",
				Type.PASSWORD,
				"{\"minUppers\": 2}",
				"5f6ECA57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"Invalid value for the Instance. The value for 'secretKey' must have at least 2 uppercase characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"f",
				"secretKey",
				Type.PASSWORD,
				"{\"minNumbers\": 22}",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"Invalid value for the Instance. The value for 'secretKey' must be at least 22 numbers");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"g",
				"secretKey",
				Type.PASSWORD,
				"{\"minSymbols\": 2}",
				"9e&107d9%d372bb6826bd81d3542a419d6asd",
				"5f6eca57%fc12718a639e3433bb02a7c5",
				"Invalid value for the Instance. The value for 'secretKey' must have at least 2 symbol characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"h",
				"secretKey",
				Type.PASSWORD,
				"{\"maxRepeat\": 2}",
				"9e&107d%d",
				"33bbb2a7c5",
				"Invalid value for the Instance. The value for 'secretKey' must not have more than 2 repeated characters");

		updateOneValueOfEntityAndVerifyOneException(
				"abc",
				"i",
				"secretKey",
				Type.PASSWORD,
				"{\"mandatory\": true, \"minlength\": 10, \"maxlength\": 50, \"minUppers\": 5, \"minNumbers\": 3, "
						+ "\"minSymbols\": 2, \"maxRepeat\": 1}",
				"E&1A7D90%DC",
				"1aa2ab&D",
				"Invalid value for the Instance. "
						+ "The value for 'secretKey' must have a minimum length of 10 characters, "
						+ "The value for 'secretKey' must have at least 5 uppercase characters, "
						+ "The value for 'secretKey' must be at least 3 numbers, "
						+ "The value for 'secretKey' must have at least 2 symbol characters, "
						+ "The value for 'secretKey' must not have more than 2 repeated characters");
	}
}