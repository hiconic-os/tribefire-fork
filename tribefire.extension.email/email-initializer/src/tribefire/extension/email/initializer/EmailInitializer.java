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
package tribefire.extension.email.initializer;

import java.util.Set;

import com.braintribe.gm.model.reason.essential.CommunicationError;
import com.braintribe.gm.model.reason.essential.InternalError;
import com.braintribe.gm.model.reason.essential.NotFound;
import com.braintribe.model.ddra.DdraMapping;
import com.braintribe.model.email.service.EmailServiceRequest;
import com.braintribe.model.email.service.reason.ConfigurationMissing;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.email.initializer.wire.EmailInitializerWireModule;
import tribefire.extension.email.initializer.wire.contract.EmailInitializerContract;
import tribefire.extension.email.initializer.wire.contract.EmailInitializerMainContract;
import tribefire.extension.email.initializer.wire.contract.ExistingInstancesContract;

public class EmailInitializer extends AbstractInitializer<EmailInitializerMainContract> {

	@Override
	public WireTerminalModule<EmailInitializerMainContract> getInitializerWireModule() {
		return EmailInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<EmailInitializerMainContract> initializerContext,
			EmailInitializerMainContract initializerMainContract) {
		initializerMainContract.coreInstances().cortexModel().getDependencies().add(initializerMainContract.existingInstances().deploymentModel());
		initializerMainContract.coreInstances().cortexServiceModel().getDependencies()
				.add(initializerMainContract.existingInstances().serviceModel());

		EmailInitializerContract initializer = initializerMainContract.initializer();
		initializer.emailServiceProcessor();
		initializer.connectivityCheckBundle();
		initializer.apiServiceDomain();

		ExistingInstancesContract existingInstances = initializerMainContract.existingInstances();
		Set<DdraMapping> ddraMappings = existingInstances.ddraConfiguration().getMappings();
		ddraMappings.addAll(initializer.ddraMappings());

		configureMetaData(initializerMainContract);

	}

	private void configureMetaData(EmailInitializerMainContract initializerMainContract) {
		EmailInitializerContract initializer = initializerMainContract.initializer();

		BasicModelMetaDataEditor editor = new BasicModelMetaDataEditor(initializerMainContract.models().configuredEmailApiModel());
		editor.onEntityType(EmailServiceRequest.T).addMetaData(initializerMainContract.initializer().processWithEmailServiceProcessor());

		editor.onEntityType(CommunicationError.T).addMetaData(initializer.httpStatus502Md(), initializer.logReasonTrace());
		editor.onEntityType(NotFound.T).addMetaData(initializer.httpStatus404Md(), initializer.logReasonTrace());
		editor.onEntityType(InternalError.T).addMetaData(initializer.httpStatus500Md(), initializer.logReasonTrace());
		editor.onEntityType(ConfigurationMissing.T).addMetaData(initializer.httpStatus501Md(), initializer.logReasonTrace());
	}
}
