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
import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.access.smood.basic.SmoodAccess;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.processing.model.tools.MetaModelTools;
import com.braintribe.model.processing.session.test.data.Flag;
import com.braintribe.model.processing.session.test.data.Person;
import com.braintribe.testing.tools.gm.GmTestTools;

/**
 * Tests for BasicPersistenceGmSession
 */
public class BasicPersistenceGmSession_MultiPartition_Test {

	SmoodAccess smoodAccess;
	BasicPersistenceGmSession session;

	@Before
	public void setup() throws Exception {
		smoodAccess = GmTestTools.newSmoodAccessMemoryOnly("testAccess", MetaModelTools.provideRawModel(Person.T, Flag.T));
		smoodAccess.setPartitions(asSet("p1", "p2"));

		session = new BasicPersistenceGmSession(smoodAccess);
	}

	/**
	 * There was a bug that internally in the session this would lead to an EntityQuery with a condition "where
	 * partition = '*'", because that's the value of the reference created internally when no partition is given (see
	 * {@link EntityReference#ANY_PARTITION}).
	 * <p>
	 * This was then fix to not include the partition in the query when this is the actual value, assuming the
	 * underlying access would not need the value anyway.
	 * <p>
	 * A better fix would probably be to just create a query like this:
	 * {@code select e from <ref.typeSignature> where e = ref}. Will probably be done in a future version of TF, don't
	 * want to break stuff right now.
	 */
	@Test
	public void refresh() throws Exception {
		Person p = createPerson("John", "p1");
		session.commit();

		Person p2 = (Person) session.query().entity(p.reference()).refresh();
		assertThat(p2).isSameAs(p);

		// Assuming a Person can only have partition "p1", this should work properly
		Person p3 = session.query().entity(Person.T, p.getId()).refresh();
		assertThat(p3).isSameAs(p);
	}

	private Person createPerson(String name, String partition) {
		Person p = session.create(Person.T);
		p.setName(name);
		p.setPartition(partition);
		return p;
	}

}
