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

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.smart.CompositeKpaEntity;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.utils.junit.assertions.BtAssertions;

public class CompositeKeyProperty_ManipulationsTests extends AbstractManipulationsTests {

	@Test
	public void changeEntityValue_BothPreliminary() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");

		CompositeKpaEntity c = newCompositeKpaEntity();
		c.setPersonId(99L);
		c.setPersonName("pp1");
		c.setPersonCompanyName("c1");

		// here we set the composite-key-property value to our preliminary instance
		p.setCompositeKpaEntity(c);

		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompositeId()).isEqualTo(99L);
		BtAssertions.assertThat(personA.getCompositeName()).isEqualTo("pp1");
		BtAssertions.assertThat(personA.getCompositeCompanyName()).isEqualTo("c1");
	}

	@Test
	public void changeEntityValue_BothPersistent() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");

		CompositeKpaEntity c = newCompositeKpaEntity();
		c.setPersonId(99L);
		c.setPersonName("pp1");
		c.setPersonCompanyName("c1");

		commit();

		// here we set the composite-key-property value to our preliminary instance
		p.setCompositeKpaEntity(c);

		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompositeId()).isEqualTo(99L);
		BtAssertions.assertThat(personA.getCompositeName()).isEqualTo("pp1");
		BtAssertions.assertThat(personA.getCompositeCompanyName()).isEqualTo("c1");
	}

}
