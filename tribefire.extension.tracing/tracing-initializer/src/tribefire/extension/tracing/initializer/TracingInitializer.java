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
package tribefire.extension.tracing.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.tracing.initializer.wire.TracingInitializerWireModule;
import tribefire.extension.tracing.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.tracing.initializer.wire.contract.TracingInitializerMainContract;

public class TracingInitializer extends AbstractInitializer<TracingInitializerMainContract> {

	@Override
	public WireTerminalModule<TracingInitializerMainContract> getInitializerWireModule() {
		return TracingInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<TracingInitializerMainContract> initializerContext,
			TracingInitializerMainContract initializerMainContract) {

		@SuppressWarnings("unused")
		GmMetaModel cortexModel = initializerMainContract.coreInstances().cortexModel();

		ExistingInstancesContract existingInstances = initializerMainContract.existingInstances();

		@SuppressWarnings("unused")
		GmMetaModel deploymentModel = existingInstances.deploymentModel();
		GmMetaModel serviceModel = existingInstances.serviceModel();
		GmMetaModel cortexServiceModel = initializerMainContract.coreInstances().cortexServiceModel();
		cortexServiceModel.getDependencies().add(serviceModel);

		initializerMainContract.initializer().healthCheckProcessor();
		initializerMainContract.initializer().functionalCheckBundle();

		// only initialize in case of default config
		if (initializerMainContract.properties().TRACING_CREATE_DEFAULT_CONTEXT()) {
			initializerMainContract.initializer().setupDefaultConfiguration(initializerMainContract.properties().TRACING_DEMO_TRACING_CONNECTOR());

			cortexServiceModel.getDependencies().add(existingInstances.demoServiceModel());
		}
	}
}
