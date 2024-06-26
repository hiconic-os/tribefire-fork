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

import static java.util.Arrays.asList;

import java.util.ArrayList;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessB.PersonB;
import com.braintribe.model.processing.query.smart.test.model.accessB.StandardIdEntity;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonB;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartStringIdEntity;
import com.braintribe.utils.junit.assertions.BtAssertions;

public class ConvertedProperties_ManipulationsTests extends AbstractManipulationsTests {

	private SmartPersonB sp;

	@Before
	public void prepareData() throws Exception {
		sp = newSmartPersonB();
		sp.setNameB("sp");
		commit();

		BtAssertions.assertThat(countPersonB()).isEqualTo(1);
	}

	// ####################################
	// ## . . . . Single value . . . . . ##
	// ####################################

	@Test
	public void setSingleValue() throws Exception {
		sp.setConvertedBirthDate(new Date(0));
		commit();

		PersonB p = loadDelegate();
		BtAssertions.assertThat(p.getBirthDate()).isNotEmpty();
	}

	// ####################################
	// ## . . . . Collection value . . . ##
	// ####################################

	@Test
	public void setCollectionValue() throws Exception {
		sp.setConvertedDates(asList(new Date(0), new Date(1000)));
		commit();

		PersonB p = loadDelegate();
		BtAssertions.assertThat(p.getDates()).isNotEmpty().hasSize(2);
	}

	@Test
	public void insertToCollectionValue() throws Exception {
		sp.setConvertedDates(new ArrayList<Date>());
		sp.getConvertedDates().add(new Date(0));
		commit();

		PersonB p = loadDelegate();
		BtAssertions.assertThat(p.getDates()).isNotEmpty().hasSize(1);
	}

	@Test
	public void bulkInsertToCollectionValue() throws Exception {
		sp.setConvertedDates(new ArrayList<Date>());
		sp.getConvertedDates().addAll(asList(new Date(0), new Date(1000)));
		commit();

		PersonB p = loadDelegate();
		BtAssertions.assertThat(p.getDates()).isNotEmpty().hasSize(2);
	}

	@Test
	public void removeFromCollectionValue() throws Exception {
		insertToCollectionValue();

		sp.getConvertedDates().remove(0);
		commit();

		PersonB p = loadDelegate();
		BtAssertions.assertThat(p.getDates()).isEmpty();
	}

	@Test
	public void bulkRemoveFromCollectionValue() throws Exception {
		insertToCollectionValue();

		sp.getConvertedDates().removeAll(asList(new Date(0), new Date(1000)));
		commit();

		PersonB p = loadDelegate();
		BtAssertions.assertThat(p.getDates()).isEmpty();
	}

	// ####################################
	// ## . . . . . . Id value . . . . . ##
	// ####################################

	@Test
	public void instantiateEntityWithConvertedId() throws Exception {
		newEntity(SmartStringIdEntity.T);
		commit();

		BtAssertions.assertThat(count(StandardIdEntity.class, smoodB)).isEqualTo(1);
	}

	private PersonB loadDelegate() {
		return selectByProperty(PersonB.class, "nameB", "sp", smoodB);
	}

}
