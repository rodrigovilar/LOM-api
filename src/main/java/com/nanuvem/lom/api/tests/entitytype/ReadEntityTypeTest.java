package com.nanuvem.lom.api.tests.entitytype;

import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createAndSaveOneEntityType;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.createEntityType;
import static com.nanuvem.lom.api.tests.entitytype.EntityTypeHelper.expectExceptionOnInvalidEntityTypeList;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.nanuvem.lom.api.EntityType;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class ReadEntityTypeTest extends LomTestCase {

	@Test
	public void listallEntities() {
		List<EntityType> allEntities = facade.listAllEntitiesTypes();
		Assert.assertEquals(0, allEntities.size());

		EntityType entity1 = createAndSaveOneEntityType("ns1", "n1");
		EntityType entity2 = createAndSaveOneEntityType("ns2", "n2");
		EntityType entity3 = createAndSaveOneEntityType("ns2", "n3");

		allEntities = facade.listAllEntitiesTypes();

		Assert.assertEquals(3, allEntities.size());
		Assert.assertEquals(entity1, allEntities.get(0));
		Assert.assertEquals(entity2, allEntities.get(1));
		Assert.assertEquals(entity3, allEntities.get(2));

		facade.deleteEntityType(entity1.getId());
		facade.deleteEntityType(entity2.getId());
		facade.deleteEntityType(entity3.getId());

		Assert.assertEquals(0, facade.listAllEntitiesTypes().size());
	}

	@Test
	public void listEntitiesByValidFragmentOfNameAndPackage() {
		String namespaceFragment = "ns";
		String nameFragment = "n";

		List<EntityType> allEntities = facade
				.listEntitiesTypesByFullName(namespaceFragment);
		Assert.assertEquals(0, allEntities.size());

		allEntities = facade.listEntitiesTypesByFullName(nameFragment);
		Assert.assertEquals(0, allEntities.size());

		EntityType entity1 = createAndSaveOneEntityType("ns1", "n1");
		EntityType entity2 = createAndSaveOneEntityType("ns2", "n2");
		EntityType entity3 = createAndSaveOneEntityType("ns2", "n3");

		allEntities = facade.listEntitiesTypesByFullName(namespaceFragment);
		Assert.assertEquals(3, allEntities.size());
		Assert.assertEquals(entity1, allEntities.get(0));
		Assert.assertEquals(entity2, allEntities.get(1));
		Assert.assertEquals(entity3, allEntities.get(2));

		allEntities = facade.listEntitiesTypesByFullName(nameFragment);
		Assert.assertEquals(3, allEntities.size());
		Assert.assertEquals(entity1, allEntities.get(0));
		Assert.assertEquals(entity2, allEntities.get(1));
		Assert.assertEquals(entity3, allEntities.get(2));

		facade.deleteEntityType(entity1.getId());
		facade.deleteEntityType(entity2.getId());
		facade.deleteEntityType(entity3.getId());

		Assert.assertEquals(0, facade.listAllEntitiesTypes().size());
	}

	@Test
	public void listEntitiesByEmptyNameAndPackage() {
		List<EntityType> allEntities = facade.listEntitiesTypesByFullName("");
		Assert.assertEquals(0, allEntities.size());

		EntityType entity1 = createAndSaveOneEntityType("ns1", "n1");
		EntityType entity2 = createAndSaveOneEntityType("ns2", "n2");
		EntityType entity3 = createAndSaveOneEntityType("ns2", "n3");

		List<EntityType> allEntities1 = facade.listEntitiesTypesByFullName("");
		Assert.assertEquals(3, allEntities1.size());
		Assert.assertEquals(entity1, allEntities1.get(0));
		Assert.assertEquals(entity2, allEntities1.get(1));
		Assert.assertEquals(entity3, allEntities1.get(2));

		allEntities = facade.listEntitiesTypesByFullName("nspace");
		Assert.assertEquals(0, allEntities.size());

		facade.deleteEntityType(entity1.getId());
		facade.deleteEntityType(entity2.getId());
		facade.deleteEntityType(entity3.getId());

		Assert.assertEquals(0, facade.listAllEntitiesTypes().size());
	}

	@Test
	public void listEntitiesByFragmentOfNameAndPackagesWithSpaces() {
		expectExceptionOnInvalidEntityTypeList("na me",
				"Invalid value for Entity full name: na me");
	}

	@Test
	public void listEntitiesForcingCaseInsensitivePackagesAndNames() {
		EntityType entity1 = createAndSaveOneEntityType("ns1", "n1");
		EntityType entity2 = createAndSaveOneEntityType("NS2", "n2");
		EntityType entity3 = createAndSaveOneEntityType("NS3", "N3");
		List<EntityType> expectedEntities = new ArrayList<EntityType>();
		expectedEntities.add(entity1);
		expectedEntities.add(entity2);
		expectedEntities.add(entity3);

		List<EntityType> allEntities1 = facade
				.listEntitiesTypesByFullName("ns");
		Assert.assertEquals(3, allEntities1.size());
		Assert.assertEquals(entity1, allEntities1.get(0));
		Assert.assertEquals(entity2, allEntities1.get(1));
		Assert.assertEquals(entity3, allEntities1.get(2));

		allEntities1 = facade.listEntitiesTypesByFullName("n");
		Assert.assertEquals(3, allEntities1.size());
		Assert.assertEquals(entity1, allEntities1.get(0));
		Assert.assertEquals(entity2, allEntities1.get(1));
		Assert.assertEquals(entity3, allEntities1.get(2));

		allEntities1 = facade.listEntitiesTypesByFullName("NS");
		Assert.assertEquals(3, allEntities1.size());
		Assert.assertEquals(entity1, allEntities1.get(0));
		Assert.assertEquals(entity2, allEntities1.get(1));
		Assert.assertEquals(entity3, allEntities1.get(2));

		allEntities1 = facade.listEntitiesTypesByFullName("N");
		Assert.assertEquals(3, allEntities1.size());
		Assert.assertEquals(entity1, allEntities1.get(0));
		Assert.assertEquals(entity2, allEntities1.get(1));
		Assert.assertEquals(entity3, allEntities1.get(2));
	}

	@Test
	public void listEntitiesUsingInvalidFragmentOfNameAndPackage() {
		expectExceptionOnInvalidEntityTypeList("n$", INVALID_VALUE_FOR_ENTITY,
				"full name", "n$");
		expectExceptionOnInvalidEntityTypeList("n#", INVALID_VALUE_FOR_ENTITY,
				"full name", "n#");
		expectExceptionOnInvalidEntityTypeList("n=", INVALID_VALUE_FOR_ENTITY,
				"full name", "n=");
		expectExceptionOnInvalidEntityTypeList("n'", INVALID_VALUE_FOR_ENTITY,
				"full name", "n'");
		expectExceptionOnInvalidEntityTypeList("n/n", INVALID_VALUE_FOR_ENTITY,
				"full name", "n/n");
		expectExceptionOnInvalidEntityTypeList("n*", INVALID_VALUE_FOR_ENTITY,
				"full name", "n*");
	}

	@Test
	public void getEntityByValidNameAndPackage() {
		Assert.assertEquals(0, facade.listEntitiesTypesByFullName("ns.n")
				.size());

		EntityType entity1 = createEntityType("ns1", "n1");
		EntityType foundEntity1 = facade.findEntityTypeByFullName("ns1.n1");
		Assert.assertEquals(entity1, foundEntity1);

		EntityType entity2 = createEntityType("ns2", "n2");
		EntityType foundEntity2 = facade.findEntityTypeByFullName("ns2.n2");
		Assert.assertEquals(entity2, foundEntity2);

		Assert.assertEquals(1, facade.listEntitiesTypesByFullName("ns1.n")
				.size());
		Assert.assertEquals(0, facade.listEntitiesTypesByFullName("ns.n1")
				.size());
		Assert.assertEquals(0, facade.listEntitiesTypesByFullName("ns2.n1")
				.size());

		List<EntityType> allEntities = facade.listAllEntitiesTypes();
		Assert.assertEquals(2, allEntities.size());
		Assert.assertEquals(entity1, allEntities.get(0));
		Assert.assertEquals(entity2, allEntities.get(1));
	}

	@Test
	public void getEntityByEmptyNameAndPackage() {
		createEntityType("ns1", "n1");
		EntityType entity2 = createEntityType(null, "n2");
		Assert.assertEquals(1, facade.listEntitiesTypesByFullName(".n1").size());

		EntityType foundEntity2 = facade.findEntityTypeByFullName("n2");
		Assert.assertEquals(entity2, foundEntity2);
		Assert.assertEquals(1, facade.listEntitiesTypesByFullName("ns1.")
				.size());
	}

	@Test
	public void getEntityByNameAndPackageWithSpaces() {
		expectExceptionOnInvalidEntityTypeList(".na me", INVALID_VALUE_FOR_ENTITY,
				"full name", ".na me");
		expectExceptionOnInvalidEntityTypeList("name space.name",
				INVALID_VALUE_FOR_ENTITY, "full name", "name space.name");
		expectExceptionOnInvalidEntityTypeList("namespace.na me",
				INVALID_VALUE_FOR_ENTITY, "full name", "namespace.na me");
	}

	@Test
	public void getEntityForcingCaseInsensitivePackagesAndNames() {
		EntityType entity = createEntityType("nS", "nA");
		EntityType ea = facade.findEntityTypeByFullName("ns.na");
		Assert.assertEquals(entity, ea);

		ea = facade.findEntityTypeByFullName("NS.NA");
		Assert.assertEquals(entity, ea);

		ea = facade.findEntityTypeByFullName("nS.nA");
		Assert.assertEquals(entity, ea);

		ea = facade.findEntityTypeByFullName("NS.na");
		Assert.assertEquals(entity, ea);

		ea = facade.findEntityTypeByFullName("ns.NA");
		Assert.assertEquals(entity, ea);

		ea = facade.findEntityTypeByFullName("Ns.Na");
		Assert.assertEquals(entity, ea);

	}

	@Test
	public void getEntityUsingInvalidNameAndPackage() {
		expectExceptionOnInvalidEntityTypeList("ns.n$", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns.n$");
		expectExceptionOnInvalidEntityTypeList("ns.n#", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns.n#");
		expectExceptionOnInvalidEntityTypeList("ns.n=", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns.n=");
		expectExceptionOnInvalidEntityTypeList("ns.n/n", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns.n/n");
		expectExceptionOnInvalidEntityTypeList("ns.n*", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns.n*");
		expectExceptionOnInvalidEntityTypeList("ns.n'", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns.n'");
		expectExceptionOnInvalidEntityTypeList("ns$.n", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns$.n");
		expectExceptionOnInvalidEntityTypeList("ns#.n", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns#.n");
		expectExceptionOnInvalidEntityTypeList("ns=.n", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns=.n");
		expectExceptionOnInvalidEntityTypeList("ns/.n", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns/.n");
		expectExceptionOnInvalidEntityTypeList("ns*.n", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns*.n");
		expectExceptionOnInvalidEntityTypeList("ns'.n", INVALID_VALUE_FOR_ENTITY,
				"full name", "ns'.n");
	}

}
