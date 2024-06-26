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
package tribefire.extension.gcp.wire.space;

import com.braintribe.model.gcp.deployment.GcpConnector;
import com.braintribe.model.gcp.deployment.GcpServiceProcessor;
import com.braintribe.model.gcp.deployment.GcpStorageBinaryProcessor;
import com.braintribe.model.gcp.deployment.HealthCheckProcessor;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.model.processing.gcp.connect.GcpStorageConnector;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class GcpModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private GcpDeployablesSpace deployables;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(GcpServiceProcessor.T) //
				.component(tfPlatform.binders().accessRequestProcessor()) //
				.expertSupplier(deployables::gcpServiceProcessor);

		bindings.bind(GcpConnector.T) //
				.component(GcpStorageConnector.class) //
				.expertFactory(deployables::connector);

		bindings.bind(GcpStorageBinaryProcessor.T) //
				.component(tfPlatform.binders().binaryPersistenceProcessor()) //
				.expertFactory(deployables::binaryProcessor) //
				.component(tfPlatform.binders().binaryRetrievalProcessor()) //
				.expertFactory(deployables::binaryProcessor);

		bindings.bind(HealthCheckProcessor.T) //
				.component(tfPlatform.binders().checkProcessor()) //
				.expertSupplier(deployables::healthCheckProcessor);
	}
}
