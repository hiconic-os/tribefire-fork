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

import org.junit.Before;
import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.accessB.EnumEntityB;
import com.braintribe.model.processing.query.smart.test.model.accessB.ItemTypeB;
import com.braintribe.model.processing.query.smart.test.model.smart.ItemType;
import com.braintribe.model.processing.query.smart.test.model.smart.ItemType_Identity;
import com.braintribe.model.processing.query.smart.test.model.smart.ItemType_String;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartEnumEntityB;
import com.braintribe.utils.junit.assertions.BtAssertions;

/**
 * 
 */
public class EnumProperties_ManipulationsTests extends AbstractManipulationsTests {

	private SmartEnumEntityB see;

	@Before
	public void prepareData() throws Exception {
		see = newSmartEnumEntityB();
		see.setName("see");
		commit();

		BtAssertions.assertThat(countEnumEntityB()).isEqualTo(1);
	}

	// #############################################
	// ## . . . . Set Single Enum Value . . . . . ##
	// #############################################

	@Test
	public void setSingleValue_Identity() throws Exception {
		see.setEnumIdentity(ItemType_Identity.TOOL);
		commit();

		EnumEntityB e = loadDelegate();
		BtAssertions.assertThat(e.getEnumIdentity()).isEqualTo(ItemType_Identity.TOOL);
	}

	@Test
	public void setSingleValue_Delegate() throws Exception {
		see.setEnumAsDelegate(ItemType.TOOL);
		commit();

		EnumEntityB e = loadDelegate();
		BtAssertions.assertThat(e.getEnumAsDelegate()).isEqualTo(ItemTypeB.TOOL_B);
	}

	@Test
	public void setSingleValue_String() throws Exception {
		see.setEnumAsString(ItemType_String.TOOL);
		commit();

		EnumEntityB e = loadDelegate();
		BtAssertions.assertThat(e.getEnumAsString()).isEqualTo("TOOL_B");
	}

	@Test
	public void setSingleValue_CustomConversion() throws Exception {
		see.setEnumCustomConverted(ItemType.TOOL);
		commit();

		EnumEntityB e = loadDelegate();
		BtAssertions.assertThat(e.getEnumCustomConverted()).isEqualTo(ItemType.TOOL.ordinal());
	}

	private EnumEntityB loadDelegate() {
		return selectByProperty(EnumEntityB.class, "name", "see", smoodB);
	}

}
