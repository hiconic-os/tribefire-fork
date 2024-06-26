// ============================================================================
// Copyright BRAINTRIBE TECHNOLOGY GMBH, Austria, 2002-2022
// 
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
// 
//     http://www.apache.org/licenses/LICENSE-2.0
// 
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
// ============================================================================
package com.braintribe.model.processing.query.eval.set;

import static com.braintribe.model.processing.query.eval.set.base.TupleSetBuilder.valueProperty;
import static com.braintribe.utils.lcd.CollectionTools2.asSet;
import static java.util.Arrays.asList;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.eval.set.base.AbstractEvalTupleSetTests;
import com.braintribe.model.processing.query.eval.set.base.ModelBuilder;
import com.braintribe.model.processing.query.test.model.Address;
import com.braintribe.model.processing.query.test.model.Company;
import com.braintribe.model.processing.query.test.model.Owner;
import com.braintribe.model.processing.query.test.model.Person;
import com.braintribe.model.queryplan.set.SourceSet;
import com.braintribe.model.queryplan.set.join.EntityJoin;
import com.braintribe.model.queryplan.set.join.JoinKind;
import com.braintribe.model.queryplan.set.join.ListJoin;
import com.braintribe.model.queryplan.set.join.MapJoin;
import com.braintribe.model.queryplan.set.join.SetJoin;
import com.braintribe.utils.lcd.CollectionTools2;

/**
 * Tests filtered set with the underlying source-set (for {@link Person} entities).
 */
public class PropertyComplexRightJoinTests extends AbstractEvalTupleSetTests {

	private Owner pA, pB, pC;
	private Company c1, c2, c3;
	private Address a1, a2, a3, a4;

	private SourceSet personSet;

	/** Builds {@link Owner} with different "company" properties. Joins to these properties are then being tested. */
	@Before
	public void buildData() {
		registerAtSmood(pA = ModelBuilder.owner("owner1"));
		registerAtSmood(pB = ModelBuilder.owner("owner2"));
		registerAtSmood(pC = ModelBuilder.owner("owner3"));

		registerAtSmood(c1 = ModelBuilder.company("companyA1"));
		registerAtSmood(c2 = ModelBuilder.company("companyA2"));
		registerAtSmood(c3 = ModelBuilder.company("companyB3")); // not referenced

		registerAtSmood(a1 = ModelBuilder.address("address1"));
		registerAtSmood(a2 = ModelBuilder.address("address2"));
		registerAtSmood(a3 = ModelBuilder.address("address3"));
		registerAtSmood(a4 = ModelBuilder.address("address4")); // not referenced

		pA.setCompany(c1);
		pB.setCompany(c2);
		pC.setCompany(null);

		c1.setAddress(a1);
		c2.setAddress(a2);
		c3.setAddress(a3);

		personSet = builder.sourceSet(Owner.class);
	}

	// ################################
	// ## . . . . . Entity . . . . . ##
	// ################################

	@Test
	public void entityInner_EntityInner() throws Exception {
		EntityJoin eJoin1 = builder.entityJoin(personSet, valueProperty(personSet, "company"), JoinKind.inner);
		EntityJoin eJoin2 = builder.entityJoin(eJoin1, valueProperty(eJoin1, "address"), JoinKind.inner);
		evaluate(eJoin2);

		assertContainsTuple(pA, c1, a1);
		assertContainsTuple(pB, c2, a2);
		assertNoMoreTuples();
	}

	@Test
	public void entityRight_EntityInner() throws Exception {
		EntityJoin eJoin1 = builder.entityJoin(personSet, valueProperty(personSet, "company"), JoinKind.right);
		EntityJoin eJoin2 = builder.entityJoin(eJoin1, valueProperty(eJoin1, "address"), JoinKind.inner);
		evaluate(eJoin2);

		assertContainsTuple(pA, c1, a1);
		assertContainsTuple(pB, c2, a2);
		assertContainsTuple(null, c3, a3);
		assertNoMoreTuples();
	}

	@Test
	public void entityRight_EntityRight() throws Exception {
		EntityJoin eJoin1 = builder.entityJoin(personSet, valueProperty(personSet, "company"), JoinKind.right);
		EntityJoin eJoin2 = builder.entityJoin(eJoin1, valueProperty(eJoin1, "address"), JoinKind.right);
		evaluate(eJoin2);

		assertContainsTuple(pA, c1, a1);
		assertContainsTuple(pB, c2, a2);
		assertContainsTuple(null, c3, a3);
		assertContainsTuple(null, null, a4);
		assertNoMoreTuples();
	}

	// ################################
	// ## . . . . . . Set . . . . . .##
	// ################################

	@Test
	public void setInner_WhichIsEmpty_EntityRight() throws Exception {
		SetJoin sJoin = builder.setJoin(personSet, valueProperty(personSet, "companySet"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(sJoin, valueProperty(sJoin, "address"), JoinKind.right);

		evaluate(aJoin);

		// no person has any "companySet" set, so "person p join p.companySet" is an empty set
		assertContainsTuple(null, null, a1);
		assertContainsTuple(null, null, a2);
		assertContainsTuple(null, null, a3);
		assertContainsTuple(null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void setInner_EntityRight() throws Exception {
		buildAddressSet();

		SetJoin cJoin = builder.setJoin(personSet, valueProperty(personSet, "companySet"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(cJoin, valueProperty(cJoin, "address"), JoinKind.right);

		evaluate(aJoin);

		assertContainsTuple(pA, c1, a1);
		assertContainsTuple(pA, c2, a2);
		assertContainsTuple(pB, c1, a1);
		assertContainsTuple(null, null, a3);
		assertContainsTuple(null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void setInner_EntityFull() throws Exception {
		buildAddressSet();

		SetJoin cJoin = builder.setJoin(personSet, valueProperty(personSet, "companySet"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(cJoin, valueProperty(cJoin, "address"), JoinKind.full);

		evaluate(aJoin);

		assertContainsTuple(pA, c1, a1);
		assertContainsTuple(pA, c2, a2);
		// This is here, because null is one of elements of pA.companySet + second join is full
		assertContainsTuple(pA, null, null);
		assertContainsTuple(pB, c1, a1);
		assertContainsTuple(null, null, a3);
		assertContainsTuple(null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void setLeft_EntityFull() throws Exception {
		buildAddressSet();

		SetJoin cJoin = builder.setJoin(personSet, valueProperty(personSet, "companySet"), JoinKind.left);
		EntityJoin aJoin = builder.entityJoin(cJoin, valueProperty(cJoin, "address"), JoinKind.full);

		evaluate(aJoin);

		assertContainsTuple(pA, c1, a1);
		assertContainsTuple(pA, c2, a2);
		// This is here, because null is one of elements of pA.companySet + second join is full
		assertContainsTuple(pA, null, null);
		assertContainsTuple(pB, c1, a1);
		// This is here, because pC.compantySet is null, but we are doing left join
		assertContainsTuple(pC, null, null);
		assertContainsTuple(null, null, a3);
		assertContainsTuple(null, null, a4);
		assertNoMoreTuples();
	}

	private void buildAddressSet() {
		pA.setCompanySet(asSet(c1, c2, null));
		pB.setCompanySet(asSet(c1));
		pC.setCompanySet(null);
	}

	// ################################
	// ## . . . . . . List . . . . . ##
	// ################################

	@Test
	public void listInner_WhichIsEmpty_EntityRight() throws Exception {
		ListJoin lJoin = builder.listJoin(personSet, valueProperty(personSet, "companyList"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(lJoin, valueProperty(lJoin, "address"), JoinKind.right);

		evaluate(aJoin);

		// no person has any "companyList" set, so "person p join p.companyList" is an empty set
		assertContainsTuple(null, null, null, a1);
		assertContainsTuple(null, null, null, a2);
		assertContainsTuple(null, null, null, a3);
		assertContainsTuple(null, null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void listInner_EntityRight() throws Exception {
		buildAddressList();

		ListJoin lJoin = builder.listJoin(personSet, valueProperty(personSet, "companyList"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(lJoin, valueProperty(lJoin, "address"), JoinKind.right);

		evaluate(aJoin);

		assertContainsTuple(pA, 0, c1, a1);
		assertContainsTuple(pA, 1, c2, a2);
		assertContainsTuple(pB, 0, c1, a1);
		assertContainsTuple(null, null, null, a3);
		assertContainsTuple(null, null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void listInner_EntityFull() throws Exception {
		buildAddressList();

		ListJoin lJoin = builder.listJoin(personSet, valueProperty(personSet, "companyList"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(lJoin, valueProperty(lJoin, "address"), JoinKind.full);

		evaluate(aJoin);

		assertContainsTuple(pA, 0, c1, a1);
		assertContainsTuple(pA, 1, c2, a2);
		// This is here, because null is one of elements of pA.companList + second join is full
		assertContainsTuple(pA, 2, null, null);
		assertContainsTuple(pB, 0, c1, a1);
		assertContainsTuple(null, null, null, a3);
		assertContainsTuple(null, null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void listLeft_EntityFull() throws Exception {
		buildAddressList();

		ListJoin lJoin = builder.listJoin(personSet, valueProperty(personSet, "companyList"), JoinKind.left);
		EntityJoin aJoin = builder.entityJoin(lJoin, valueProperty(lJoin, "address"), JoinKind.full);

		evaluate(aJoin);

		assertContainsTuple(pA, 0, c1, a1);
		assertContainsTuple(pA, 1, c2, a2);
		// This is here, because null is one of elements of pA.companList + second join is full
		assertContainsTuple(pA, 2, null, null);
		assertContainsTuple(pB, 0, c1, a1);
		// This is here, because pC.compantyList is null, but we are doing left join
		assertContainsTuple(pC, null, null, null);
		assertContainsTuple(null, null, null, a3);
		assertContainsTuple(null, null, null, a4);
		assertNoMoreTuples();
	}

	private void buildAddressList() {
		pA.setCompanyList(asList(c1, c2, null));
		pB.setCompanyList(asList(c1));
		pC.setCompanySet(null);
	}

	// ################################
	// ## . . . . . . Map . . . . . .##
	// ################################

	@Test
	public void mapInner_WhichIsEmpty_EntityRight() throws Exception {
		MapJoin mJoin = builder.mapJoin(personSet, valueProperty(personSet, "companyMap"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(mJoin, valueProperty(mJoin, "address"), JoinKind.right);

		evaluate(aJoin);

		// no person has any "companyMap" set, so "person p join p.companyMap" is an empty set
		assertContainsTuple(null, null, null, a1);
		assertContainsTuple(null, null, null, a2);
		assertContainsTuple(null, null, null, a3);
		assertContainsTuple(null, null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void mapInner_EntityRight() throws Exception {
		buildAddressMap();

		MapJoin mJoin = builder.mapJoin(personSet, valueProperty(personSet, "companyMap"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(mJoin, valueProperty(mJoin, "address"), JoinKind.right);

		evaluate(aJoin);

		assertContainsTuple(pA, "c1", c1, a1);
		assertContainsTuple(pA, "c2", c2, a2);
		assertContainsTuple(pB, "c1", c1, a1);
		assertContainsTuple(null, null, null, a3);
		assertContainsTuple(null, null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void mapInner_EntityFull() throws Exception {
		buildAddressMap();

		MapJoin mJoin = builder.mapJoin(personSet, valueProperty(personSet, "companyMap"), JoinKind.inner);
		EntityJoin aJoin = builder.entityJoin(mJoin, valueProperty(mJoin, "address"), JoinKind.full);

		evaluate(aJoin);

		assertContainsTuple(pA, "c1", c1, a1);
		assertContainsTuple(pA, "c2", c2, a2);
		// This is here, because null is one of elements of pA.companMap + second join is full
		assertContainsTuple(pA, "null", null, null);
		assertContainsTuple(pB, "c1", c1, a1);
		assertContainsTuple(null, null, null, a3);
		assertContainsTuple(null, null, null, a4);
		assertNoMoreTuples();
	}

	@Test
	public void mapLeft_EntityFull() throws Exception {
		buildAddressMap();

		MapJoin mJoin = builder.mapJoin(personSet, valueProperty(personSet, "companyMap"), JoinKind.left);
		EntityJoin aJoin = builder.entityJoin(mJoin, valueProperty(mJoin, "address"), JoinKind.full);

		evaluate(aJoin);

		assertContainsTuple(pA, "c1", c1, a1);
		assertContainsTuple(pA, "c2", c2, a2);
		// This is here, because null is one of elements of pA.companMap + second join is full
		assertContainsTuple(pA, "null", null, null);
		assertContainsTuple(pB, "c1", c1, a1);
		// This is here, because pC.compantyMap is null, but we are doing left join
		assertContainsTuple(pC, null, null, null);
		assertContainsTuple(null, null, null, a3);
		assertContainsTuple(null, null, null, a4);
		assertNoMoreTuples();
	}

	private void buildAddressMap() {
		pA.setCompanyMap(CollectionTools2.<String, Company> asMap("c1", c1, "c2", c2, "null", null));
		pB.setCompanyMap(CollectionTools2.<String, Company> asMap("c1", c1));
		pC.setCompanyMap(null);
	}

}
