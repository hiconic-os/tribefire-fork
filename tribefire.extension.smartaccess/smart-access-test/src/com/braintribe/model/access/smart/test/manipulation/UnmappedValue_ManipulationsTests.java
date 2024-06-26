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

import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;
import com.braintribe.model.processing.smart.SmartAccessException;
import com.braintribe.utils.junit.assertions.BtAssertions;
import com.braintribe.utils.junit.core.rules.ThrowableChainRule;

public class UnmappedValue_ManipulationsTests extends AbstractManipulationsTests {

	private SmartPersonA p;

	@Rule
	public ThrowableChainRule exceptionChainRule = new ThrowableChainRule(SmartAccessException.class);

	@Before
	public void initalize() throws Exception {
		p = newSmartPersonA();
		commit();

		BtAssertions.assertThat(p.<Object> getId()).isNotNull();
		BtAssertions.assertThat(countPersonA()).isEqualTo(1);
	}

	@Test
	public void unmappedString() throws Exception {
		p.setUnmappedString("value");
		session.commit();
	}

	@Test
	public void unmappedEntity() throws Exception {
		p.setUnmappedParent(p);
		session.commit();
	}

	@Test
	public void unmappedCollection() throws Exception {
		p.setUnmappedParents(asSet(p));
		session.commit();
	}

}
