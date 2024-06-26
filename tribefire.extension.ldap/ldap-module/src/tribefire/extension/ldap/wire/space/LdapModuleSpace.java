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
package tribefire.extension.ldap.wire.space;

import com.braintribe.model.ldapaccessdeployment.HealthCheckProcessor;
import com.braintribe.model.ldapaccessdeployment.LdapAccess;
import com.braintribe.model.ldapaccessdeployment.LdapUserAccess;
import com.braintribe.model.ldapauthenticationdeployment.LdapAuthentication;
import com.braintribe.model.ldapconnectiondeployment.LdapConnection;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.module.api.InitializerBindingBuilder;
import tribefire.module.api.WireContractBindingBuilder;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefirePlatformContract;
import tribefire.module.wire.contract.WebPlatformBindersContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class LdapModuleSpace implements TribefireModuleContract {

	@Import
	private TribefirePlatformContract tfPlatform;

	@Import
	private WebPlatformBindersContract commonComponents;

	@Import
	private LdapDeployablesSpace deployables;

	//
	// WireContracts
	//

	@Override
	public void bindWireContracts(WireContractBindingBuilder bindings) {
		// Bind wire contracts to make them available for other modules.
		// Note that the Contract class cannot be defined in this module, but must be in a gm-api artifact.
	}

	//
	// Hardwired deployables
	//

	@Override
	public void bindHardwired() {
		// Bind hardwired deployables here.
	}

	//
	// Initializers
	//

	@Override
	public void bindInitializers(InitializerBindingBuilder bindings) {
		// Bind DataInitialiers for various CollaborativeAcceses
	}

	//
	// Deployables
	//

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {

		//@formatter:off
		bindings.bind(LdapAccess.T)
			.component(commonComponents.incrementalAccess())
			.expertFactory(deployables::access);
		
		bindings.bind(LdapUserAccess.T)
			.component(commonComponents.incrementalAccess())
			.expertFactory(deployables::userAccess);
		
		bindings.bind(LdapConnection.T)
			.component(com.braintribe.utils.ldap.LdapConnection.class)
			.expertFactory(deployables::connection);
		
		bindings.bind(LdapAuthentication.T)
			.component(commonComponents.authenticationService())
			.expertFactory(deployables::service);
		
		bindings.bind(HealthCheckProcessor.T).component(commonComponents.checkProcessor())
			.expertFactory(this.deployables::healthCheckProcessor);
		
		//@formatter:on

	}

}
