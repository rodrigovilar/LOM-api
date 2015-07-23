package com.nanuvem.lom.api.tests.propertytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createEntityType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.MANDATORY_TRUE;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.TEXT;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.createAndVerifyOnePropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.createOnePropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.expectExceptionOnCreateInvalidPropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.expectExceptionOnUpdateInvalidPropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.updateAttribute;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.verifyUpdatedPropertyType;

import org.junit.Test;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class TextPropertyTypeTest extends LomTestCase {

    private static final String TEXT_CONFIGURATION_PARTIAL = "{\"minlength\": 5,\"maxlength\": 15}";

    private static final String TEXT_CONFIGURATION_COMPLETE = "{\"mandatory\": false, \"default\": \"abc@abc.com\", "
            + "\"regex\": \"(\\\\w[-._\\\\w]\\\\w@\\\\w[-.\\\\w]*\\\\w.\\\\w{2,3})\", "
            + "\"minlength\": 5,\"maxlength\": 15}";


    @Test
    public void validateConfigurationForTextAttributeType() {
        createEntityType("abc", "a");

        createAndVerifyOnePropertyType("abc.a", 1, "pa", TEXT,
                "{\"regex\":\"(\\\\w[-.\\\\w]*\\\\w@\\\\w[-.\\\\w]\\\\w.\\\\w{2,3})\"}");

        createAndVerifyOnePropertyType("abc.a", 1, "pb", TEXT, "{\"minlength\":10}");

        createAndVerifyOnePropertyType("abc.a", 1, "pc", TEXT, "{\"minlength\":100}");

        createAndVerifyOnePropertyType("abc.a", 1, "pd", TEXT, "{\"mandatory\": true, \"regex\": "
                + "\"(\\\\w[-._\\\\w]\\\\w@\\\\w[-.\\\\w]*\\\\w.\\\\w{2,3})\", "
                + "\"minlength\": 5,\"maxlength\": 15}");

        createAndVerifyOnePropertyType("abc.a", 1, "pe", TEXT, "");

        createAndVerifyOnePropertyType("abc.a", 1, "pf", TEXT,
                "{\"default\": \"abc@abc.com\",\"regex\": \"(\\\\w[-.\\\\w]\\\\w@\\\\w[-._\\\\w]\\\\w.\\\\w{2,3})\","
                        + "\"minlength\": 5,\"maxlength\": 15}");

        createAndVerifyOnePropertyType("abc.a", 1, "pg", TEXT, "{\"default\":\"abc\"}");

    }

    @Test
    public void invalidConfigurationForTextAttributeType() {
        createEntityType("abc", "a");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "{\"mandatory\":10}",
                "Invalid configuration for attribute pa: the mandatory value must be true or false literals");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "{\"mandatory\":\"true\"}",
                "Invalid configuration for attribute pa: the mandatory value must be true or false literals");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "{\"default\":10}",
                "Invalid configuration for attribute pa: the default value must be a string");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "{\"regex\":10}",
                "Invalid configuration for attribute pa: the regex value must be a string");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "{\"minlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "{\"minlength\":10.0}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "{\"maxlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "{\"maxlength\":10.0}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT,
                "{\"default\":\"abc\", \"regex\":\"(\\\\w[-.\\\\w]*\\\\w@\\\\w[-.\\\\w]*\\\\w.\\\\w{2,3})\"}",
                "Invalid configuration for attribute pa: the default value does not match regex configuration");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT,
                "{\"default\":\"abc\", \"minlength\":5}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT,
                "{\"default\":\"abcabc\", \"maxlength\":5}",
                "Invalid configuration for attribute pa: the default value is greater than maxlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT,
                "{\"minlength\":50, \"maxlength\":10}",
                "Invalid configuration for attribute pa: the minlength is greater than maxlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT,
                "{\"default\":\"abc\", \"minlength\":9, \"maxlength\":50}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");
    }

    @Test
    public void validChangeConfigurationForTextAttributeType() {
        createEntityType("abc", "a");

        PropertyType createdAttribute1 = createOnePropertyType("abc.a", null, "pa", TEXT, MANDATORY_TRUE);
        PropertyType createdAttribute2 = createOnePropertyType("abc.a", null, "pb", TEXT, TEXT_CONFIGURATION_COMPLETE);

        PropertyType updatedAttribute11 = updateAttribute("abc.a", createdAttribute1, null, "pa", TEXT,
                TEXT_CONFIGURATION_PARTIAL);
        verifyUpdatedPropertyType(createdAttribute1, updatedAttribute11);

        PropertyType updatedAttribute12 = updateAttribute("abc.a", updatedAttribute11, null, "pa", TEXT,
                TEXT_CONFIGURATION_COMPLETE);
        verifyUpdatedPropertyType(updatedAttribute11, updatedAttribute12);

        PropertyType updatedAttribute21 = updateAttribute("abc.a", createdAttribute2, null, "pb", TEXT,
                TEXT_CONFIGURATION_PARTIAL);
        verifyUpdatedPropertyType(createdAttribute2, updatedAttribute21);

        PropertyType updatedAttribute22 = updateAttribute("abc.a", updatedAttribute21, null, "pb", TEXT,
                MANDATORY_TRUE);
        verifyUpdatedPropertyType(updatedAttribute21, updatedAttribute22);

        PropertyType updatedAttribute23 = updateAttribute("abc.a", updatedAttribute22, null, "pb", TEXT,
                "{\"default\":\"abc\"}");
        verifyUpdatedPropertyType(updatedAttribute22, updatedAttribute23);

        PropertyType updatedAttribute24 = updateAttribute("abc.a", updatedAttribute23, null, "pb", TEXT,
                "{\"default\":\"123\"}");
        verifyUpdatedPropertyType(updatedAttribute23, updatedAttribute24);
    }

    @Test
    public void invalidChangeConfigurationForTextAttributeType() {
        createEntityType("abc", "a");
        PropertyType createdAttribute = createOnePropertyType("abc.a", null, "pa", TEXT, null);

        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"mandatory\":10}",
                "Invalid configuration for attribute pa: the mandatory value must be true or false literals");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"mandatory\":\"true\"}",
                "Invalid configuration for attribute pa: the mandatory value must be true or false literals");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"default\":10}",
                "Invalid configuration for attribute pa: the default value must be a string");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"regex\":10}",
                "Invalid configuration for attribute pa: the regex value must be a string");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"minlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"minlength\":10.0}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"maxlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"maxlength\":10.0}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT,
                "{\"default\":\"abc\", \"regex\":\"(\\\\w[-.\\\\w]*\\\\w@\\\\w[-.\\\\w]*\\\\w.\\\\w{2,3})\"}",
                "Invalid configuration for attribute pa: the default value does not match regex configuration");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT,
                "{\"default\":\"abc\", \"minlength\":5}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT,
                "{\"default\":\"abcabc\", \"maxlength\":5}",
                "Invalid configuration for attribute pa: the default value is greater than maxlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT,
                "{\"minlength\":50, \"maxlength\":10}",
                "Invalid configuration for attribute pa: the minlength is greater than maxlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT,
                "{\"default\":\"abc\", \"minlength\":9, \"maxlength\":50}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", TEXT, "{\"anyconf\":\"abc\"}",
                "Invalid configuration for attribute pa: the anyconf configuration attribute is unknown");
    }
}