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
import com.braintribe.model.processing.query.smart.test.model.accessB.StandardIdEntity;
import com.braintribe.model.processing.query.smart.test.model.smart.Company;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartStringIdEntity;
import com.braintribe.utils.junit.assertions.BtAssertions;

public class SingleValue_ManipulationsTests extends AbstractManipulationsTests {

	@Test
	public void instantiation() throws Exception {
		SmartPersonA p = newSmartPersonA();
		commit();

		BtAssertions.assertThat(p.<Object> getId()).isNotNull();
		BtAssertions.assertThat(countPersonA()).isEqualTo(1);
	}

	@Test
	public void instantiation_WithConvertedId() throws Exception {
		SmartStringIdEntity s = newStandardStringIdEntity();
		commit();

		BtAssertions.assertThat(s.<Object> getId()).isNotNull();
		BtAssertions.assertThat(count(StandardIdEntity.class, smoodB)).isEqualTo(1);
	}

	/* There was a bug, that id (set by induced manipulation) was only set to the first entity */
	@Test
	public void instantiation_Multiple() throws Exception {
		SmartPersonA p1 = newSmartPersonA();
		SmartPersonA p2 = newSmartPersonA();
		commit();

		BtAssertions.assertThat(p1.<Object> getId()).isNotNull();
		BtAssertions.assertThat(p2.<Object> getId()).isNotNull();
		BtAssertions.assertThat(countPersonA()).isEqualTo(2);
	}

	@Test
	public void changeSimpleValue() throws Exception {
		SmartPersonA p = newSmartPersonA();
		commit();

		p.setNameA("p1");
		commit();

		BtAssertions.assertThat(personAByName("p1")).isNotNull();
	}

	@Test
	public void changeSimpleValue_WithConvertedId() throws Exception {
		SmartStringIdEntity s = newStandardStringIdEntity();
		commit();

		s.setName("s");
		commit();

		BtAssertions.assertThat(standardIdEntityByName("s")).isNotNull();
	}

	@Test
	public void changeSimpleValue_PartitionInference() throws Exception {
		SmartPersonA p = newSmartPersonA();
		commit();

		session.suspendHistory();
		p.setPartition("*");
		session.resumeHistory();
		
		p.setNameA("p1");
		commit();

		BtAssertions.assertThat(personAByName("p1")).isNotNull();
	}

	@Test
	public void changeEntityValue_Preliminary() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p");
		commit();

		Company c = newCompany();
		c.setNameA("braintribe");

		p.setCompanyA(c);
		commit();

		PersonA pA = personAByName("p");
		BtAssertions.assertThat(pA).isNotNull();
		BtAssertions.assertThat(pA.getCompanyA()).isNotNull();
		BtAssertions.assertThat(pA.getCompanyA().getNameA()).isEqualTo("braintribe");
	}

	@Test
	public void changeEntityValue_Persistent() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p");
		commit();

		Company c = newCompany();
		c.setNameA("braintribe");
		commit();

		p.setCompanyA(c);
		commit();

		PersonA pA = personAByName("p");
		BtAssertions.assertThat(pA).isNotNull();
		BtAssertions.assertThat(pA.getCompanyA()).isNotNull();
		BtAssertions.assertThat(pA.getCompanyA().getNameA()).isEqualTo("braintribe");
	}

	@Test
	public void changeEntityValue_Null() throws Exception {
		SmartPersonA p = newSmartPersonA();
		p.setNameA("p");
		commit();

		p.setCompanyA(null);
		commit();

		PersonA pA = personAByName("p");
		BtAssertions.assertThat(pA).isNotNull();
		BtAssertions.assertThat(pA.getCompanyA()).isNull();
	}

	@Test
	public void delete() throws Exception {
		SmartPersonA p = newSmartPersonA();
		commit();

		session.deleteEntity(p);
		commit();

		BtAssertions.assertThat(countPersonA()).isEqualTo(0);
	}

}
