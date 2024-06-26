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
package com.braintribe.model.processing.query.smart.test2.shared;

import com.braintribe.model.accessdeployment.smart.meta.AsIs;
import com.braintribe.model.processing.query.smart.test2._common.SmartModelTestSetup;
import com.braintribe.model.processing.query.smart.test2._common.SmartTestSetupConstants;
import com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedFile;
import com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedFileDescriptor;
import com.braintribe.model.processing.query.smart.test2.shared.model.shared.SharedSource;
import com.braintribe.model.processing.smart.mapping.api.SmartMappingEditor;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlanner;

/**
 * A SmartEntity is mapped to exactly one delegate, all properties {@link AsIs}, thus the {@link SmartQueryPlanner} is
 * completely bypassed and the original query is directly passed to the delegate access.
 * 
 * @author peter.gazdik
 */
public class DelegateAsIsSmartSetup implements SmartTestSetupConstants {

	public static final SmartModelTestSetup DELEGATE_AS_IS_SETUP = get();

	private static SmartModelTestSetup get() {
		return _SharedSetupBase.get(DelegateAsIsSmartSetup::configureMappings, "smart.delegate-as-is");
	}

	private static void configureMappings(SmartMappingEditor mapper) {
		/* This is for the simpler use-case, this entity has no dependencies on other entities, thus it's easy to check
		 * it can be fully delegated */
		mapper.onEntityType(SharedSource.T, "root") //
				.forDelegate(accessIdA) //
				.allAsIs();

		/* These two are for the more complicated use-case with QualifiedEntity/Property Assignments. Still, is
		 * effectively 1:1, should be delegated. */
		mapper.onEntityType(SharedFile.T, "root") //
				.forDelegate(accessIdA) //
				.allAsIs();

		mapper.onEntityType(SharedFileDescriptor.T, "root") //
				.forDelegate(accessIdA) //
				.entityTo(SharedFileDescriptor.T) //
				.propertiesAsIs().propertyTo(SharedFileDescriptor.file, SharedFileDescriptor.file);
	}
}
