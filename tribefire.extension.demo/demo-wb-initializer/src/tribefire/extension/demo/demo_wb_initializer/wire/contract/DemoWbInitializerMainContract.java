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
package tribefire.extension.demo.demo_wb_initializer.wire.contract;

import com.braintribe.wire.api.space.WireSpace;

import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;

/**
 * <p>
 * The main contract of an initializer serves to expose all relevant contracts to the "outside" world. <br>
 * It is highly recommended that every initializer contains a main contract to be extensible (once a contract is
 * bound to its WireModule, an interface change would often result in breaking changes).
 * 
 * <p>
 * In this example two internal contracts <code>demoWorkbenchInitializerIconContract</code> and
 * <code>demoWorkbenchInitializerContract</code>, as well as the depended <code>workbenchContract</code> is exposed.
 *
 */
public interface DemoWbInitializerMainContract extends WireSpace {

	/**
	 * Coming from DefaultWbWireModule.
	 */
	DefaultWbContract workbenchContract();

	/**
	 * Coming from this initializer.
	 */
	DemoWbInitializerIconContract demoWorkbenchInitializerIconContract();

	/**
	 * Coming from CoreInstancesWireModule.
	 */
	DemoWbInitializerContract demoWorkbenchInitializerContract();

}
