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
package tribefire.cortex.assets.default_wb_initializer;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.assets.default_wb_initializer.wire.DefaultWbWireModule;
import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbContract;
import tribefire.cortex.assets.default_wb_initializer.wire.contract.DefaultWbMainContract;
import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;

public class DefaultWbInitializer extends AbstractInitializer<DefaultWbMainContract> {

	@Override
	public WireTerminalModule<DefaultWbMainContract> getInitializerWireModule() {
		return DefaultWbWireModule.INSTANCE;
	}

	@Override
	protected void initialize(PersistenceInitializationContext context,
			WiredInitializerContext<DefaultWbMainContract> initializerContext,
			DefaultWbMainContract initializerContract) {
		
		DefaultWbContract workbench = initializerContract.workbenchContract();
		
		workbench.defaultRootPerspective();
		workbench.defaultActionbarPerspective();
		workbench.defaultHomeFolderPerspective();
		workbench.defaultGlobalActionbarPerspective();
		workbench.defaultHeaderbarPerspective();
		workbench.defaultTabActionbarPerspective();
		workbench.defaultViewActionbarPerspective();
		
		workbench.defaultWorkbenchConfiguration();
		
	}
	
}