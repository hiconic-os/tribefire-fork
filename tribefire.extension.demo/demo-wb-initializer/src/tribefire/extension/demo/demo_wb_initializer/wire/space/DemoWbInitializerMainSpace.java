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
package tribefire.extension.demo.demo_wb_initializer.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerContract;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerIconContract;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerMainContract;

/**
 * @see DemoWbInitializerMainContract
 */
@Managed
public class DemoWbInitializerMainSpace extends AbstractInitializerSpace implements DemoWbInitializerMainContract {

	@Import
	DefaultWbContract workbench;
	
	@Import
	DemoWbInitializerIconContract icons;
	
	@Import
	DemoWbInitializerContract demoWorkbench;
	
	/**
	 * Note: No @Managed annotation as we are just passing-through existing instances.
	 * Only annotate a managed instance if entities are created in there.
	 */
	@Override
	public DefaultWbContract workbenchContract() {
		return workbench;
	}
	
	
	/**
	 * Note: No @Managed annotation as we are just passing-through existing instances.
	 */
	@Override
	public DemoWbInitializerIconContract demoWorkbenchInitializerIconContract() {
		return icons;
	}

	
	/**
	 * Note: No @Managed annotation as we are just passing-through existing instances.
	 */
	@Override
	public DemoWbInitializerContract demoWorkbenchInitializerContract() {
		return demoWorkbench;
	}
}
