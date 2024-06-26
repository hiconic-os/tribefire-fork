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
package tribefire.extension.messaging.initializer;

import com.braintribe.logging.Logger;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.messaging.initializer.wire.MessagingInitializerWireModule;
import tribefire.extension.messaging.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.messaging.initializer.wire.contract.MessagingInitializerMainContract;

public class MessagingInitializer extends AbstractInitializer<MessagingInitializerMainContract> {

	private static final Logger logger = Logger.getLogger(MessagingInitializer.class);

	@Override
	public WireTerminalModule<MessagingInitializerMainContract> getInitializerWireModule() {
		return MessagingInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<MessagingInitializerMainContract> initializerContext,
			MessagingInitializerMainContract initializerMainContract) {

		@SuppressWarnings("unused")
		GmMetaModel cortexModel = initializerMainContract.coreInstances().cortexModel();

		ExistingInstancesContract existingInstances = initializerMainContract.existingInstances();

		@SuppressWarnings("unused")
		GmMetaModel deploymentModel = existingInstances.deploymentModel();
		GmMetaModel serviceModel = existingInstances.serviceModel();
		GmMetaModel cortexServiceModel = initializerMainContract.coreInstances().cortexServiceModel();

		initializerMainContract.initializer().healthCheckProcessor();
		initializerMainContract.initializer().functionalCheckBundle();

		if (initializerMainContract.properties().MESSAGING_CREATE_DEFAULT_SETUP()) {
			logger.info(() -> "Creating a default setup configuration");
			initializerMainContract.defaults().setupDefaultConfiguration();
			cortexServiceModel.getDependencies().add(existingInstances.defaultConfiguredServiceModel());
		} else {
			logger.debug(() -> "Not creating a default setup configuration");
		}

		// TODO INITIALIZER FOR TEST PURPOSE COMMENT OUT BEFORE COMMIT !!!
		// initializerMainContract.initializer().setupDefaultConfiguration();
		// WARNING: this line should be after setupDefaultConfiguration is done
		// cortexServiceModel.getDependencies().add(existingInstances.messagingProducerServiceModel());
		// cortexServiceModel.getDependencies().add(existingInstances.messagingConsumerServiceModel());
	}
}
