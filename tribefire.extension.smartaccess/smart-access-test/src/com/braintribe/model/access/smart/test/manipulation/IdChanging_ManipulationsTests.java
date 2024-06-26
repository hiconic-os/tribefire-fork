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

import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdA;

import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.PersonA;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.SmartAccessException;
import com.braintribe.utils.junit.assertions.BtAssertions;

public class IdChanging_ManipulationsTests extends AbstractManipulationsTests {

	@Test
	public void manuallySetId() throws Exception {
		SmartPersonA sp = newSmartPersonA();
		sp.setId(10L);
		sp.setNameA("p");
		commit();

		PersonA pA = personAByName("p");

		BtAssertions.assertThat(pA.<Object> getId()).isEqualTo(10l);
		BtAssertions.assertThat(countPersonA()).isEqualTo(1);

		// Test automatically assigned partition is correct
		BtAssertions.assertThat(pA.getPartition()).isNull(); // we do not set it, so in the smood it is null
		BtAssertions.assertThat(sp.getPartition()).isEqualTo(accessIdA); // automatically assigned will be accessA
	}

	/**
	 * There was a bug where setting id to null would lead to an NPE. This was fixed by ignoring such manipulation in
	 * the ReferenceManager when the original id was also null.
	 * 
	 * Note that setting id null to an entity with id would lead to a {@link SmartAccessException}.
	 */
	@Test
	public void setIdToNull_Preliminary() throws Exception {
		SmartPersonA sp = newSmartPersonA();
		sp.setId(null);
		sp.setNameA("p");
		commit();

		PersonA pA = personAByName("p");

		BtAssertions.assertThat(pA.<Object> getId()).isNotNull();
		BtAssertions.assertThat(countPersonA()).isEqualTo(1);

		// Test automatically assigned partition is correct
		BtAssertions.assertThat(pA.getPartition()).isNull(); // we do not set it, so in the smood it is null
		BtAssertions.assertThat(sp.getPartition()).isEqualTo(accessIdA); // automatically assigned will be accessA
	}

	/** @see #setIdToNull_Preliminary() */
	@Test(expected=RuntimeException.class)
	public void setIdToNull_Persistent() throws Exception {
		SmartPersonA sp = newSmartPersonA();
		sp.setNameA("p");
		commit();

		sp.setId(null);
		commit();
	}
}
