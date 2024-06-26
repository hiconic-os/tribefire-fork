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
package tribefire.extension.library.initializer;

import java.util.Set;

import com.braintribe.model.ddra.DdraMapping;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.library.initializer.wire.LibraryInitializerModuleWireModule;
import tribefire.extension.library.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.library.initializer.wire.contract.LibraryInitializerModuleContract;
import tribefire.extension.library.initializer.wire.contract.LibraryInitializerModuleMainContract;

public class LibraryInitializer extends AbstractInitializer<LibraryInitializerModuleMainContract> {

	@Override
	public WireTerminalModule<LibraryInitializerModuleMainContract> getInitializerWireModule() {
		return LibraryInitializerModuleWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<LibraryInitializerModuleMainContract> initializerContext,
			LibraryInitializerModuleMainContract initializerMainContract) {

		GmMetaModel cortexModel = initializerMainContract.coreInstances().cortexModel();

		cortexModel.getDependencies().add(initializerMainContract.existingInstances().deploymentModel());

		LibraryInitializerModuleContract initializer = initializerMainContract.initializer();
		initializer.metaData();

		if (initializer.isDbAccess()) {
			initializer.dbConnector();
			initializer.sqlBinaryProcessor();
		}
		initializer.libraryAccess();
		initializer.libraryService();
		initializer.updateNvdMirrorScheduledJob();

		ExistingInstancesContract existingInstances = initializerMainContract.existingInstances();
		Set<DdraMapping> ddraMappings = existingInstances.ddraConfiguration().getMappings();
		ddraMappings.addAll(initializer.ddraMappings());

		
		
	}
}
