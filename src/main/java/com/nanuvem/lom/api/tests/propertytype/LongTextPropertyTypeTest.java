package com.nanuvem.lom.api.tests.propertytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createEntityType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.*;

import org.junit.Test;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class LongTextPropertyTypeTest extends LomTestCase {

    private static final String LONGTEXT_CONFIGURATION_PARTIAL = "{\"minlength\": 5,\"maxlength\": 15}";

    private static final String LONGTEXT_CONFIGURATION_COMPLETE = "{\"mandatory\": false, \"default\": \"long text\", "
            + "\"minlength\": 5,\"maxlength\": 15}";


    @Test
    public void validConfigurationForLongTextAttributeType() {
        createEntityType("abc", "a");

        createAndVerifyOnePropertyType("abc.a", 1, "p1", LONGTEXT, "{\"minlength\":10}");

        createAndVerifyOnePropertyType("abc.a", 1, "p2", LONGTEXT, "{\"maxlength\":100000}");

        createAndVerifyOnePropertyType("abc.a", 1, "p3", LONGTEXT,
                "{\"mandatory\":true, \"default\":\"long text\", \"minlength\":5, \"maxlength\":150000}");

        createAndVerifyOnePropertyType("abc.a", 1, "p4", LONGTEXT, "");
    }

    @Test
    public void invalidConfigurationForLongTextAttributeType() {
        createEntityType("abc", "a");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT, "{\"default\":10}",
                "Invalid configuration for attribute pa: the default value must be a string");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT, "{\"minlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT, "{\"minlength\":10.0}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT, "{\"maxlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT, "{\"maxlength\":10.0}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT, "{\"default\":\"abc\", \"minlength\":5}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT,
                "{\"default\":\"abcabc\", \"maxlength\":5}",
                "Invalid configuration for attribute pa: the default value is greater than maxlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT, "{\"minlength\":50, \"maxlength\":10}",
                "Invalid configuration for attribute pa: the minlength is greater than maxlength");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", LONGTEXT,
                "{\"default\":\"abc\",\"minlength\":9, \"maxlength\":10}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");
    }

    @Test
    public void validChangeConfigurationForLongTextAttributeType() {
        createEntityType("abc", "a");

        PropertyType createdAttribute1 = createOnePropertyType("abc.a", null, "pa", LONGTEXT, MANDATORY_TRUE);
        PropertyType createdAttribute2 = createOnePropertyType("abc.a", null, "pb", LONGTEXT, LONGTEXT_CONFIGURATION_COMPLETE);

        PropertyType updatedAttribute11 = updateAttribute("abc.a", createdAttribute1, 1, "pa", LONGTEXT,
                LONGTEXT_CONFIGURATION_PARTIAL);
        verifyUpdatedPropertyType(createdAttribute1, updatedAttribute11);

        PropertyType updatedAttribute12 = updateAttribute("abc.a", updatedAttribute11, 1, "pa", LONGTEXT,
                LONGTEXT_CONFIGURATION_COMPLETE);
        verifyUpdatedPropertyType(updatedAttribute11, updatedAttribute12);

        PropertyType updatedAttribute21 = updateAttribute("abc.a", createdAttribute2, 2, "pb", LONGTEXT,
                LONGTEXT_CONFIGURATION_PARTIAL);
        verifyUpdatedPropertyType(createdAttribute2, updatedAttribute21);

        PropertyType updatedAttribute22 = updateAttribute("abc.a", updatedAttribute21, 2, "pb", LONGTEXT,
                MANDATORY_TRUE);
        verifyUpdatedPropertyType(updatedAttribute21, updatedAttribute22);

        PropertyType updatedAttribute23 = updateAttribute("abc.a", updatedAttribute22, 2, "pb", LONGTEXT,
                "{\"default\":\"abc\"}");
        verifyUpdatedPropertyType(updatedAttribute22, updatedAttribute23);

        PropertyType updatedAttribute24 = updateAttribute("abc.a", updatedAttribute23, 2, "pb", LONGTEXT,
                "{\"default\":\"123\"}");
        verifyUpdatedPropertyType(updatedAttribute23, updatedAttribute24);

    }

    @Test
    public void invalidChangeConfigurationForLongTextAttributeType() {
        createEntityType("abc", "a");
        PropertyType createdAttribute = createOnePropertyType("abc.a", null, "pa", LONGTEXT, null);

        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT, "{\"mandatory\":10}",
                "Invalid configuration for attribute pa: the mandatory value must be true or false literals");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT,
                "{\"mandatory\":\"true\"}",
                "Invalid configuration for attribute pa: the mandatory value must be true or false literals");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT, "{\"default\":10}",
                "Invalid configuration for attribute pa: the default value must be a string");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT,
                "{\"minlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT, "{\"minlength\":10.0}",
                "Invalid configuration for attribute pa: the minlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT,
                "{\"maxlength\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT, "{\"maxlength\":10.0}",
                "Invalid configuration for attribute pa: the maxlength value must be an integer number");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT,
                "{\"default\":\"abc\", \"minlength\":5}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT,
                "{\"default\":\"abcabc\", \"maxlength\":5}",
                "Invalid configuration for attribute pa: the default value is greater than maxlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT,
                "{\"minlength\":50, \"maxlength\":10}",
                "Invalid configuration for attribute pa: the minlength is greater than maxlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT,
                "{\"default\":\"abc\", \"minlength\":9, \"maxlength\":50}",
                "Invalid configuration for attribute pa: the default value is smaller than minlength");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT, "{\"anyconf\":\"abc\"}",
                "Invalid configuration for attribute pa: the anyconf configuration attribute is unknown");
    }
}