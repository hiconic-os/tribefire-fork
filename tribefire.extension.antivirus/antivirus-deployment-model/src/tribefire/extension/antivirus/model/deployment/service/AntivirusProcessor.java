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
package tribefire.extension.antivirus.model.deployment.service;

import java.util.List;

import com.braintribe.model.extensiondeployment.access.AccessRequestProcessor;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.antivirus.model.deployment.repository.configuration.ProviderSpecification;

/**
 * Antivirus Processor
 * 
 *
 */
public interface AntivirusProcessor extends AccessRequestProcessor {

	final EntityType<AntivirusProcessor> T = EntityTypes.T(AntivirusProcessor.class);

	String providerSpecifications = "providerSpecifications";

	@Name("Provider Specifications")
	@Description("Optional list of Provider Specifications. If not specified the Provider Specification(s) from the requests will be used")
	List<ProviderSpecification> getProviderSpecifications();
	void setProviderSpecifications(List<ProviderSpecification> providerSpecifications);

	String getAntivirusContext();
	void setAntivirusContext(String antivirusContext);
}
