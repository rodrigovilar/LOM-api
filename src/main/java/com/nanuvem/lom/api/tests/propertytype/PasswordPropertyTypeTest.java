package com.nanuvem.lom.api.tests.propertytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createEntityType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.MANDATORY_TRUE;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.PASSWORD;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.createAndVerifyOnePropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.createOnePropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.expectExceptionOnCreateInvalidPropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.expectExceptionOnUpdateInvalidPropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.updateAttribute;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.verifyUpdatedPropertyType;

import org.junit.Test;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class PasswordPropertyTypeTest extends LomTestCase {

    private static final String PASSWORD_CONFIGURATION_PARTIAL = "{\"minlength\": 5,\"maxlength\": 15}";

    private static final String PASSWORD_CONFIGURATION_COMPLETE = "{\"mandatory\": false, "
            + "\"default\": \"Abc12@abd.com\", " + "\"minUppers\": 1, " + "\"minNumbers\": 2, " + "\"minSymbols\": 1, "
            + "\"maxRepeat\": 1," + "\"minlength\": 5," + "\"maxlength\": 15}";


    @Test
    public void validConfigurationForPasswordAttributeType() {
        createEntityType("abc", "a");

        createAndVerifyOnePropertyType("abc.a", 1, "pa", PASSWORD, "{\"minlength\":10}");

        createAndVerifyOnePropertyType("abc.a", 1, "pb", PASSWORD, "{\"maxlength\":100000}");

        createAndVerifyOnePropertyType("abc.a", 1, "pc", PASSWORD, "{\"maxlength\":100000}");

        createAndVerifyOnePropertyType("abc.a", 1, "pd", PASSWORD, "{\"minUppers\":1}");

        createAndVerifyOnePropertyType("abc.a", 1, "pe", PASSWORD, "{\"minNumbers\":2}");

        createAndVerifyOnePropertyType("abc.a", 1, "pf", PASSWORD, "{\"minSymbols\":3}");

        createAndVerifyOnePropertyType("abc.a", 1, "pg", PASSWORD, "{\"maxRepeat\":2}");

        createAndVerifyOnePropertyType("abc.a", 1, "ph", PASSWORD,
                "{\"mandatory\":true, \"minlength\":5, \"maxlength\":12, "
                        + "\"minUppers\":1, \"minNumbers\":2, \"minSymbols\":3, " + "\"maxRepeat\":2}");

        createAndVerifyOnePropertyType("abc.a", 1, "pi", PASSWORD, "");
        createAndVerifyOnePropertyType("abc.a", 1, "pj", PASSWORD, "{\"default\":\"Abcdef\", \"minUppers\":1}");

        createAndVerifyOnePropertyType("abc.a", 1, "pk", PASSWORD, "{\"default\":\"abcdef10\", \"minNumbers\":2}");

        createAndVerifyOnePropertyType("abc.a", 1, "pl", PASSWORD, "{\"default\":\"abcdef!@#\", \"minSymbols\":3}");

        createAndVerifyOnePropertyType("abc.a", 1, "pm", PASSWORD, "{\"default\":\"aabcdef\", \"maxRepeat\":2}");

        createAndVerifyOnePropertyType("abc.a", 1, "pn", PASSWORD,
                "{\"default\":\"Abbcdef12!@#\", \"minUppers\":1, "
                        + "\"minNumbers\":2, \"minSymbols\":3, \"maxRepeat\":2}");
    }

    @Test
    public void invalidConfigurationForPasswordAttributeType() {
        createEntityType("abc", "a");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"default\":10}",
                "Invalid configuration for attribute pa: the default value must be a string");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"minlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"minlength\":10.0}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"maxlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"maxlength\":10.0}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"minUppers\":\"abc\"}",
                "Invalid configuration for attribute pa: the minUppers value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"minUppers\":10.0}",
                "Invalid configuration for attribute pa: the minUppers value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"minNumbers\":\"abc\"}",
                "Invalid configuration for attribute pa: the minNumbers value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"minNumbers\":10.0}",
                "Invalid configuration for attribute pa: the minNumbers value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"minSymbols\":\"abc\"}",
                "Invalid configuration for attribute pa: the minSymbols value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"minSymbols\":10.0}",
                "Invalid configuration for attribute pa: the minSymbols value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"maxRepeat\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxRepeat value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD, "{\"maxRepeat\":10.0}",
                "Invalid configuration for attribute pa: the maxRepeat value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abc\", \"minlength\":5}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abcabc\", \"maxlength\":5}",
                "Invalid configuration for attribute pa: the default value is greater than maxlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"minlength\":50, \"maxlength\":10}",
                "Invalid configuration for attribute pa: the minlength is greater than maxlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abc\", \"minlength\":9, \"maxlength\":10}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abcdef\", \"minUppers\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 1 upper case character");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abcDef\", \"minUppers\":2}",
                "Invalid configuration for attribute pa: the default value must have at least 2 upper case characters");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abcdef\", \"minNumbers\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 1 numerical character");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abc1\", \"minNumbers\":2}",
                "Invalid configuration for attribute pa: the default value must have at least 2 numerical characters");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abcdef\", \"minSymbols\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 1 symbol character");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"!abc\", \"minSymbols\":2}",
                "Invalid configuration for attribute pa: the default value must have at least 2 symbol characters");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"aabcdef\", \"maxRepeat\":0}",
                "Invalid configuration for attribute pa: the default value must not have repeated characters");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                        "{\"default\":\"abcccd\", \"maxRepeat\":1}",
                        "Invalid configuration for attribute pa: the default value must not have more than 2 repeated characters");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                        "{\"default\":\"abccccd\", \"maxRepeat\":2}",
                        "Invalid configuration for attribute pa: the default value must not have more than 3 repeated characters");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"Abcdef1!@\", \"minUppers\":1, \"minNumbers\":1, \"minSymbols\":3,\"maxRepeat\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 3 symbol characters");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", PASSWORD,
                "{\"default\":\"abccc\", \"minUppers\":1, \"minNumbers\":2, \"minSymbols\":3,\"maxRepeat\":1}",
                "Invalid configuration for attribute pa: "
                        + "the default value must have at least 1 upper case character, "
                        + "the default value must have at least 2 numerical characters, "
                        + "the default value must have at least 3 symbol characters, "
                        + "the default value must not have more than 2 repeated characters");
    }

    @Test
    public void validChangeConfigurationForPasswordAttributeType() {
        createEntityType("abc", "a");

        PropertyType createdAttribute1 = createOnePropertyType("abc.a", null, "pa", PASSWORD, MANDATORY_TRUE);
        PropertyType createdAttribute2 = createOnePropertyType("abc.a", null, "pb", PASSWORD, PASSWORD_CONFIGURATION_COMPLETE);

        PropertyType updatedAttribute11 = updateAttribute("abc.a", createdAttribute1, 1, "pa", PASSWORD,
                PASSWORD_CONFIGURATION_PARTIAL);
        verifyUpdatedPropertyType(createdAttribute1, updatedAttribute11);

        PropertyType updatedAttribute12 = updateAttribute("abc.a", updatedAttribute11, 1, "pa", PASSWORD,
                PASSWORD_CONFIGURATION_COMPLETE);
        verifyUpdatedPropertyType(updatedAttribute11, updatedAttribute12);

        PropertyType updatedAttribute21 = updateAttribute("abc.a", createdAttribute2, 2, "pb", PASSWORD,
                PASSWORD_CONFIGURATION_PARTIAL);
        verifyUpdatedPropertyType(createdAttribute2, updatedAttribute21);

        PropertyType updatedAttribute22 = updateAttribute("abc.a", updatedAttribute21, 2, "pb", PASSWORD,
                MANDATORY_TRUE);
        verifyUpdatedPropertyType(updatedAttribute21, updatedAttribute22);

        PropertyType updatedAttribute23 = updateAttribute("abc.a", updatedAttribute22, 2, "pb", PASSWORD,
                "{\"default\":\"abc\"}");
        verifyUpdatedPropertyType(updatedAttribute22, updatedAttribute23);

        PropertyType updatedAttribute24 = updateAttribute("abc.a", updatedAttribute23, 2, "pb", PASSWORD,
                "{\"default\":\"123\"}");
        verifyUpdatedPropertyType(updatedAttribute23, updatedAttribute24);
    }

    @Test
    public void invalidChangeConfigurationForPaswordAttributeType() {
        createEntityType("abc", "a");
        PropertyType createdAttribute = createOnePropertyType("abc.a", null, "pa", PASSWORD, null);

        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"mandatory\":10}",
                "Invalid configuration for attribute pa: the mandatory value must be true or false literals");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"mandatory\":\"true\"}",
                "Invalid configuration for attribute pa: the mandatory value must be true or false literals");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"default\":10}",
                "Invalid configuration for attribute pa: the default value must be a string");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"minlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"minlength\":10.0}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"maxlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"maxlength\":10.0}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abc\", \"minlength\":5}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abcabc\", \"maxlength\":5}",
                "Invalid configuration for attribute pa: the default value is greater than maxlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"minlength\":50, \"maxlength\":10}",
                "Invalid configuration for attribute pa: the minlength is greater than maxlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abc\", \"minlength\":9, \"maxlength\":50}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"minUppers\":\"abc\"}",
                "Invalid configuration for attribute pa: the minUppers value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"minUppers\":10.0}",
                "Invalid configuration for attribute pa: the minUppers value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"minNumbers\":\"abc\"}",
                "Invalid configuration for attribute pa: the minNumbers value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"minNumbers\":10.0}",
                "Invalid configuration for attribute pa: the minNumbers value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"minSymbols\":\"abc\"}",
                "Invalid configuration for attribute pa: the minSymbols value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"minSymbols\":10.0}",
                "Invalid configuration for attribute pa: the minSymbols value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"maxRepeat\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxRepeat value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"maxRepeat\":10.0}",
                "Invalid configuration for attribute pa: the maxRepeat value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abcdef\", \"minUppers\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 1 upper case character");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abcDef\", \"minUppers\":2}",
                "Invalid configuration for attribute pa: the default value must have at least 2 upper case characters");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abcdef\", \"minNumbers\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 1 numerical character");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abc1\", \"minNumbers\":2}",
                "Invalid configuration for attribute pa: the default value must have at least 2 numerical characters");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abcdef\", \"minSymbols\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 1 symbol character");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"!abc\", \"minSymbols\":2}",
                "Invalid configuration for attribute pa: the default value must have at least 2 symbol characters");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"aabcdef\", \"maxRepeat\":0}",
                "Invalid configuration for attribute pa: the default value must not have repeated characters");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abcccd\", \"maxRepeat\":1}",
                "Invalid configuration for attribute pa: the default value must not have more than 2 repeated characters");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"Abcdef12!@b\", \"minUppers\":1, \"minNumbers\":2, \"minSymbols\":3, \"maxRepeat\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 3 symbol characters");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD,
                "{\"default\":\"abccc\", \"minUppers\":1, \"minNumbers\":2, \"minSymbols\":3, \"maxRepeat\":1}",
                "Invalid configuration for attribute pa: the default value must have at least 1 upper case "
                        + "character, the default value must have at least 2 numerical characters, the default value "
                        + "must have at least 3 symbol characters, the default value must not have more than 2 "
                        + "repeated characters");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", PASSWORD, "{\"anyconf\":\"abc\"}",
                "Invalid configuration for attribute pa: the anyconf configuration attribute is unknown");
    }


}