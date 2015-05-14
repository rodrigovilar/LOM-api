package com.nanuvem.lom.api.tests.entitytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityHelper.createEntity;
import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.Test;

import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.MetadataException;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class DeleteEntityTest extends LomTestCase {

    @Test
    public void deleteEntity() {
        EntityType c = createEntity("a", "aaa");
        facade.deleteEntityType(c.getId());

        try {
            facade.deleteEntityType(c.getId());
            fail();
        } catch (MetadataException e) {
            Assert.assertEquals("Unknown id for Entity: 1", e.getMessage());
        }

        try {
            facade.deleteEntityType(c.getId() + 10);
            fail();
        } catch (MetadataException e) {
            Assert.assertEquals("Unknown id for Entity: 11", e.getMessage());
        }

    }
}
