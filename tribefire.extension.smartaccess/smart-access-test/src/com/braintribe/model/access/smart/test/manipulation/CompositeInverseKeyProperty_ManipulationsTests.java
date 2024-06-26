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
package com.braintribe.model.access.smart.test.manipulation;

import java.util.HashSet;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.CompositeIkpaEntityA;
import com.braintribe.model.processing.query.smart.test.model.smart.CompositeIkpaEntity;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.utils.junit.assertions.BtAssertions;

public class CompositeInverseKeyProperty_ManipulationsTests extends AbstractManipulationsTests {

	SmartPersonA p1, p2;
	CompositeIkpaEntity sc1, sc2;

	@Test
	public void changeValue_SingleSingle() throws Exception {
		p1 = newSmartPersonA();
		p1.setNameA("p1");

		CompositeIkpaEntity sc1 = newCompositeIkpaEntity();
		sc1.setDescription("d1");
		commit();

		p1.setCompositeIkpaEntity(sc1);
		commit();

		CompositeIkpaEntityA c1 = compositeIkpaEntityAByDescription("d1");
		BtAssertions.assertThat(c1.getPersonId()).isEqualTo(p1.getId());
		BtAssertions.assertThat(c1.getPersonName()).isEqualTo(p1.getNameA());
	}

	@Test
	public void changeValue_SingleSingle_Nullify() throws Exception {
		changeValue_SingleSingle();

		p1.setCompositeIkpaEntity(null);
		commit();

		CompositeIkpaEntityA c1 = compositeIkpaEntityAByDescription("d1");
		BtAssertions.assertThat(c1.getPersonId()).isNull();
		BtAssertions.assertThat(c1.getPersonName()).isNull();
	}

	@Test
	public void insert_MultiSingle() throws Exception {
		p1 = newSmartPersonA();
		p1.setNameA("p1");

		sc1 = newCompositeIkpaEntity();
		sc1.setDescription("d1");

		sc2 = newCompositeIkpaEntity();
		sc2.setDescription("d2");
		commit();

		p1.setCompositeIkpaEntitySet(new HashSet<CompositeIkpaEntity>());
		p1.getCompositeIkpaEntitySet().add(sc1);
		p1.getCompositeIkpaEntitySet().add(sc2);
		commit();

		assertCompositeIkpaLinkedTo_Set("d1", p1);
		assertCompositeIkpaLinkedTo_Set("d2", p1);
	}

	@Test
	public void remove_MultiSingle() throws Exception {
		insert_MultiSingle();

		p1.getCompositeIkpaEntitySet().remove(sc2);
		commit();

		assertCompositeIkpaLinkedTo_Set("d1", p1);
		assertCompositeIkpaLinkedTo_Set("d2", null);
	}

	@Test
	public void clear_MultiSingle() throws Exception {
		insert_MultiSingle();

		p1.getCompositeIkpaEntitySet().clear();
		commit();

		assertCompositeIkpaLinkedTo_Set("d1", null);
		assertCompositeIkpaLinkedTo_Set("d2", null);
	}

	private void assertCompositeIkpaLinkedTo_Set(String description, SmartPersonA p) {
		CompositeIkpaEntityA c = compositeIkpaEntityAByDescription(description);
		BtAssertions.assertThat(c.getPersonId_Set()).isEqualTo(p == null ? null : p.getId());
		BtAssertions.assertThat(c.getPersonName_Set()).isEqualTo(p == null ? null : p.getNameA());
	}

}
