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
package tribefire.extension.antivirus.wire.space;

import java.util.List;

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.antivirus.model.deployment.repository.configuration.ProviderSpecification;
import tribefire.extension.antivirus.service.AntivirusProcessor;
import tribefire.extension.antivirus.service.HealthCheckProcessor;
import tribefire.module.wire.contract.PlatformReflectionContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;
import tribefire.module.wire.contract.WebPlatformResourcesContract;

/**
 *
 */
@Managed
public class DeployablesSpace implements WireSpace {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private WebPlatformResourcesContract resources;

	@Import
	private PlatformReflectionContract platformReflection;

	// -----------------------------------------------------------------------
	// PROCESSOR
	// -----------------------------------------------------------------------

	@Managed
	public AntivirusProcessor antivirusProcessor(ExpertContext<tribefire.extension.antivirus.model.deployment.service.AntivirusProcessor> context) {
		AntivirusProcessor bean = new AntivirusProcessor();

		tribefire.extension.antivirus.model.deployment.service.AntivirusProcessor deployable = context.getDeployable();

		List<ProviderSpecification> providerSpecifications = deployable.getProviderSpecifications();
		bean.setProviderSpecifications(providerSpecifications);
		return bean;
	}

	// -----------------------------------------------------------------------
	// PROCESSOR
	// -----------------------------------------------------------------------

	@Managed
	public HealthCheckProcessor healthCheckProcessor(
			ExpertContext<tribefire.extension.antivirus.model.deployment.service.HealthCheckProcessor> context) {

		tribefire.extension.antivirus.model.deployment.service.HealthCheckProcessor deployable = context.getDeployable();

		HealthCheckProcessor bean = new HealthCheckProcessor();
		bean.setCortexSessionSupplier(tfPlatform.systemUserRelated().cortexSessionSupplier());
		bean.setProviderSpecificationType(deployable.getProviderSpecificationType());
		bean.setProviderModuleSpecificationMethod(deployable.getProviderModuleSpecificationMethod());
		bean.setProviderActivatedMethod(deployable.getProviderActivatedMethod());
		return bean;
	}

}
