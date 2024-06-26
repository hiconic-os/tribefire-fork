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
package tribefire.extension.antivirus.initializer.wire.space;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.logging.Logger;
import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.utils.CommonTools;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.cortex.model.check.CheckCoverage;
import tribefire.cortex.model.check.CheckWeight;
import tribefire.extension.antivirus.initializer.wire.contract.AntivirusInitializerContract;
import tribefire.extension.antivirus.initializer.wire.contract.DefaultAntivirusProvider;
import tribefire.extension.antivirus.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.antivirus.initializer.wire.contract.RuntimePropertiesContract;
import tribefire.extension.antivirus.model.deployment.repository.configuration.ClamAVSpecification;
import tribefire.extension.antivirus.model.deployment.repository.configuration.CloudmersiveSpecification;
import tribefire.extension.antivirus.model.deployment.repository.configuration.ProviderSpecification;
import tribefire.extension.antivirus.model.deployment.repository.configuration.VirusTotalSpecification;
import tribefire.extension.antivirus.model.deployment.service.HealthCheckProcessor;
import tribefire.extension.antivirus.templates.api.AntivirusTemplateContext;
import tribefire.extension.antivirus.templates.api.AntivirusTemplateContextBuilder;
import tribefire.extension.antivirus.templates.wire.contract.AntivirusTemplatesContract;

@Managed
public class AntivirusInitializerSpace extends AbstractInitializerSpace implements AntivirusInitializerContract {

	private static final Logger logger = Logger.getLogger(AntivirusInitializerSpace.class);

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private AntivirusTemplatesContract antivirusTemplates;

	@Import
	private RuntimePropertiesContract properties;

	@Override
	public void setupDefaultConfiguration(DefaultAntivirusProvider defaultAntivirusProvider) {
		antivirusTemplates.setupAntivirus(defaultAntivirusTemplateContext(defaultAntivirusProvider));
	}

	@Override
	@Managed
	public ClamAVSpecification clamAVSpecification() {
		String host = properties.ANTIVIRUS_CLAMAV_URL();
		if (!CommonTools.isEmpty(host)) {
			ClamAVSpecification bean = create(ClamAVSpecification.T);
			bean.setURL(host);
			bean.setPort(properties.ANTIVIRUS_CLAMAV_PORT());
			bean.setTimeout(properties.ANTIVIRUS_CLAMAV_TIMEOUT());
			return bean;
		} else {
			return null;
		}
	}

	@Override
	@Managed
	public CloudmersiveSpecification cloumersiveSpecification() {
		String apiKey = properties.ANTIVIRUS_CLOUD_MERSIVE_API_KEY();
		if (apiKey != null) {
			CloudmersiveSpecification bean = create(CloudmersiveSpecification.T);
			bean.setApiKey(apiKey);
			return bean;
		} else {
			return null;
		}
	}

	@Override
	@Managed
	public VirusTotalSpecification virusTotalSpecification() {
		String apiKey = properties.ANTIVIRUS_VIRUSTOTAL_API_KEY();
		if (apiKey != null) {
			VirusTotalSpecification bean = create(VirusTotalSpecification.T);
			bean.setApiKey(apiKey);
			bean.setPollIntervalInSec(properties.ANTIVIRUS_VIRUSTOTAL_POLL_INTERVAL());
			bean.setPollDurationInSec(properties.ANTIVIRUS_VIRUSTOTAL_POLL_DURATION());
			return bean;
		} else {
			return null;
		}
	}

	@Managed
	public AntivirusTemplateContext defaultAntivirusTemplateContext(DefaultAntivirusProvider defaultAntivirusProvider) {
		AntivirusTemplateContextBuilder builder = AntivirusTemplateContext.builder();

		List<ProviderSpecification> providerSpecifications = new ArrayList<>();
		switch (defaultAntivirusProvider) {
			case CLAMAV:
				ClamAVSpecification clamAVSpecification = clamAVSpecification();
				if (clamAVSpecification != null) {
					providerSpecifications.add(clamAVSpecification);
				}
				break;
			case CLOUDMERSIVE:
				CloudmersiveSpecification cloumersiveSpecification = cloumersiveSpecification();
				if (cloumersiveSpecification != null) {
					providerSpecifications.add(cloumersiveSpecification);
				}
				break;
			case VIRUS_TOTAL:
				VirusTotalSpecification virusTotalSpecification = virusTotalSpecification();
				if (virusTotalSpecification != null) {
					providerSpecifications.add(virusTotalSpecification);
				}
				break;
			default:
				throw new IllegalArgumentException(
						DefaultAntivirusProvider.class.getSimpleName() + ": '" + defaultAntivirusProvider + "' not supported");
		}

		builder.setProviderSpecifications(providerSpecifications);

		//@formatter:off
		AntivirusTemplateContext bean = builder
				.setContext("default")
				.setEntityFactory(super::create)
				.setAntivirusModule(existingInstances.module())
				.setLookupFunction(super::lookup)
				.setLookupExternalIdFunction(super::lookupExternalId)				
			.build();
		//@formatter:on

		return bean;
	}

	// -----------------------------------------------------------------------
	// HEALTH
	// -----------------------------------------------------------------------

	@Override
	@Managed
	public CheckBundle functionalCheckBundle() {
		CheckBundle bean = create(CheckBundle.T);
		bean.setModule(existingInstances.module());
		bean.getChecks().add(healthCheckProcessor());
		bean.setName("Antivirus Checks");
		bean.setWeight(CheckWeight.under1s);
		bean.setCoverage(CheckCoverage.connectivity);
		bean.setIsPlatformRelevant(false);

		return bean;
	}

	@Managed
	@Override
	public HealthCheckProcessor healthCheckProcessor() {
		HealthCheckProcessor bean = create(HealthCheckProcessor.T);
		bean.setModule(existingInstances.module());
		bean.setAutoDeploy(true);
		bean.setName("Antivirus Health Check");
		bean.setExternalId("antivirus.healthzProcessor");
		return bean;
	}
}
