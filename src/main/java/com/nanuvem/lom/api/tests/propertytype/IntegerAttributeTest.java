package com.nanuvem.lom.api.tests.propertytype;

import static com.nanuvem.lom.api.tests.entity.InstanceHelper.*;
import static com.nanuvem.lom.api.tests.entitytype.EntityHelper.createEntity;
import static com.nanuvem.lom.api.tests.propertytype.AttributeHelper.createAndVerifyOneAttribute;
import static com.nanuvem.lom.api.tests.propertytype.AttributeHelper.expectExceptionOnCreateInvalidAttribute;

import org.junit.Test;

import com.nanuvem.lom.api.Type;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class IntegerAttributeTest extends LomTestCase {

    @Test
    public void validConfigurationForIntegerAttributeType() {
        createEntity("abc", "a");

        createAndVerifyOneAttribute("abc.a", 1, "p1", Type.INTEGER, "{\"minvalue\":-5}");

        createAndVerifyOneAttribute("abc.a", 1, "p2", Type.INTEGER, "{\"maxvalue\":100000000000}");

        createAndVerifyOneAttribute("abc.a", 1, "p3", Type.INTEGER,
                "{\"mandatory\":true, \"default\":10, \"minvalue\":5, \"maxvalue\":150000}");

        createAndVerifyOneAttribute("abc.a", 1, "p4", Type.INTEGER, "");
    }

    @Test
    public void invalidConfigurationForIntegerAttributeType() {
        createEntity("abc", "a");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER, "{\"default\":\"abc\"}",
                "Invalid configuration for attribute pa: the default value must be an integer number");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER, "{\"minvalue\":\"abc\"}",
                "Invalid configuration for attribute pa: the minvalue value must be an integer number");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER, "{\"minvalue\":10.0}",
                "Invalid configuration for attribute pa: the minvalue value must be an integer number");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER, "{\"maxvalue\":\"abc\"}",
                "Invalid configuration for attribute pa: the maxvalue value must be an integer number");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER, "{\"maxvalue\":10.0}",
                "Invalid configuration for attribute pa: the maxvalue value must be an integer number");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER,
                "{\"default\":3, \"minvalue\":5}",
                "Invalid configuration for attribute pa: the default value is smaller than minvalue");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER,
                "{\"default\":12, \"maxvalue\":10}",
                "Invalid configuration for attribute pa: the default value is greater than maxvalue");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER,
                "{\"minvalue\":50, \"maxvalue\":10}",
                "Invalid configuration for attribute pa: the minvalue is greater than maxvalue");

        expectExceptionOnCreateInvalidAttribute("abc.a", null, "pa", Type.INTEGER,
                "{\"default\":7, \"minvalue\":9, \"maxvalue\":10}",
                "Invalid configuration for attribute pa: the default value is smaller than minvalue");
    }
    
    

}