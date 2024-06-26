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
package tribefire.extension.modelling_cortex_initializer.wire.contract;

import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;

/**
 * <p>
 * The main contract of an initializer serves to expose all relevant contracts to the "outside" world. <br>
 * It is highly recommended that every initializer contains a main contract to be extensible (once a contract is bound
 * to its WireModule, an interface change would often result in breaking changes).
 * 
 * <p>
 * In this example three internal contracts <code>initializerContract</code>,
 * <code>initializerModelsContract</code> and <code>existingInstancesContract</code>, as well as the depended
 * <code>coreInstancesContract</code> are exposed.
 *
 */
public interface ModellingCortexMainContract extends WireSpace {

	/**
	 * Coming from this initializer.
	 */
	ModellingCortexContract initializerContract();

	/**
	 * Coming from this initializer.
	 */
	ModellingCortexModelsContract initializerModelsContract();
	
	/**
	 * Coming from this initializer.
	 */
	ExistingInstancesContract existingInstancesContract();

	/**
	 * Coming from CoreInstancesWireModule.
	 */
	CoreInstancesContract coreInstancesContract();

}
