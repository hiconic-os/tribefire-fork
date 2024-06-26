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
package tribefire.extension.metrics.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.metrics.initializer.wire.MetricsInitializerWireModule;
import tribefire.extension.metrics.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.metrics.initializer.wire.contract.MetricsInitializerMainContract;

public class MetricsInitializer extends AbstractInitializer<MetricsInitializerMainContract> {

	@Override
	public WireTerminalModule<MetricsInitializerMainContract> getInitializerWireModule() {
		return MetricsInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<MetricsInitializerMainContract> initializerContext,
			MetricsInitializerMainContract initializerMainContract) {

		GmMetaModel cortexModel = initializerMainContract.coreInstances().cortexModel();

		ExistingInstancesContract existingInstances = initializerMainContract.existingInstances();

		GmMetaModel deploymentModel = existingInstances.deploymentModel();
		GmMetaModel serviceModel = existingInstances.serviceModel();
		GmMetaModel cortexServiceModel = initializerMainContract.coreInstances().cortexServiceModel();
		cortexServiceModel.getDependencies().add(serviceModel);

		// initializerMainContract.initializer().healthCheckProcessor();
		initializerMainContract.initializer().functionalCheckBundle();

		// only initialize in case of default config
		if (initializerMainContract.properties().METRICS_CREATE_DEMO_CONTEXT()) {
			initializerMainContract.initializer().setupDefaultConfiguration(initializerMainContract.properties().METRICS_DEMO_METRICS_CONNECTOR());
		}
	}
}
