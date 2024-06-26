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
package tribefire.cortex.assets.tribefire_connector.wire.space;

import com.braintribe.model.deployment.tribefire.connector.LocalTribefireConnection;
import com.braintribe.model.deployment.tribefire.connector.RemoteTribefireConnection;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.assets.tribefire_connector.binding.TribefireConnectionBinder;
import tribefire.module.api.InitializerBindingBuilder;
import tribefire.module.api.WireContractBindingBuilder;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefirePlatformContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class TribefireConnectorModuleSpace implements TribefireModuleContract {

	@Import
	private TribefirePlatformContract tfPlatform;
		
	@Import
	private TribefireConnectionsSpace tribefireConnections;

	//
	// WireContracts
	//

	@Override
	public void bindWireContracts(WireContractBindingBuilder bindings) {
		//
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
		bindings.bind(LocalTribefireConnection.T).component(TribefireConnectionBinder.INSTANCE).expertFactory(tribefireConnections::localTribefireConnection);
		bindings.bind(RemoteTribefireConnection.T).component(TribefireConnectionBinder.INSTANCE).expertFactory(tribefireConnections::remoteTribefireConnection);
	}

}
