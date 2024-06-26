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
package tribefire.extension.modelling.management_wb;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.modelling.management_wb.wire.ManagementWbInitializerWireModule;
import tribefire.extension.modelling.management_wb.wire.contract.ManagementWbInitializerContract;
import tribefire.extension.modelling.management_wb.wire.contract.ManagementWbInitializerMainContract;

public class ManagementWbInitializer extends AbstractInitializer<ManagementWbInitializerMainContract> {

	@Override
	public WireTerminalModule<ManagementWbInitializerMainContract> getInitializerWireModule() {
		return ManagementWbInitializerWireModule.INSTANCE;
	}
	
	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<ManagementWbInitializerMainContract> initializerContext,
			ManagementWbInitializerMainContract mainContract) {

		DefaultWbContract workbench = mainContract.workbenchContract();
		ManagementWbInitializerContract managementWb = mainContract.initializerContract();
		
		// add entry point
		workbench.defaultRootPerspective().getFolders().add(managementWb.entryPointFolder());
		
		// add to home folder
		workbench.defaultHomeFolderPerspective().getFolders().add(managementWb.projectFolder());
		
		// add actions
		workbench.defaultActionbarFolder().getSubFolders().remove(workbench.deleteEntityFolder());
		workbench.defaultActionbarFolder().getSubFolders().remove(workbench.workWithEntityFolder());
		workbench.defaultActionbarFolder().getSubFolders().remove(workbench.addToClipboardFolder());
		workbench.defaultActionbarFolder().getSubFolders().addAll(managementWb.actionbarFolders());
	}
}
