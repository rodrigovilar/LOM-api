package com.nanuvem.lom.api.tests.entitytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createAndSaveOneEntityType;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createUpdateAndVerifyOneEntityType;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.expectExceptionOnInvalidEntityTypeUpdate;

import org.junit.Assert;
import org.junit.Test;

import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class UpdateEntityTypeTest extends LomTestCase {

    @Test
    public void validNewNameAndPackage() {
        createUpdateAndVerifyOneEntityType("a", "aaa1", "b", "bbb");
        createUpdateAndVerifyOneEntityType("a", "aaa2", "a", "bbb");
        createUpdateAndVerifyOneEntityType("a", "aaa3", "b", "aaa");
        createUpdateAndVerifyOneEntityType("", "aaa1", "", "bbb");
        createUpdateAndVerifyOneEntityType(null, "aaa2", null, "bbb");
        createUpdateAndVerifyOneEntityType("a.b.c", "aaa1", "b", "bbb");
        createUpdateAndVerifyOneEntityType("a.b.c", "aaa2", "b.c", "bbb");
    }

    @Test
    public void removePackageSetPackage() {
        createUpdateAndVerifyOneEntityType("a", "aaa1", "", "aaa");
        createUpdateAndVerifyOneEntityType("a", "aaa2", "", "bbb");
        createUpdateAndVerifyOneEntityType("", "aaa1", "b", "bbb");
        createUpdateAndVerifyOneEntityType("a", "aaa3", null, "aaa");
        createUpdateAndVerifyOneEntityType("a", "aaa4", null, "bbb");
        createUpdateAndVerifyOneEntityType(null, "aaa2", "b", "bbb");
        createUpdateAndVerifyOneEntityType("a", "aaa5", "a", "aaa5");
        createUpdateAndVerifyOneEntityType("a", "aaa6", "a", "aaa7");
        createUpdateAndVerifyOneEntityType(null, "aaa3", null, "aaa4");
    }

    @Test
    public void renameCausingTwoEntitiesWithSameNameInDifferentPackages() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa");
        createAndSaveOneEntityType("b", "bbb");

        ea.setName("bbb");
        facade.update(ea);
    }

    @Test
    public void moveCausingTwoEntitiesWithSameNameInDifferentPackages() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa");
        createAndSaveOneEntityType("b", "bbb");

        ea.setNamespace("c");
        ea.setName("bbb");
        facade.update(ea);
    }

    @Test
    public void newNameAndPackageWithSpaces() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "name space", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace",
                "name space");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "namespace", "na me", INVALID_VALUE_FOR_ENTITY, "name", "na me");
    }

    @Test
    public void removeName() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "namespace", null, ENTITY_NAME_IS_MANDATORY);
        expectExceptionOnInvalidEntityTypeUpdate(ea, "namespace", "", ENTITY_NAME_IS_MANDATORY);
        expectExceptionOnInvalidEntityTypeUpdate(ea, null, null, ENTITY_NAME_IS_MANDATORY);
        expectExceptionOnInvalidEntityTypeUpdate(ea, null, "", ENTITY_NAME_IS_MANDATORY);
    }

    @Test
    public void renameMoveCausingTwoEntitiesWithSameNameInDefaultPackage() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa");
        createAndSaveOneEntityType("b", "bbb");
        createAndSaveOneEntityType("b", "aaa");
        createAndSaveOneEntityType("a", "bbb"); 

        expectExceptionOnInvalidEntityTypeUpdate(ea, "b", "bbb", ENTITY_ALREADY_EXISTS, "b.bbb");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "b", "aaa", ENTITY_ALREADY_EXISTS, "b.aaa");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a", "bbb", ENTITY_ALREADY_EXISTS, "a.bbb");

        EntityType e1 = createAndSaveOneEntityType("a.b.c", "aaa");
        EntityType e2 = createAndSaveOneEntityType("b.c", "aaa");
        EntityType e3 = createAndSaveOneEntityType("a.b.c", "bbb");
        createAndSaveOneEntityType("b.c", "bbb"); 

        expectExceptionOnInvalidEntityTypeUpdate(e1, "b.c", "bbb", ENTITY_ALREADY_EXISTS, "b.c.bbb");
        expectExceptionOnInvalidEntityTypeUpdate(e2, "b.c", "bbb", ENTITY_ALREADY_EXISTS, "b.c.bbb");
        expectExceptionOnInvalidEntityTypeUpdate(e3, "b.c", "bbb", ENTITY_ALREADY_EXISTS, "b.c.bbb");
    }

    @Test
    public void renameMoveCausingTwoEntitiesWithSameNameInAnonDefaultPackage() {
        EntityType ea1 = createAndSaveOneEntityType("a", "aaa");
        EntityType ea2 = createAndSaveOneEntityType(null, "aaa");
        EntityType ea3 = createAndSaveOneEntityType("a", "bbb");
        createAndSaveOneEntityType(null, "bbb"); 

        expectExceptionOnInvalidEntityTypeUpdate(ea1, null, "bbb", ENTITY_ALREADY_EXISTS, "bbb");
        expectExceptionOnInvalidEntityTypeUpdate(ea2, null, "bbb", ENTITY_ALREADY_EXISTS, "bbb");
        expectExceptionOnInvalidEntityTypeUpdate(ea3, null, "bbb", ENTITY_ALREADY_EXISTS, "bbb");

        EntityType ec1 = createAndSaveOneEntityType("a.b.c", "ccc");
        EntityType ec2 = createAndSaveOneEntityType("", "ccc");
        EntityType ec3 = createAndSaveOneEntityType("a.b.c", "ddd");
        createAndSaveOneEntityType("", "ddd"); 

        expectExceptionOnInvalidEntityTypeUpdate(ec1, "", "ddd", ENTITY_ALREADY_EXISTS, "ddd");
        expectExceptionOnInvalidEntityTypeUpdate(ec2, "", "ddd", ENTITY_ALREADY_EXISTS, "ddd");
        expectExceptionOnInvalidEntityTypeUpdate(ec3, "", "ddd", ENTITY_ALREADY_EXISTS, "ddd");
    }

    @Test
    public void renameMoveCausingNameAndPackagesWithInvalidChars() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a", "aaa$", INVALID_VALUE_FOR_ENTITY, "name", "aaa$");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a", "aaa#", INVALID_VALUE_FOR_ENTITY, "name", "aaa#");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a", "aaa=", INVALID_VALUE_FOR_ENTITY, "name", "aaa=");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a", "aaa'", INVALID_VALUE_FOR_ENTITY, "name", "aaa'");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a", "aaa.a", INVALID_VALUE_FOR_ENTITY, "name", "aaa.a");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a", "aaa/a", INVALID_VALUE_FOR_ENTITY, "name", "aaa/a");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a", "aaa*", INVALID_VALUE_FOR_ENTITY, "name", "aaa*");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a$", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a$");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a#", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a#");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a=", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a=");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a'", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a'");
        // expectExceptionOnInvalidEntityUpdate(ea, "a.", "aaa",
        // INVALID_VALUE_FOR_ENTITY, "namespace", "a.");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a/a", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a/a");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "a*", "aaa", INVALID_VALUE_FOR_ENTITY, "namespace", "a*");
    }

    @Test
    public void renameMoveForcingCaseInsentivePackagesAndNames() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa");
        createAndSaveOneEntityType("b", "bbb");
        createAndSaveOneEntityType("CcC", "ccc");
        createAndSaveOneEntityType("DDD", "ddd"); 

        expectExceptionOnInvalidEntityTypeUpdate(ea, "b", "BbB", ENTITY_ALREADY_EXISTS, "b.bbb");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "b", "BBB", ENTITY_ALREADY_EXISTS, "b.bbb");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "ccc", "ccc", ENTITY_ALREADY_EXISTS, "ccc.ccc");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "CCC", "ccc", ENTITY_ALREADY_EXISTS, "ccc.ccc");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "ddd", "ddd", ENTITY_ALREADY_EXISTS, "ddd.ddd");
        expectExceptionOnInvalidEntityTypeUpdate(ea, "ddd", "DDD", ENTITY_ALREADY_EXISTS, "ddd.ddd");
    }

    @Test
    public void invalidIdAndVersion() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa"); 
        Long originalId = ea.getId();
        Integer originalVersion = ea.getVersion();

        ea.setId(null);
        expectExceptionOnInvalidEntityTypeUpdate(ea, "namespace", "name", "The id of an Entity is mandatory on update");

        ea.setId(originalId);
        ea.setVersion(null);
        expectExceptionOnInvalidEntityTypeUpdate(ea, "namespace", "name", "The version of an Entity is mandatory on update");

        ea.setId(null);
        ea.setVersion(null);
        expectExceptionOnInvalidEntityTypeUpdate(ea, "namespace", "name",
                "The version and id of an Entity are mandatory on update");

        ea.setId(originalId + 1);
        ea.setVersion(originalVersion);
        expectExceptionOnInvalidEntityTypeUpdate(ea, "namespace", "name", "Invalid id for Entity namespace.name");

        ea.setId(originalId);
        ea.setVersion(originalVersion - 1);
        expectExceptionOnInvalidEntityTypeUpdate(ea, "namespace", "name", "Updating a deprecated version of Entity a.aaa. "
                + "Get the Entity again to obtain the newest version and proceed updating.");
    }

    @Test
    public void severalUpdates() {
        EntityType ea = createAndSaveOneEntityType("a", "aaa"); 

        ea.setNamespace("b");
        ea.setName("abc");
        ea = facade.update(ea);

        ea.setNamespace("a.b.d");
        ea.setName("abc");
        ea = facade.update(ea);

        ea.setNamespace(null);
        ea.setName("abc");
        ea = facade.update(ea);

        ea.setNamespace("a.b.c");
        ea.setName("abc");
        ea = facade.update(ea);

        EntityType found = facade.findEntityTypeById(ea.getId());
        Assert.assertEquals("a.b.c", found.getNamespace());
        Assert.assertEquals("abc", found.getName());
        Assert.assertEquals(new Long(1), found.getId());
        Assert.assertEquals(new Integer(4), found.getVersion());
    }
}
