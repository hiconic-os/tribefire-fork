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
package tribefire.extension.azure.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.azure.model.deployment.AzureBlobBinaryProcessor;
import tribefire.extension.azure.model.deployment.AzureServiceProcessor;
import tribefire.extension.azure.model.deployment.HealthCheckProcessor;
import tribefire.module.api.InitializerBindingBuilder;
import tribefire.module.api.WireContractBindingBuilder;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class AzureModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private AzureDeployablesSpace deployables;

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
		bindings.bind(AzureBlobBinaryProcessor.T) //
			.component(tfPlatform.binders().binaryRetrievalProcessor()) //
			.expertFactory(deployables::binaryProcessor) //
			.component(tfPlatform.binders().binaryPersistenceProcessor()) //
			.expertFactory(deployables::binaryProcessor);
		
		bindings.bind(AzureServiceProcessor.T) //
			.component(tfPlatform.binders().serviceProcessor()) //
			.expertSupplier(deployables::azureServiceProcessor);
		
		bindings.bind(HealthCheckProcessor.T) //
			.component(tfPlatform.binders().checkProcessor()) //
			.expertSupplier(this.deployables::healthCheckProcessor);		
		//@formatter:on
	}

}
