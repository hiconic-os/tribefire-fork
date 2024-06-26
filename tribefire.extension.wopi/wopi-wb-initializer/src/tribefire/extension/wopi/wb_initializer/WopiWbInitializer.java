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
package tribefire.extension.wopi.wb_initializer;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.wopi.wb_initializer.wire.WopiWbInitializerWireModule;
import tribefire.extension.wopi.wb_initializer.wire.contract.CommonWorkbenchContract;
import tribefire.extension.wopi.wb_initializer.wire.contract.IconContract;
import tribefire.extension.wopi.wb_initializer.wire.contract.ResourcesContract;
import tribefire.extension.wopi.wb_initializer.wire.contract.WopiWbInitializerContract;
import tribefire.extension.wopi.wb_initializer.wire.contract.WopiWbInitializerMainContract;

public class WopiWbInitializer extends AbstractInitializer<WopiWbInitializerMainContract> {

	@Override
	public WireTerminalModule<WopiWbInitializerMainContract> getInitializerWireModule() {
		return WopiWbInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<WopiWbInitializerMainContract> initializerContext,
			WopiWbInitializerMainContract initializerMainContract) {

		DefaultWbContract workbench = initializerMainContract.workbench();
		IconContract icons = initializerMainContract.icons();
		WopiWbInitializerContract wopiWorkbench = initializerMainContract.initializer();
		ResourcesContract resources = initializerMainContract.resources();
		CommonWorkbenchContract commonWorkbench = initializerMainContract.commonWorkbench();

		// update standard actions
		commonWorkbench.standardActions();

		// update header bar
		workbench.tbLogoFolder().setIcon(icons.logoIcon());

		// add entry point
		workbench.defaultRootPerspective().getFolders().add(wopiWorkbench.entryPointFolder());

		// update action bar
		workbench.defaultActionbarFolder().getSubFolders().addAll(wopiWorkbench.actionbarFolders());

		// -----------------------------------------------------------------------
		// ADD TO HOME FOLDER
		// -----------------------------------------------------------------------

		// workbenchContract.defaultHomeFolderPerspective().getFolders().addAll(list(wopiWorkbench.wopi()));

		// -----------------------------------------------------------------------
		// ADD ACTIONS
		// -----------------------------------------------------------------------

		// -----------------------------------------------------------------------
		// EXPLORER STYLING
		// -----------------------------------------------------------------------

		String explorerTitle = "WOPI Administrator";
		workbench.defaultWorkbenchConfiguration().setTitle(explorerTitle);
		workbench.defaultWorkbenchConfiguration().setFavIcon(resources.favIconOrange());
		updateCss(workbench, resources);
	}

	// -----------------------------------------------------------------------
	// HELPERS
	// -----------------------------------------------------------------------

	private void updateCss(DefaultWbContract workbench, ResourcesContract resources) {
		workbench.defaultWorkbenchConfiguration().setStylesheet(resources.extensionWopiExplorerCss());
	}
}
