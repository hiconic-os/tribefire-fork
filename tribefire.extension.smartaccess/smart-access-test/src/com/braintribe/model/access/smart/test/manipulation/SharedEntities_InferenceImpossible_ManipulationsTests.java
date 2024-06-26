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
import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdB;

import org.junit.Rule;
import org.junit.Test;

import com.braintribe.model.processing.query.smart.test.model.shared.SharedFile;
import com.braintribe.model.processing.query.smart.test.model.shared.SharedFileDescriptor;
import com.braintribe.model.processing.smart.SmartAccessException;
import com.braintribe.utils.junit.core.rules.ThrowableChainRule;

/**
 * These tests check if the correct delegate is chosen.
 * 
 */
public class SharedEntities_InferenceImpossible_ManipulationsTests extends AbstractManipulationsTests {

	@Rule
	public ThrowableChainRule exceptionChainRule = new ThrowableChainRule(SmartAccessException.class);

	@Test
	public void explicitIncompatibleAssignment() throws Exception {
		SharedFile f1 = newSharedFile();
		f1.setPartition(accessIdA);

		SharedFile f2 = newSharedFile();
		f2.setPartition(accessIdB);

		f1.setParent(f2);

		commit();
	}

	@Test
	public void incompatibleDefaults() throws Exception {
		SharedFile f = newSharedFile();
		SharedFileDescriptor d = newSharedFileDescriptor();

		f.setFileDescriptor(d);
		commit();
	}

	// #####################################
	// ## . . . . . . HELPERS . . . . . . ##
	// #####################################

	protected SharedFile newSharedFile() {
		return newEntity(SharedFile.T);
	}

	protected SharedFileDescriptor newSharedFileDescriptor() {
		return newEntity(SharedFileDescriptor.T);
	}

}
