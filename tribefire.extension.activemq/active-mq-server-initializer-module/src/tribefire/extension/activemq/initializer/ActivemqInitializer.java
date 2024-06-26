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
package tribefire.extension.activemq.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.activemq.initializer.wire.ActivemqInitializerModuleWireModule;
import tribefire.extension.activemq.initializer.wire.contract.ActivemqInitializerModuleContract;
import tribefire.extension.activemq.initializer.wire.contract.ActivemqInitializerModuleMainContract;

public class ActivemqInitializer extends AbstractInitializer<ActivemqInitializerModuleMainContract> {

	@Override
	public WireTerminalModule<ActivemqInitializerModuleMainContract> getInitializerWireModule() {
		return ActivemqInitializerModuleWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context,
			WiredInitializerContext<ActivemqInitializerModuleMainContract> initializerContext,
			ActivemqInitializerModuleMainContract initializerMainContract) {

		GmMetaModel cortexModel = initializerMainContract.coreInstances().cortexModel();
		cortexModel.getDependencies().add(initializerMainContract.existingInstances().deploymentModel());

		ActivemqInitializerModuleContract initializer = initializerMainContract.initializer();

		if (initializerMainContract.properties().AMQ_DEPLOYMENT_START()) {
			initializer.worker();
		}
		initializer.functionalCheckBundle();
		initializer.healthCheckProcessor();
	}
}
