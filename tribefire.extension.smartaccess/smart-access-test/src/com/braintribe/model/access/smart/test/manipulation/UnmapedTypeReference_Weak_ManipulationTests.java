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

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessA.special.ManualA;
import com.braintribe.model.processing.query.smart.test.model.accessA.special.ReaderA;
import com.braintribe.model.processing.query.smart.test.model.smart.special.SmartBookB;
import com.braintribe.model.processing.query.smart.test.model.smart.special.SmartManualA;
import com.braintribe.model.processing.query.smart.test.model.smart.special.SmartReaderA;
import com.braintribe.model.processing.smart.SmartAccessException;
import com.braintribe.utils.junit.assertions.BtAssertions;
import com.braintribe.utils.junit.core.rules.ThrowableChain;
import com.braintribe.utils.junit.core.rules.ThrowableChainRule;

/**
 *  
 */
public class UnmapedTypeReference_Weak_ManipulationTests extends AbstractManipulationsTests {

	private SmartReaderA sr;

	@Rule
	public ThrowableChainRule exceptionChainRule = new ThrowableChainRule();

	@Before
	public void prepareData() {
		sr = newSmartReaderA();
		sr.setName("r");
		commit();
	}

	// #####################################
	// ## . . . . . . . KPA . . . . . . . ##
	// #####################################

	@Test
	public void setValidEntityOk() throws Exception {
		SmartManualA a = newSmartManualA();
		a.setTitle("titleA");
		commit();

		sr.setWeakFavoriteManual(a);
		commit();

		ReaderA r = readerAByName("r");
		BtAssertions.assertThat(r.getFavoriteManualTitle()).isEqualTo(a.getTitle());
	}

	@Test
	@ThrowableChain({ SmartAccessException.class })
	public void setWrongEntityCausesError() throws Exception {
		SmartBookB b = newSmartBookB();
		b.setTitle("titleA");
		commit();

		sr.setWeakFavoriteManual(b);
		session.commit(); // this is expected to throw an exception

		Assert.fail("SmartAccessIllegalManipulationException should have been thrown.");
	}

	@Test
	public void addValidEntityOk() throws Exception {
		SmartManualA a = newSmartManualA();
		a.setTitle("titleA");
		commit();

		sr.getWeakFavoriteManuals().add(a);
		commit();

		ReaderA r = readerAByName("r");
		BtAssertions.assertThat(r.getFavoriteManualTitles()).containsOnly(a.getTitle());
	}

	@Test
	@ThrowableChain({ SmartAccessException.class })
	public void addWrongEntityCausesError() throws Exception {
		SmartBookB b = newSmartBookB();
		b.setTitle("titleA");
		commit();

		sr.getWeakFavoriteManuals().add(b);
		session.commit(); // this is expected to throw an exception

		Assert.fail("SmartAccessIllegalManipulationException should have been thrown.");
	}

	// #####################################
	// ## . . . . . . . IKPA . . . . . . .##
	// #####################################

	@Test
	public void addValidEntityOk_Inverse() throws Exception {
		SmartManualA sm = newSmartManualA();
		sm.setTitle("titleA");
		commit();

		sr.getWeakInverseFavoriteManuals().add(sm);
		commit();

		ManualA m = manualAByTitle(sm.getTitle());
		BtAssertions.assertThat(m.getManualString()).isEqualTo(sr.getName());
	}

	@Test
	@ThrowableChain({ SmartAccessException.class })
	public void addWrongEntityCausesError_Inverse() throws Exception {
		SmartBookB b = newSmartBookB();
		b.setTitle("titleA");
		commit();

		sr.getWeakInverseFavoriteManuals().add(b);
		session.commit(); // this is expected to throw an exception

		Assert.fail("SmartAccessIllegalManipulationException should have been thrown.");
	}

	protected ReaderA readerAByName(String name) {
		return selectByProperty(ReaderA.class, "name", name, smoodA);
	}

	protected ManualA manualAByTitle(String title) {
		return selectByProperty(ManualA.class, "title", title, smoodA);
	}

}
