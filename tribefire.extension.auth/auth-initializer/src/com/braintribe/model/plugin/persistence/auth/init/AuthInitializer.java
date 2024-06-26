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
package com.braintribe.model.plugin.persistence.auth.init;

import com.braintribe.gm.persistence.initializer.support.api.SimplifiedWireSupportedInitializer;
import com.braintribe.gm.persistence.initializer.support.api.WiredInitializerContext;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.plugin.persistence.auth.init.wire.AuthInitializerWireModule;
import com.braintribe.model.plugin.persistence.auth.init.wire.contract.AuthMainContract;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

public class AuthInitializer implements SimplifiedWireSupportedInitializer<AuthMainContract> {

	@Override
	public WireTerminalModule<AuthMainContract> getWireModule() {
		return AuthInitializerWireModule.INSTANCE;
	}

	@Override
	public void initializeModels(PersistenceInitializationContext context, WiredInitializerContext<AuthMainContract> initializerContext,
			AuthMainContract initializerContract) {

		//GmMetaModel cortexModel = initializerContract.coreInstances().cortexModel();

		//cortexModel.getDependencies().add(initializerContract.existingInstances().deploymentModel());

	}

	@Override
	public void initializeData(PersistenceInitializationContext context, WiredInitializerContext<AuthMainContract> initializerContext,
			AuthMainContract initializerContract) {

		initializerContract.metaData().metaData();

	}
}
