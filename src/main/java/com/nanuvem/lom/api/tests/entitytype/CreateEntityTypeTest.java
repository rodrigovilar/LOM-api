package com.nanuvem.lom.api.tests.entitytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createAndSaveOneEntityType;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createAndVerifyOneEntityType;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createAndVerifyTwoEntitiesTypes;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.expectExceptionOnCreateInvalidEntityType;

import org.junit.Test;

import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class CreateEntityTypeTest extends LomTestCase {

    @Test
    public void validNameAndNamespace() {
        createAndVerifyOneEntityType("abc", "abc");
        createAndVerifyOneEntityType("a.b.c", "abc");
        createAndVerifyOneEntityType("a", "a");
        createAndVerifyOneEntityType("abc123", "aaa");
        createAndVerifyOneEntityType("abc", "abc1122");
    }

    @Test
    public void withoutNamespace() {
        createAndVerifyOneEntityType("", "abc");
        createAndVerifyOneEntityType(null, "abc");
        createAndVerifyOneEntityType("", "a1");
        createAndVerifyOneEntityType(null, "a1");
    }

    @Test
    public void twoEntitiesWithSameNameInDifferentNamespaces() {
        createAndVerifyTwoEntitiesTypes("p1", "name", "p2", "name");
        createAndVerifyTwoEntitiesTypes(null, "name", "p1", "name");
        createAndVerifyTwoEntitiesTypes("a", "name", "a.b", "name");
    }

    @Test
    public void nameAndNamespaceWithSpaces() {
        expectExceptionOnCreateInvalidEntityType("name space", "name", INVALID_VALUE_FOR_ENTITY, "namespace", "name space");
        expectExceptionOnCreateInvalidEntityType("namespace", "na me", INVALID_VALUE_FOR_ENTITY, "name", "na me");
    }

    @Test
    public void withoutName() {
        expectExceptionOnCreateInvalidEntityType("namespace", null, ENTITY_NAME_IS_MANDATORY);
        expectExceptionOnCreateInvalidEntityType("namespace", "", ENTITY_NAME_IS_MANDATORY);
        expectExceptionOnCreateInvalidEntityType(null, null, ENTITY_NAME_IS_MANDATORY);
        expectExceptionOnCreateInvalidEntityType("", null, ENTITY_NAME_IS_MANDATORY);
    }

    @Test
    public void twoEntitiesWithSameNameInDefaultNamespace() {
        createAndSaveOneEntityType(null, "aaa");
        expectExceptionOnCreateInvalidEntityType(null, "aaa", ENTITY_ALREADY_EXISTS, "aaa");
        expectExceptionOnCreateInvalidEntityType("", "aaa", ENTITY_ALREADY_EXISTS, "aaa");
    }

    @Test
    public void twoEntitiesWithSameNameInAnonDefaultNamespace() {
        createAndSaveOneEntityType("a", "aaa");
        expectExceptionOnCreateInvalidEntityType("a", "aaa", ENTITY_ALREADY_EXISTS, "a.aaa");
    }

    @Test
    public void nameAndNamespaceWithInvalidChars() {
        expectExceptionOnCreateInvalidEntityType("a", "aaa$", INVALID_VALUE_FOR_ENTITY, "name", "aaa$");
        expectExceptionOnCreateInvalidEntityType("a", "aaa#", INVALID_VALUE_FOR_ENTITY, "name", "aaa#");
        expectExceptionOnCreateInvalidEntityType("a", "aaa=", INVALID_VALUE_FOR_ENTITY, "name", "aaa=");
        expectExceptionOnCreateInvalidEntityType("a", "aaa.a", INVALID_VALUE_FOR_ENTITY, "name", "aaa.a");
        expectExceptionOnCreateInvalidEntityType("a", "aaa/a", INVALID_VALUE_FOR_ENTITY, "name", "aaa/a");
        expectExceptionOnCreateInvalidEntityType("a", "aaa*", INVALID_VALUE_FOR_ENTITY, "name", "aaa*");
        expectExceptionOnCreateInvalidEntityType("a", "aaa'", INVALID_VALUE_FOR_ENTITY, "name", "aaa'");
        expectExceptionOnCreateInvalidEntityType("a$", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a$");
        expectExceptionOnCreateInvalidEntityType("a#", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a#");
        expectExceptionOnCreateInvalidEntityType("a=", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a=");
        expectExceptionOnCreateInvalidEntityType("a'", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a'");
        // expectExceptionOnCreateInvalidEntity("a.",
        // "aaa",INVALID_VALUE_FOR_ENTITY, "namespace", "a.");
        expectExceptionOnCreateInvalidEntityType("a/a", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a/a");
        expectExceptionOnCreateInvalidEntityType("a*", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a*");
    }

}
