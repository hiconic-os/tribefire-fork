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

import com.braintribe.model.processing.query.smart.test.model.accessA.discriminator.DiscriminatorEntityA;
import com.braintribe.model.processing.query.smart.test.model.smart.discriminator.SmartDiscriminatorType1;
import com.braintribe.utils.junit.assertions.BtAssertions;

/**
 * @author peter.gazdik
 */
public class DiscriminatorEntity_ManipulationTests extends AbstractManipulationsTests {

	@Test
	public void testInstantiation() throws Exception {
		SmartDiscriminatorType1 entity = newEntity(SmartDiscriminatorType1.T);
		entity.setName("e1");
		commit();

		DiscriminatorEntityA delegate = selectByProperty(DiscriminatorEntityA.class, "name", "e1", smoodA);
		BtAssertions.assertThat(delegate).isNotNull();
		BtAssertions.assertThat(delegate.getDiscriminator()).isEqualTo(SmartDiscriminatorType1.DISC_TYPE1);
	}

}
