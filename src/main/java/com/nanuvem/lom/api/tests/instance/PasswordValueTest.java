package com.nanuvem.lom.api.tests.instance;

import org.junit.Test;

import com.nanuvem.lom.api.AttributeType;
import com.nanuvem.lom.api.tests.LomTestCase;

import static com.nanuvem.lom.api.tests.instance.InstanceHelper.*;
import static com.nanuvem.lom.api.tests.attribute.AttributeHelper.*;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.*;

public abstract class PasswordValueTest extends LomTestCase {

	@Test
	public void instanceWithValidValuesForTheConfigurationOfAttributesPassword() {
		createEntity("abc", "a");
		createOneAttribute("abc.a", null, "secretKey", AttributeType.PASSWORD,
				"{\"default\": \"password\"}");
		createAndVerifyOneInstance("abc.a", (String) null);

		createEntity("abc", "b");
		createOneAttribute("abc.b", null, "secretKey", AttributeType.PASSWORD,
				"{\"minUppers\": 2}");
		createAndVerifyOneInstance("abc.b", "SecretKey");

		createEntity("abc", "c");
		createOneAttribute("abc.c", null, "secretKey", AttributeType.PASSWORD,
				"{\"minNumbers\": 2}");
		createAndVerifyOneInstance("abc.c", "1secretkey2");

		createEntity("abc", "d");
		createOneAttribute("abc.d", null, "secretKey", AttributeType.PASSWORD,
				"{\"minSymbols\": 2}");
		createAndVerifyOneInstance("abc.d", "&secretkey%");

		createEntity("abc", "e");
		createOneAttribute("abc.e", null, "secretKey", AttributeType.PASSWORD,
				"{\"maxRepeat\": 2}");
		createAndVerifyOneInstance("abc.e", "secretkey");

		createEntity("abc", "f");
		createOneAttribute("abc.f", null, "secretKey", AttributeType.PASSWORD,
				"{\"minlength\": 6}");
		createAndVerifyOneInstance("abc.f", "secret");

		createEntity("abc", "g");
		createOneAttribute("abc.g", null, "secretKey", AttributeType.PASSWORD,
				"{\"maxlength\": 6}");
		createAndVerifyOneInstance("abc.g", "secret");

		createEntity("abc", "h");
		createOneAttribute("abc.h", null, "secretKey", AttributeType.PASSWORD,
				"{\"mandatory\": true}");
		createAndVerifyOneInstance("abc.h", "secret");

		createEntity("abc", "i");
		createOneAttribute("abc.i", null, "secretKey", AttributeType.PASSWORD,
				"{\"mandatory\": false}");
		createAndVerifyOneInstance("abc.i", (String) null);

		createEntity("abc", "j");
		createOneAttribute("abc.j", null, "secretKey", AttributeType.PASSWORD,
				"{\"mandatory\": true, \"default\": \"mypassword\"}");
		createAndVerifyOneInstance("abc.j", (String) null);

		createEntity("abc", "k");
		createOneAttribute(
				"abc.k",
				null,
				"secretKey",
				AttributeType.PASSWORD,
				"{\"mandatory\": true, \"default\": \"P@ss%W04\", \"minlength\": 6, "
						+ "\"maxlength\": 9, \"minUppers\": 2, \"minNumbers\": 2, "
						+ "\"minSymbols\": 2, \"maxRepeat\": 2}");
		createAndVerifyOneInstance("abc.k", "0tH3r@$Ss");
	}

	@Test
	public void instanceWithInvalidValuesForTheConfigurationOfAttributesPassword() {
		createEntity("abc", "a");
		createEntity("abc", "b");
		createEntity("abc", "ba");
		createEntity("abc", "c");
		createEntity("abc", "ca");
		createEntity("abc", "d");
		createEntity("abc", "da");
		createEntity("abc", "e");
		createEntity("abc", "f");
		createEntity("abc", "g");
		createEntity("abc", "h");
		createEntity("abc", "i");
		createEntity("abc", "j");

		invalidValueForInstance(
				"abc.a",
				null,
				"nameA",
				AttributeType.PASSWORD,
				"{\"mandatory\" : true}",
				null,
				"Invalid value for the Instance. The value for the 'nameA' attribute is mandatory");
		invalidValueForInstance(
				"abc.b",
				null,
				"nameB",
				AttributeType.PASSWORD,
				"{\"minlength\" : 5}",
				"abcd",
				"Invalid value for the Instance. The value for 'nameB' must have a minimum length of 5 characters");
		invalidValueForInstance(
				"abc.ba",
				null,
				"nameBA",
				AttributeType.PASSWORD,
				"{\"mandatory\" : true, \"minlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameBA' attribute is mandatory, "
						+ "The value for 'nameBA' must have a minimum length of 5 characters");
		invalidValueForInstance(
				"abc.c",
				null,
				"nameC",
				AttributeType.PASSWORD,
				"{\"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameC' must have a maximum length of 5 characters");
		invalidValueForInstance(
				"abc.ca",
				null,
				"nameCA",
				AttributeType.PASSWORD,
				"{\"mandatory\" : true, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameCA' attribute is mandatory");
		invalidValueForInstance(
				"abc.d",
				null,
				"nameD",
				AttributeType.PASSWORD,
				"{\"minlength\" : 5, \"maxlength\" : 5}",
				"abcdef",
				"Invalid value for the Instance. The value for 'nameD' must have a maximum length of 5 characters");
		invalidValueForInstance(
				"abc.da",
				null,
				"nameDA",
				AttributeType.PASSWORD,
				"{\"mandatory\" : true, \"minlength\" : 5, \"maxlength\" : 5}",
				"",
				"Invalid value for the Instance. The value for the 'nameDA' attribute is mandatory, "
						+ "The value for 'nameDA' must have a minimum length of 5 characters");
		invalidValueForInstance(
				"abc.e",
				null,
				"nameE",
				AttributeType.PASSWORD,
				"{\"minUppers\" : 3}",
				"ABcdef",
				"Invalid value for the Instance. The value for 'nameE' must have at least 3 uppercase characters");
		invalidValueForInstance(
				"abc.f",
				null,
				"nameF",
				AttributeType.PASSWORD,
				"{\"minNumbers\" : 3}",
				"abc12def",
				"Invalid value for the Instance. The value for 'nameF' must be at least 3 numbers");
		invalidValueForInstance(
				"abc.g",
				null,
				"nameG",
				AttributeType.PASSWORD,
				"{\"minSymbols\" : 3}",
				"ab%c12def*",
				"Invalid value for the Instance. The value for 'nameG' must have at least 3 symbol characters");
		invalidValueForInstance(
				"abc.h",
				null,
				"nameH",
				AttributeType.PASSWORD,
				"{\"maxRepeat\" : 0}",
				"ab%c1a2e*",
				"Invalid value for the Instance. The value for 'nameH' must not have repeated characters");
		invalidValueForInstance(
				"abc.i",
				null,
				"nameI",
				AttributeType.PASSWORD,
				"{\"maxRepeat\" : 1}",
				"ab%ac1a2e*",
				"Invalid value for the Instance. The value for 'nameI' must not have more than 2 repeated characters");
		invalidValueForInstance(
				"abc.j",
				null,
				"nameJ",
				AttributeType.PASSWORD,
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

		updateOneValueOfInstanceAndVerifyOneException("abc", "a", "secretKey",
				AttributeType.PASSWORD, null, null,
				"5f6eca57fc12718a639e3433bb02a7c5", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "b", "secretKey",
				AttributeType.PASSWORD, "{\"mandatory\": false}",
				"5f6eca57fc12718a639e3433bb02a7c5", null, null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "c", "secretKey",
				AttributeType.PASSWORD, "{\"mandatory\": true}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6asd", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "d", "secretKey",
				AttributeType.PASSWORD,
				"{\"default\": \"5f6eca57fc12718a639e3433bb02a7c5\"}", null,
				"9e107d9d372bb6826bd81d3542a419d6asd", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "e", "secretKey",
				AttributeType.PASSWORD, "{\"minlength\": 32}", null,
				"9e107d9d372bb6826bd81d3542a419d6asd", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "f", "secretKey",
				AttributeType.PASSWORD, "{\"maxlength\": 32}", null,
				"9e107d9d372bb6826bd81d3542a419d6", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "g", "secretKey",
				AttributeType.PASSWORD, "{\"minUppers\": 2}", null,
				"9e107d9d372bB6826bD81d3542a419d6", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "h", "secretKey",
				AttributeType.PASSWORD, "{\"minNumbers\": 2}", null,
				"9e107d9d372bB6826bD81d3542a419d6", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "i", "secretKey",
				AttributeType.PASSWORD, "{\"maxRepeat\": 10}", null,
				"%9e107d9d372bB6826bD81d3542a419d6&", null);

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"j",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"mandatory\": true, \"minlength\": 32, \"maxlength\": 32, \"minNumbers\": 4, \"minSymbols\": 2, \"maxRepeat\": 10}",
				"5f6eca57FC12718a639e3433bb02a7&%",
				"%9e107d9d372bB66bD81d3542a419d6&", null);
	}

	@Test
	public void expectAnExceptionWhenUpdatingAttributeValueWithInvalidValue() {

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"a",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"mandatory\": true}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				null,
				"Invalid value for the Instance. The value for the 'secretKey' attribute is mandatory");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"b",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"minlength\": 34}",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"Invalid value for the Instance. The value for 'secretKey' must have a minimum length of 34 characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"c",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"maxlength\": 32}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"Invalid value for the Instance. The value for 'secretKey' must have a maximum length of 32 characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"d",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"minlength\": 30, \"maxlength\": 32}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"Invalid value for the Instance. The value for 'secretKey' must have a maximum length of 32 characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"e",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"minUppers\": 2}",
				"5f6ECA57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"Invalid value for the Instance. The value for 'secretKey' must have at least 2 uppercase characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"f",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"minNumbers\": 22}",
				"9e107d9d372bb6826bd81d3542a419d6asd",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"Invalid value for the Instance. The value for 'secretKey' must be at least 22 numbers");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"g",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"minSymbols\": 2}",
				"9e&107d9%d372bb6826bd81d3542a419d6asd",
				"5f6eca57%fc12718a639e3433bb02a7c5",
				"Invalid value for the Instance. The value for 'secretKey' must have at least 2 symbol characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"h",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"maxRepeat\": 2}",
				"9e&107d%d",
				"33bbb2a7c5",
				"Invalid value for the Instance. The value for 'secretKey' must not have more than 2 repeated characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"i",
				"secretKey",
				AttributeType.PASSWORD,
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