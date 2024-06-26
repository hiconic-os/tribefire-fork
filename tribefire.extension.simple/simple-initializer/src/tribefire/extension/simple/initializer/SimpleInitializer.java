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
package tribefire.extension.simple.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.simple.initializer.wire.SimpleInitializerWireModule;
import tribefire.extension.simple.initializer.wire.contract.SimpleInitializerContract;
import tribefire.extension.simple.initializer.wire.contract.SimpleInitializerMainContract;
import tribefire.extension.simple.initializer.wire.contract.SimpleInitializerModelsContract;

public class SimpleInitializer extends AbstractInitializer<SimpleInitializerMainContract> {

	@Override
	public WireTerminalModule<SimpleInitializerMainContract> getInitializerWireModule() {
		return SimpleInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<SimpleInitializerMainContract> initializerContext,
			SimpleInitializerMainContract initializerMainContract) {

		SimpleInitializerModelsContract models = initializerMainContract.models();
		CoreInstancesContract coreInstances = initializerMainContract.coreInstances();

		GmMetaModel cortexModel = coreInstances.cortexModel();
		GmMetaModel cortexServiceModel = coreInstances.cortexServiceModel();

		cortexModel.getDependencies().add(models.configuredSimpleDeploymentModel());
		cortexServiceModel.getDependencies().add(models.configuredSimpleServiceModel());

		SimpleInitializerContract initializer = initializerMainContract.initializer();

		initializer.simpleInMemoryAccess();
		initializer.simpleEchoProcessor();
		initializer.simpleWebTerminal();

		initializer.addMetaDataToModels();
	}

}
