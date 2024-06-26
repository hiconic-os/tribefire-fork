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
package tribefire.extension.demo.demo_wb_initializer;

import static com.braintribe.wire.api.util.Lists.list;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.demo.demo_wb_initializer.wire.DemoWbWireModule;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerContract;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerIconContract;
import tribefire.extension.demo.demo_wb_initializer.wire.contract.DemoWbInitializerMainContract;

/**
 * <p>
 * This initializer shows how to set up a workbench via code. <br>
 * It sets up upon the default workbench configuration which is being applied via asset dependency
 * <code>tribefire.cortex.assets:tribefire-default-workbench-initializer</code>. <br>

 * <p>
 * A good alternative to this approach is to use the GME as it provides a convenient way to create
 * the same configuration via UI support.
 *  
 */
public class DemoWbInitializer extends AbstractInitializer<DemoWbInitializerMainContract>{

	@Override
	public WireTerminalModule<DemoWbInitializerMainContract> getInitializerWireModule() {
		return DemoWbWireModule.INSTANCE;
	}
	
	@Override
	protected void initialize(PersistenceInitializationContext context,
			WiredInitializerContext<DemoWbInitializerMainContract> initializerContext,
			DemoWbInitializerMainContract initializerContract) {

		DefaultWbContract workbench = initializerContract.workbenchContract();
		DemoWbInitializerIconContract iconContract = initializerContract.demoWorkbenchInitializerIconContract();
		DemoWbInitializerContract demoWorkbench = initializerContract.demoWorkbenchInitializerContract();

		// update header bar
		workbench.tbLogoFolder().setIcon(iconContract.logoIcon());

		// add entry point
		workbench.defaultRootPerspective().getFolders().add(demoWorkbench.entryPointFolder());

		// add to home folder
		workbench.defaultHomeFolderPerspective().getFolders()
				.addAll(list(
						demoWorkbench.personFolder(), //
						demoWorkbench.companyFolder() //
						));

		// add actions
		workbench.defaultActionbarPerspective().getFolders().add(demoWorkbench.employeesByGenderFolder());
		workbench.defaultActionbarPerspective().getFolders().add(demoWorkbench.newEmployeeFolder());

	}
	
}