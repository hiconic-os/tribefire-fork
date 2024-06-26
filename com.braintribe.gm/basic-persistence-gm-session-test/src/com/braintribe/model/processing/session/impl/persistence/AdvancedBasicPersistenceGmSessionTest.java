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
package com.braintribe.model.processing.session.impl.persistence;

import static com.braintribe.testing.junit.assertions.assertj.core.api.Assertions.assertThat;

import static com.braintribe.utils.lcd.CollectionTools2.asList;

import java.util.function.Function;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.access.smood.basic.SmoodAccess;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.model.tools.MetaModelTools;
import com.braintribe.model.processing.session.test.data.Flag;
import com.braintribe.model.processing.session.test.data.Person;
import com.braintribe.model.processing.session.test.data.SpecialPerson;
import com.braintribe.model.processing.smood.Smood;
import com.braintribe.testing.tools.gm.GmTestTools;

/**
 * Tests for BasicPersistenceGmSession
 */
public class AdvancedBasicPersistenceGmSessionTest {

	private static final String ACCESS_ID = "test";

	Smood smood;
	BasicPersistenceGmSession session;

	Function<EntityType<?>, GenericEntity> constructor = EntityType::create;

	@Before
	public void setup() throws Exception {
		GmMetaModel model = MetaModelTools.provideRawModel(Person.T, SpecialPerson.T, Flag.T);
		SmoodAccess access = GmTestTools.newSmoodAccessMemoryOnly(ACCESS_ID, model);

		smood = access.getDatabase();
		session = new BasicPersistenceGmSession(access);
	}

	@Test
	public void resolvingEntityWhenIdNotUniqueInHierarchy() throws Exception {
		// Create instance of Person and it's sub-type SpecialPerson
		Person p1 = newPerson("p1");
		SpecialPerson sp1 = newSpecialPerson("sp1");

		// set the same id to both
		p1.setId(1l);
		sp1.setId(1L);

		smood.initialize(asList(p1, sp1));

		// get the person by reference (this was throwing an exception before the bug was fixed)
		Person p = (Person) session.query().entity(p1.reference()).find();

		assertThat(p instanceof SpecialPerson).isFalse();
	}

	@Test
	public void requireDoesUpdateEntityWhenNotAttachedYet() throws Exception {
		// 1:
		// Create Person and initialize Smood with it
		Person accessPerson = newPerson("Original Name");
		accessPerson.setId("id-1");
		accessPerson.setPartition(ACCESS_ID);

		smood.initialize(accessPerson);

		// 2:
		// Query Person, get the copy of what is in the access
		Person sessionPerson = session.query().entity(accessPerson).require();

		assertThat(accessPerson).isNotSameAs(sessionPerson);
		assertThat(accessPerson.getName()).isEqualTo(sessionPerson.getName());

		// 3:
		// Change the value in the access, note that require will not update our sessionPerson
		accessPerson.setName("Changed Name");

		sessionPerson = session.query().entity(accessPerson).require();
		assertThat(sessionPerson.getName()).isEqualTo("Original Name");

		// 4:
		// refresh() will now update our sessionPerson
		sessionPerson = session.query().entity(accessPerson).refresh();
		assertThat(sessionPerson.getName()).isEqualTo("Changed Name");
	}

	protected Person newPerson(String name) {
		Person p = newInstance(Person.T);
		p.setName(name);
		return p;
	}

	protected SpecialPerson newSpecialPerson(String name) {
		SpecialPerson p = newInstance(SpecialPerson.T);
		p.setName(name);
		return p;
	}

	private <T extends GenericEntity> T newInstance(EntityType<T> et) {
		return (T) constructor.apply(et);
	}

}
