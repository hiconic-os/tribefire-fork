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
package tribefire.cortex.assets.darktheme_wb_initializer;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.model.resource.Resource;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.assets.darktheme_wb_initializer.wire.DarkthemeWbWireModule;
import tribefire.cortex.assets.darktheme_wb_initializer.wire.contract.DarkthemeWbIconContract;
import tribefire.cortex.assets.darktheme_wb_initializer.wire.contract.DarkthemeWbStyleContract;
import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;

public class DarkthemeWbInitializer extends AbstractInitializer<DarkthemeWbStyleContract> {

	@Override
	public WireTerminalModule<DarkthemeWbStyleContract> getInitializerWireModule() {
		return DarkthemeWbWireModule.INSTANCE;
	}
	
	@Override
	protected void initialize(PersistenceInitializationContext context,
			WiredInitializerContext<DarkthemeWbStyleContract> initializerContext, DarkthemeWbStyleContract initializerContract) {
		
		Resource grayishBlueStylesheet = initializerContract.styleSheet();
		
		DefaultWbContract workbench = initializerContract.workbenchContract();
		DarkthemeWbIconContract icon = initializerContract.iconContract();
		
		workbench.defaultWorkbenchConfiguration().setStylesheet(grayishBlueStylesheet);
		
		workbench.executeServiceRequestFolder().setIcon(icon.run());
		workbench.tbLogoFolder().setIcon(icon.logo());
		workbench.homeConstellationFolder().setIcon(icon.home());
		workbench.changesConstellationFolder().setIcon(icon.changes());
		workbench.transientChangesConstellationFolder().setIcon(icon.changes());
		workbench.clipboardConstellationFolder().setIcon(icon.clipboard());
		workbench.notificationsConstellationFolder().setIcon(icon.notification());
		workbench.quickAccessConstellationFolder().setIcon(icon.magnifier());
		workbench.newFolder().setIcon(icon.newIcon());
		workbench.uploadFolder().setIcon(icon.upload());
		workbench.undoFolder().setIcon(icon.undo());
		workbench.redoFolder().setIcon(icon.redo());
		workbench.commitFolder().setIcon(icon.commit());
		
	}
	
}