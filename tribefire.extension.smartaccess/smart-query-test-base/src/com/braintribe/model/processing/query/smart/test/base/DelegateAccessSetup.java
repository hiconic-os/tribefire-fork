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
package com.braintribe.model.processing.query.smart.test.base;

import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdA;
import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdB;
import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import com.braintribe.common.lcd.EmptyReadWriteLock;
import com.braintribe.model.access.smood.basic.SmoodAccess;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.query.smart.test.model.deployment.MoodAccess;
import com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup;
import com.braintribe.model.processing.session.impl.persistence.BasicPersistenceGmSession;
import com.braintribe.testing.tools.gm.access.TransientNonIncrementalAccess;

/**
 * 
 */
public class DelegateAccessSetup {

	protected final SmartMappingSetup setup;

	protected BasicPersistenceGmSession session;
	protected SmoodAccess smoodAccessA;
	protected SmoodAccess smoodAccessB;
	protected MoodAccess smoodDenotationA;
	protected MoodAccess smoodDenotationB;

	public DelegateAccessSetup(SmartMappingSetup setup) {
		this.setup = setup;
	}

	// ######################################
	// ## . . . . . Smood Accesses . . . . ##
	// ######################################

	public SmoodAccess getAccessA() {
		if (smoodAccessA == null) {
			smoodAccessA = configureSmoodAccess(setup.modelA, accessIdA, accessIdA);
		}
		return smoodAccessA;
	}

	public SmoodAccess getAccessB() {
		if (smoodAccessB == null) {
			smoodAccessB = configureSmoodAccess(setup.modelB, accessIdB, accessIdB);
		}
		return smoodAccessB;
	}

	public MoodAccess getDenotationAccessA() {
		if (smoodDenotationA == null) {
			smoodDenotationA = configureSmoodDenotation(accessIdA, setup.modelA);
		}
		return smoodDenotationA;
	}

	public MoodAccess getDenotationAccessB() {
		if (smoodDenotationB == null) {
			smoodDenotationB = configureSmoodDenotation(accessIdB, setup.modelB);
		}
		return smoodDenotationB;
	}

	protected SmoodAccess configureSmoodAccess(GmMetaModel metaModel, String name, String... partitions) {
		SmoodAccess result = newSmoodAccess(partitions);
		result.setAccessId(name);
		result.setDataDelegate(new TransientNonIncrementalAccess(metaModel));
		result.setReadWriteLock(EmptyReadWriteLock.INSTANCE);
		result.setDefaultTraversingCriteria(null);
		return result;
	}

	protected SmoodAccess newSmoodAccess(String... partitions) {
		return new MultiPartitionSmoodAccess(asSet(partitions));
	}

	private MoodAccess configureSmoodDenotation(String name, GmMetaModel model) {
		MoodAccess result = MoodAccess.T.createPlain();
		result.setMetaModel(model);
		result.setExternalId(name);

		return result;
	}

}
