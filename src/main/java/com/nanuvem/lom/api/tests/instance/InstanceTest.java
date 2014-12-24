package com.nanuvem.lom.api.tests.instance;

import static com.nanuvem.lom.api.tests.attribute.AttributeHelper.createOneAttribute;
import static com.nanuvem.lom.api.tests.entity.EntityHelper.createEntity;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.createAndVerifyOneInstance;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.expectExceptionOnCreateInvalidInstance;
import static com.nanuvem.lom.api.tests.instance.InstanceHelper.updateOneValueOfInstanceAndVerifyOneException;

import org.junit.Test;

import com.nanuvem.lom.api.AttributeType;
import com.nanuvem.lom.api.tests.LomTestCase;

public abstract class InstanceTest extends LomTestCase {

	@Test
	public void unknownClass() {
		expectExceptionOnCreateInvalidInstance("a", "Entity not found: a", "30");

		expectExceptionOnCreateInvalidInstance("abc.a",
				"Entity not found: abc.a", "30");
	}

	@Test
	public void nullClass() {
		expectExceptionOnCreateInvalidInstance(null,
				"Invalid value for Instance entity: The entity is mandatory",
				"30");
	}

	@Test
	public void entityWithoutAttributes() {
		createEntity("abc", "a");
		createAndVerifyOneInstance("abc.a");

		createEntity("abc", "b");
		createAndVerifyOneInstance("abc.b");

		createEntity("", "a");
		createAndVerifyOneInstance("a");

		createEntity("", "b");
		createAndVerifyOneInstance("b");
	}

	@Test
	public void entityWithKnownAttributesAndWithoutConfiguration() {
		createEntity("system", "Client");
		createOneAttribute("system.Client", null, "pa", AttributeType.TEXT,
				null);

		createEntity("system", "User");
		createOneAttribute("system.User", null, "pa", AttributeType.TEXT, null);
		createOneAttribute("system.User", null, "pb", AttributeType.LONGTEXT,
				null);

		createEntity("system", "Organization");
		createOneAttribute("system.Organization", null, "pa",
				AttributeType.TEXT, null);
		createOneAttribute("system.Organization", null, "pb",
				AttributeType.LONGTEXT, null);
		createOneAttribute("system.Organization", null, "pc",
				AttributeType.INTEGER, null);

		createEntity("system", "Category");
		createOneAttribute("system.Category", null, "pa", AttributeType.TEXT,
				null);
		createOneAttribute("system.Category", null, "pb",
				AttributeType.LONGTEXT, null);
		createOneAttribute("system.Category", null, "pc",
				AttributeType.INTEGER, null);
		createOneAttribute("system.Category", null, "pd",
				AttributeType.PASSWORD, null);

		createAndVerifyOneInstance("system.client");
		createAndVerifyOneInstance("system.client", "va");
		createAndVerifyOneInstance("system.user");
		createAndVerifyOneInstance("system.user", "va", "vb");
		createAndVerifyOneInstance("system.organization");
		createAndVerifyOneInstance("system.organization", "va", "vb", "3");
		createAndVerifyOneInstance("system.category");
		createAndVerifyOneInstance("system.category", "va", "vb", "3", "vd");
	}

	@Test
	public void updateValueOfTheAttributeValueForOtherValidValues() {

		updateOneValueOfInstanceAndVerifyOneException("abc", "b",
				"descryption", AttributeType.LONGTEXT,
				"{\"default\": \"Nothing to say\", \"maxlength\": 100}",
				"Here must contain a description, written by a long text",
				"It's a personal like that codes", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "c", "counter",
				AttributeType.INTEGER, "{\"default\": \"0\", \"minvalue\": 0}",
				"1", "3", null);

		updateOneValueOfInstanceAndVerifyOneException("abc", "d", "secretKey",
				AttributeType.PASSWORD, "{\"default\": \"password\"}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6", null);
	}

	@Test
	public void updateValueOfTheAttributeValueForInvalidValues() {

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"b",
				"descryption",
				AttributeType.LONGTEXT,
				"{\"default\": \"Nothing to say\", \"maxlength\": 100}",
				"Here must contain a description, written by a long text",
				"Seja bem Vindo! Sou professor do Departamento de Ciências Exatas do CCAE/UFPB e "
						+ "leciono disciplinas nos cursos de Licenciatura em Ciência da Computação e Sistemas de Informação. "
						+ "Já ensinei as seguintes disciplinas: Análise e Projeto de Sistemas; Desenvolvimento de Sistemas Corporativos; "
						+ "Programação Orientada a Objetos; Linguagem de Programação; Introdução à Programação; "
						+ "Banco de Dados; Tenho graduação e mestrado em Ciência da Computação pela Universidade Federal de Campina Grande. "
						+ "Meus interesses de pesquisa são Sistemas adaptativos, "
						+ "Frameworks e Meta modelagem.",
				"Invalid value for the Instance. The value for 'description' must have a maximum length of 100 characters");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"c",
				"counter",
				AttributeType.INTEGER,
				"{\"default\": \"0\", \"minvalue\": 0}",
				"1",
				"-3",
				"Invalid value for the Instance. The value for 'counter' must be greater or equal to 0");

		updateOneValueOfInstanceAndVerifyOneException(
				"abc",
				"d",
				"secretKey",
				AttributeType.PASSWORD,
				"{\"default\": \"password\", \"maxlength\": 32}",
				"5f6eca57fc12718a639e3433bb02a7c5",
				"9e107d9d372bb6826bd81d3542a419d6",
				"Invalid value for the Instance. The value for 'secretKey' must have a maximum length of 32 characters");
	}
}