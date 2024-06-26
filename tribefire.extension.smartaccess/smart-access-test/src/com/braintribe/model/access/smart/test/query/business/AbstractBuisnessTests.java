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
package com.braintribe.model.access.smart.test.query.business;

import static com.braintribe.model.processing.query.smart.test.setup.base.SmartMappingSetup.accessIdB;

import com.braintribe.model.access.smart.test.query.AbstractSmartQueryTests;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.processing.query.smart.test.model.accessA.business.CustomerA;
import com.braintribe.model.processing.query.smart.test.model.accessB.business.JdeInventoryB;
import com.braintribe.model.processing.query.smart.test.model.accessB.business.SapInventoryB;
import com.braintribe.model.processing.smood.Smood;

/**
 * This is a base class for business-tests. It is a random collection of special use-cases that occurred in real-life and were causing
 * problems.
 */
public class AbstractBuisnessTests extends AbstractSmartQueryTests {

	protected CustomerA customerA(String ucn) {
		CustomerA result =  CustomerA.T.create();
		result.setUcn(ucn);
		result.setPartition(setup.getAccessA().getAccessId());

		return register(smoodA, result);
	}

	protected JdeInventoryB jde(String ucn, String info) {
		JdeInventoryB result = JdeInventoryB.T.create();
		result.setUcn(ucn);
		result.setProductInfo(info);
		result.setPartition(accessIdB);

		return register(smoodB, result);
	}

	protected SapInventoryB sap(String ucn, String info) {
		SapInventoryB result = SapInventoryB.T.create();
		result.setUcn(ucn);
		result.setProductInfo(info);
		result.setPartition(accessIdB);

		return register(smoodB, result);
	}

	private <T extends GenericEntity> T register(Smood smood, T entity) {
		smood.registerEntity(entity, true);
		return entity;
	}

}
