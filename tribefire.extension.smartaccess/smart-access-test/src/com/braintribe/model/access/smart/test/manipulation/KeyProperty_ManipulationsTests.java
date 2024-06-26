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

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.smart.Company;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.utils.junit.assertions.BtAssertions;

public class KeyProperty_ManipulationsTests extends AbstractManipulationsTests {

	@Test
	public void changeEntityValue_BothPreliminary() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");

		Company c = newCompany();
		c.setNameA("c1");

		// here we set the key-property value to our preliminary instance
		p.setKeyCompanyA(c);

		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompanyNameA()).isEqualTo("c1");
	}

	@Test
	public void changeEntityValue_KeyEntityPersistent() throws Exception {
		Company c = newCompany();
		c.setNameA("c1");
		commit();

		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");

		// here we set the key-property value to our persistent instance
		p.setKeyCompanyA(c);

		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompanyNameA()).isEqualTo("c1");
	}

	@Test
	public void changeEntityValue_BothPersistent() throws Exception {
		Company c = newCompany();
		c.setNameA("c1");
		commit();

		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");
		commit();

		// here we set the key-property value to our persistent instance
		p.setKeyCompanyA(c);

		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompanyNameA()).isEqualTo("c1");
	}

	@Test
	public void insertToCollection_BothPreliminary() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");

		Company c1 = newCompany();
		c1.setNameA("c1");

		Company c2 = newCompany();
		c2.setNameA("c2");

		// here we set the key-property value to our preliminary instance
		p.setKeyCompanySetA(new HashSet<Company>());
		p.getKeyCompanySetA().add(c1);
		p.getKeyCompanySetA().add(c2);

		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompanyNameSetA()).containsOnly("c1", "c2");
	}

	@Test
	public void bulkInsertToCollection_BothPreliminary() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");

		Company c1 = newCompany();
		c1.setNameA("c1");

		Company c2 = newCompany();
		c2.setNameA("c2");

		// here we set the key-property value to our preliminary instance
		p.setKeyCompanySetA(new HashSet<Company>());
		p.getKeyCompanySetA().addAll(Arrays.asList(c1, c2));

		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompanyNameSetA()).containsOnly("c1", "c2");
	}

	@Test
	public void removeFromCollection() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");

		Company c1 = newCompany();
		c1.setNameA("c1");

		Company c2 = newCompany();
		c2.setNameA("c2");

		// here we set the key-property value to our preliminary instance
		p.setKeyCompanySetA(new HashSet<Company>());
		p.getKeyCompanySetA().addAll(Arrays.asList(c1, c2));
		commit();

		p.getKeyCompanySetA().remove(c2);
		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompanyNameSetA()).containsOnly("c1");
	}

	@Test
	public void bulkRemoveFromCollection() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p1");

		Company c1 = newCompany();
		c1.setNameA("c1");

		Company c2 = newCompany();
		c2.setNameA("c2");

		// here we set the key-property value to our preliminary instance
		p.setKeyCompanySetA(new HashSet<Company>());
		p.getKeyCompanySetA().addAll(Arrays.asList(c1, c2));
		commit();

		p.getKeyCompanySetA().removeAll(Arrays.asList(c1, c2));
		commit();

		PersonA personA = personAByName("p1");
		BtAssertions.assertThat(personA.getCompanyNameSetA()).isEmpty();
	}

}
