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
package tribefire.extension.modelling_wb;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.modelling_wb.wire.ModellingWbInitializerWireModule;
import tribefire.extension.modelling_wb.wire.contract.ModellingWbInitializerContract;
import tribefire.extension.modelling_wb.wire.contract.ModellingWbInitializerMainContract;

public class ModellingWbInitializer extends AbstractInitializer<ModellingWbInitializerMainContract> {

	@Override
	public WireTerminalModule<ModellingWbInitializerMainContract> getInitializerWireModule() {
		return ModellingWbInitializerWireModule.INSTANCE;
	}
	
	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<ModellingWbInitializerMainContract> initializerContext,
			ModellingWbInitializerMainContract mainContract) {

		DefaultWbContract workbench = mainContract.workbenchContract();
		ModellingWbInitializerContract modellingWb = mainContract.initializerContract();
		
		// add entry point
		workbench.defaultRootPerspective().getFolders().add(modellingWb.entryPointFolder());
		
		// add actions
		workbench.defaultActionbarFolder().getSubFolders().remove(workbench.deleteEntityFolder());
		workbench.defaultActionbarFolder().getSubFolders().remove(workbench.workWithEntityFolder());
		workbench.defaultActionbarFolder().getSubFolders().remove(workbench.addToClipboardFolder());
		
		workbench.defaultActionbarFolder().getSubFolders().addAll(modellingWb.actionbarFolders());
	}
}
