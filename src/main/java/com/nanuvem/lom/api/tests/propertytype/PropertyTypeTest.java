package com.nanuvem.lom.api.tests.propertytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createEntityType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.ATTRIBUTE_DUPLICATION;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.ATTRIBUTE_IS_MANDATORY;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.ENTITY_NOT_FOUND;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.INVALID_SEQUENCE;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.LONGTEXT;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.MANDATORY_FALSE;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.MANDATORY_TRUE;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.TEXT;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.createAndVerifyOnePropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.createOnePropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.expectExceptionOnCreateInvalidPropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.expectExceptionOnUpdateInvalidPropertyType;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.expectExceptionOnUpdateWithInvalidNewName;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.updateAttribute;
import static com.nanuvem.lom.api.tests.propertytype.PropertyTypeHelper.verifyUpdatedPropertyType;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;

import com.nanuvem.lom.api.PropertyType;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class PropertyTypeTest extends LomTestCase {


    @Test
    public void validAttributeData() {
        createEntityType("abc", "a");
        createAndVerifyOnePropertyType("abc.a", 1, "pa", TEXT, MANDATORY_TRUE);
        createAndVerifyOnePropertyType("abc.a", 1, "pe", LONGTEXT, MANDATORY_FALSE);
        createAndVerifyOnePropertyType("abc.a", 1, "pi", TEXT, "");
        createAndVerifyOnePropertyType("abc.a", 1, "po", TEXT, null);
    }

    @Test
    public void invalidAttributeData() {
        createEntityType("abc", "a");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 0, "pa", TEXT, MANDATORY_TRUE, INVALID_SEQUENCE, "0");
        expectExceptionOnCreateInvalidPropertyType("abc.a", -1, "pa", TEXT, MANDATORY_TRUE, INVALID_SEQUENCE, "-1");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 2, "pa", TEXT, MANDATORY_TRUE, INVALID_SEQUENCE, "2");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "", TEXT, MANDATORY_TRUE, ATTRIBUTE_IS_MANDATORY, "name");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", null, MANDATORY_TRUE, ATTRIBUTE_IS_MANDATORY, "type");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "pa", TEXT, "ABC",
                "Invalid value for Attribute configuration: ABC");
    }

    @Test
    public void invalidAttributeName() {
        createEntityType("abc", "a");

        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "aaa$", TEXT, MANDATORY_TRUE, INVALID_VALUE_FOR_ATTRIBUTE,
                "name", "aaa$");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "aaa#", TEXT, MANDATORY_TRUE, INVALID_VALUE_FOR_ATTRIBUTE,
                "name", "aaa#");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "aaa=", TEXT, MANDATORY_TRUE, INVALID_VALUE_FOR_ATTRIBUTE,
                "name", "aaa=");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "aaa'", TEXT, MANDATORY_TRUE, INVALID_VALUE_FOR_ATTRIBUTE,
                "name", "aaa'");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "aaa.a", TEXT, MANDATORY_TRUE, INVALID_VALUE_FOR_ATTRIBUTE,
                "name", "aaa.a");
        expectExceptionOnCreateInvalidPropertyType("abc.a", 1, "aaa*", TEXT, MANDATORY_TRUE, INVALID_VALUE_FOR_ATTRIBUTE,
                "name", "aaa*");
    }

    @Test
    @Ignore
    public void validateSeveralAttributesInTheSameEntity() {
        createEntityType("abc", "a");

        createAndVerifyOnePropertyType("abc.a", null, "pa", TEXT, "");
        Assert.assertEquals(new Integer(1), facade.findPropertyTypeByNameAndFullnameEntityType("pa", "abc.a").getSequence());

        createAndVerifyOnePropertyType("abc.a", null, "pb", LONGTEXT, "");
        Assert.assertEquals(new Integer(2), facade.findPropertyTypeByNameAndFullnameEntityType("pb", "abc.a").getSequence());

        createEntityType("", "b");

        createAndVerifyOnePropertyType("b", new Integer(1), "pa", LONGTEXT, "");
        createAndVerifyOnePropertyType("b", new Integer(1), "pb", LONGTEXT, "");
        Assert.assertEquals(new Integer(2), facade.findPropertyTypeByNameAndFullnameEntityType("pa", "b").getSequence());
        Assert.assertEquals(new Integer(1), facade.findPropertyTypeByNameAndFullnameEntityType("pb", "b").getSequence());

        createEntityType("", "c");

        createAndVerifyOnePropertyType("c", new Integer(1), "pa", TEXT, "");
        createAndVerifyOnePropertyType("c", new Integer(2), "pb", LONGTEXT, "");
        createAndVerifyOnePropertyType("c", new Integer(2), "pc", LONGTEXT, "");
        Assert.assertEquals(new Integer(1), facade.findPropertyTypeByNameAndFullnameEntityType("pa", "c").getSequence());
        Assert.assertEquals(new Integer(3), facade.findPropertyTypeByNameAndFullnameEntityType("pb", "c").getSequence());
        Assert.assertEquals(new Integer(2), facade.findPropertyTypeByNameAndFullnameEntityType("pc", "c").getSequence());
    }

    @Test
    public void validateAttributeDuplicationInTheSameEntity() {
        createEntityType("abc", "a");
        createAndVerifyOnePropertyType("abc.a", null, "pa", TEXT, "");

        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", LONGTEXT, "", ATTRIBUTE_DUPLICATION, "abc.a", "pa");
        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pa", TEXT, "", ATTRIBUTE_DUPLICATION, "abc.a", "pa");
        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "pA", TEXT, "", ATTRIBUTE_DUPLICATION, "abc.a", "pa");
        expectExceptionOnCreateInvalidPropertyType("abc.a", null, "PA", TEXT, "", ATTRIBUTE_DUPLICATION, "abc.a", "pa");
    }

    @Test
    public void invalidEntity() {
        expectExceptionOnCreateInvalidPropertyType("a", null, "abc123", TEXT, "", ENTITY_NOT_FOUND, "a");

        createEntityType("abc", "a");

        expectExceptionOnCreateInvalidPropertyType("abca", null, "abc123", TEXT, "", ENTITY_NOT_FOUND, "abca");
        expectExceptionOnCreateInvalidPropertyType("a", null, "abc123", TEXT, "", ENTITY_NOT_FOUND, "a");
        expectExceptionOnCreateInvalidPropertyType("abc.b", null, "abc123", TEXT, "", ENTITY_NOT_FOUND, "abc.b");

        createEntityType("", "b");

        expectExceptionOnCreateInvalidPropertyType("a", null, "abc123", null, "", ENTITY_NOT_FOUND, "a");
        expectExceptionOnCreateInvalidPropertyType("abc.b", null, "abc123", null, "", ENTITY_NOT_FOUND, "abc.b");
    }

    @Test
    public void validNewName() {
        createEntityType("abc", "a");
        PropertyType createdAttribute = createOnePropertyType("abc.a", null, "pa", LONGTEXT, null);

        PropertyType updatedAttribute = updateAttribute("abc.a", createdAttribute, 1, "pb", LONGTEXT, null);
        verifyUpdatedPropertyType(createdAttribute, updatedAttribute);
    }

    @Test
    public void invalidNewName() {
        createEntityType("abc", "a");
        PropertyType createdAttribute = createOnePropertyType("abc.a", null, "pa", LONGTEXT, null);
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "", "The name of an Attribute is mandatory");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, null, "The name of an Attribute is mandatory");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "p a", "Invalid value for Attribute name: p a");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "a$", "Invalid value for Attribute name: a$");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "a#", "Invalid value for Attribute name: a#");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "a=", "Invalid value for Attribute name: a=");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "a'", "Invalid value for Attribute name: a'");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "a.", "Invalid value for Attribute name: a.");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "a.a", "Invalid value for Attribute name: a.a");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "a/a", "Invalid value for Attribute name: a/a");
        expectExceptionOnUpdateWithInvalidNewName(createdAttribute, "a*", "Invalid value for Attribute name: a*");
    }

    @Test
    @Ignore
    public void validNewSequence() {
        createEntityType("abc", "a");
        PropertyType createdAttribute1 = createOnePropertyType("abc.a", null, "pa", TEXT, null);
        PropertyType createdAttribute2 = createOnePropertyType("abc.a", null, "pb", TEXT, null);

        PropertyType updatedAttribute1 = updateAttribute("abc.a", createdAttribute1, 2, "pa", TEXT, null);
        verifyUpdatedPropertyType(createdAttribute1, updatedAttribute1);

        PropertyType updatedAttribute2 = updateAttribute("abc.a", createdAttribute2, 2, "pb", TEXT, null);
        verifyUpdatedPropertyType(createdAttribute2, updatedAttribute2);

        PropertyType updatedAttribute3 = updateAttribute("abc.a", updatedAttribute2, 1, "pb", TEXT, null);
        verifyUpdatedPropertyType(updatedAttribute2, updatedAttribute3);
    }

    @Test
    @Ignore
    public void invalidNewSequence() {
        createEntityType("abc", "a");

        PropertyType createdAttribute = createOnePropertyType("abc.a", null, "pa", TEXT, null);

        createOnePropertyType("abc.a", null, "pb", TEXT, null);

        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, null, "pa", LONGTEXT, null,
                "Invalid value for Attribute sequence: null");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, -1, "pa", LONGTEXT, null,
                "Invalid value for Attribute sequence: -1");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 0, "pa", LONGTEXT, null,
                "Invalid value for Attribute sequence: 0");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 3, "pa", LONGTEXT, null,
                "Invalid value for Attribute sequence: 3");
    }

    @Test
    public void changeType() {
        createEntityType("abc", "a");
        PropertyType createdAttribute = createOnePropertyType("abc.a", null, "pa", TEXT, null);

        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", null, null,
                "Can not change the type of an attribute");
        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute, 1, "pa", LONGTEXT, null,
                "Can not change the type of an attribute");
    }

    @Test
    public void renamingConflicts() {
        createEntityType("abc", "a");
        createEntityType("abc", "b");

        createOnePropertyType("abc.a", null, "pa", TEXT, null);

        PropertyType createdAttribute2 = createOnePropertyType("abc.b", null, "pb", TEXT, null);
        PropertyType createdAttribute3 = createOnePropertyType("abc.a", null, "pc", TEXT, null);

        PropertyType updatedAttribute2 = updateAttribute("abc.b", createdAttribute2, 1, "pa", TEXT, null);
        verifyUpdatedPropertyType(createdAttribute2, updatedAttribute2);

        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute3, 1, "pa", TEXT, null,
                "Attribute duplication on abc.a Entity. It already has an attribute pa.");

        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute3, 1, "pA", TEXT, null,
                "Attribute duplication on abc.a Entity. It already has an attribute pa.");

        expectExceptionOnUpdateInvalidPropertyType("abc.a", createdAttribute3, 1, "PA", TEXT, null,
                "Attribute duplication on abc.a Entity. It already has an attribute pa.");
    }

    @Test
    public void genericChangeConfiguration() {
        createEntityType("abc", "a");

        PropertyType createdAttribute1 = createOnePropertyType("abc.a", null, "pa", TEXT, MANDATORY_TRUE);

        PropertyType createdAttribute2 = createOnePropertyType("abc.a", null, "pb", TEXT, null);

        PropertyType updatedAttribute11 = updateAttribute("abc.a", createdAttribute1, 1, "pa", TEXT,
                "{\"mandatory\":false}");
        verifyUpdatedPropertyType(createdAttribute1, updatedAttribute11);

        PropertyType updatedAttribute12 = updateAttribute("abc.a", updatedAttribute11, 2, "pa", TEXT, null);
        verifyUpdatedPropertyType(updatedAttribute11, updatedAttribute12);

        PropertyType updatedAttribute2 = updateAttribute("abc.a", createdAttribute2, 2, "pb", TEXT, MANDATORY_TRUE);
        verifyUpdatedPropertyType(createdAttribute2, updatedAttribute2);
    }

}