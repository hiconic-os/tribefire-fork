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
package tribefire.extension.opentracing.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.opentracing.initializer.wire.OpentracingInitializerWireModule;
import tribefire.extension.opentracing.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.opentracing.initializer.wire.contract.OpentracingInitializerMainContract;

public class OpentracingInitializer extends AbstractInitializer<OpentracingInitializerMainContract> {

	@Override
	public WireTerminalModule<OpentracingInitializerMainContract> getInitializerWireModule() {
		return OpentracingInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<OpentracingInitializerMainContract> initializerContext,
			OpentracingInitializerMainContract initializerMainContract) {

		// TODO: add service models to cortex

		GmMetaModel cortexModel = initializerMainContract.coreInstances().cortexModel();

		ExistingInstancesContract existingInstances = initializerMainContract.existingInstances();

		GmMetaModel deploymentModel = existingInstances.deploymentModel();
		GmMetaModel serviceModel = existingInstances.serviceModel();
		cortexModel.getDependencies().add(deploymentModel);
		cortexModel.getDependencies().add(serviceModel);

		initializerMainContract.initializer().setupDefaultConfiguration();
	}
}
