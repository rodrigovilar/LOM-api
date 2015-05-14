package com.nanuvem.lom.api.tests.propertytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityHelper.createEntity;
import static com.nanuvem.lom.api.tests.propertytype.AttributeHelper.createAndVerifyOneAttribute;
import static com.nanuvem.lom.api.tests.propertytype.AttributeHelper.expectExceptionOnCreateInvalidAttribute;

import org.junit.Ignore;
import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class ObjectAttributeTest extends LomTestCase {

    @Test
    @Ignore
    public void validConfigurationForObjectAttributeType() {
//        createEntity("abc", "a");
//
//        createAndVerifyOneAttribute("abc.a", 1, "pa", AttributeType.OBJECT, "{\"schema\":OBJECT WITH ONE ATTRIBUTE}");
//
//        createAndVerifyOneAttribute("abc.a", 1, "pb", AttributeType.OBJECT, "{\"schema\":OBJECT WITH TWO ATTRIBUTE}");
//
//        createAndVerifyOneAttribute("abc.a", 1, "pc", AttributeType.OBJECT,
//                "{\"schema\":ARRAY OF OBJECTS WITH TWO ATTRIBUTES}");
//
//        createAndVerifyOneAttribute("abc.a", 1, "pd", AttributeType.OBJECT, "{\"schema\":OBJECT WITH ONE SUB OBJECT}");
//
//        createAndVerifyOneAttribute("abc.a", 1, "pa", AttributeType.OBJECT,
//                "{\"default\":OBJECT WITH ONE ATTRIBUTE, \"schema\":OBJECT WITH ONE ATTRIBUTE}");
//
//        createAndVerifyOneAttribute("abc.a", 1, "pb", AttributeType.OBJECT,
//                "{\"default\":OBJECT WITH TWO ATTRIBUTES, \"schema\":OBJECT WITH TWO ATTRIBUTE}");
//
//        createAndVerifyOneAttribute("abc.a", 1, "pc", AttributeType.OBJECT,
//                "{\"default\":ARRAY OF OBJECTS WITH TWO ATTRIBUTES, \"schema\":ARRAY OF OBJECTS WITH TWO ATTRIBUTES}");
//
//        createAndVerifyOneAttribute("abc.a", 1, "pd", AttributeType.OBJECT,
//                "{\"default\":OBJECT WITH ONE SUB OBJECT, \"schema\":OBJECT WITH ONE SUB OBJECT}");
    }

    @Test
    @Ignore
    public void invalidConfigurationForObjectAttributeType() {
		// createEntity("abc", "a");
		//
		// expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa",
		// AttributeType.OBJECT,
		// "{\"default\":10}",
		// "Invalid configuration for attribute pa: the default value must be a string");
		//
		// expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa",
		// AttributeType.OBJECT,
		// "{\"default\":'ABC'}",
		// "Invalid configuration for attribute pa: the default value must use JSON format");
		//
		// expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa",
		// AttributeType.OBJECT,
		// "{\"default\":'{}', \"schema\":OBJECT WITH ONE ATTRIBUTE}",
		// "Invalid configuration for attribute pa: the default value must match the JSON schema");
		//
		// expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa",
		// AttributeType.OBJECT,
		// "{\"default\":OBJECT WITH A WRONG ATTRIBUTE NAME, \"schema\":OBJECT WITH ONE ATTRIBUTE}",
		// "Invalid configuration for attribute pa: the default value must match the JSON schema");
		//
		// expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa",
		// AttributeType.OBJECT,
		// "{\"default\":OBJECT WITH A WRONG ATTRIBUTE TYPE, \"schema\":OBJECT WITH ONE ATTRIBUTE}",
		// "Invalid configuration for attribute pa: the default value must match the JSON schema");

    }
}