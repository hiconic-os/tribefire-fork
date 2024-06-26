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
package tribefire.extension.ldap.initializer;

import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.ldap.initializer.wire.LdapInitializerModuleWireModule;
import tribefire.extension.ldap.initializer.wire.contract.LdapInitializerModuleContract;
import tribefire.extension.ldap.initializer.wire.contract.LdapInitializerModuleMainContract;
import tribefire.extension.ldap.initializer.wire.contract.RuntimePropertiesContract;

public class LdapInitializer extends AbstractInitializer<LdapInitializerModuleMainContract> {

	@Override
	public WireTerminalModule<LdapInitializerModuleMainContract> getInitializerWireModule() {
		return LdapInitializerModuleWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<LdapInitializerModuleMainContract> initializerContext,
			LdapInitializerModuleMainContract initializerMainContract) {

		LdapInitializerModuleContract initializer = initializerMainContract.initializer();

		RuntimePropertiesContract properties = initializerMainContract.properties();
		if (properties.LDAP_INITIALIZE_DEFAULTS() && initializer.isLdapConfigured()) {

			initializerMainContract.models().registerModels();
			initializerMainContract.models().metaData();

			initializerMainContract.initializer().connection();
			initializerMainContract.initializer().ldapUserAccess();
			initializerMainContract.initializer().ldapAccess();
			initializerMainContract.initializer().authentication();
			initializerMainContract.initializer().cortexConfiguration();

		}

		initializer.connectivityCheckBundle();

	}
}
