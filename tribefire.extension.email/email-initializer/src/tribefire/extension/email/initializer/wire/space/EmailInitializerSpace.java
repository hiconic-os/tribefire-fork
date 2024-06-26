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
package tribefire.extension.email.initializer.wire.space;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import com.braintribe.gm.model.reason.meta.HttpStatusCode;
import com.braintribe.gm.model.reason.meta.LogReason;
import com.braintribe.model.ddra.DdraMapping;
import com.braintribe.model.email.deployment.service.EmailServiceProcessor;
import com.braintribe.model.email.deployment.service.HealthCheckProcessor;
import com.braintribe.model.email.service.EmailServiceConstants;
import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.logging.LogLevel;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.model.processing.email.util.EmailConstants;
import com.braintribe.model.service.domain.ServiceDomain;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.cortex.model.check.CheckCoverage;
import tribefire.cortex.model.check.CheckWeight;
import tribefire.extension.email.initializer.DdraMappingsBuilder;
import tribefire.extension.email.initializer.wire.contract.EmailInitializerContract;
import tribefire.extension.email.initializer.wire.contract.EmailInitializerModelsContract;
import tribefire.extension.email.initializer.wire.contract.ExistingInstancesContract;

@Managed
public class EmailInitializerSpace extends AbstractInitializerSpace implements EmailInitializerContract, EmailConstants {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private EmailInitializerModelsContract models;

	@Override
	@Managed
	public ServiceDomain apiServiceDomain() {
		ServiceDomain bean = create(ServiceDomain.T);
		bean.setExternalId(EmailServiceConstants.SERVICE_DOMAIN);
		bean.setName("Email Services");
		bean.setServiceModel(models.configuredEmailApiModel());
		return bean;
	}

	@Override
	@Managed
	public Set<DdraMapping> ddraMappings() {
		//@formatter:off
			Set<DdraMapping> bean =
						new DdraMappingsBuilder(
							EmailServiceConstants.SERVICE_DOMAIN,
							this::lookup,
							this::create)
						.build();
			//@formatter:on
		return bean;
	}

	@Managed
	@Override
	public EmailServiceProcessor emailServiceProcessor() {
		EmailServiceProcessor bean = create(EmailServiceProcessor.T);
		bean.setModule(existingInstances.module());
		bean.setExternalId(EXTERNAL_ID_EMAIL_SERVICE_PROCESSOR);
		bean.setName("Email Service Processor");

		return bean;
	}

	@Managed
	@Override
	public MetaData processWithEmailServiceProcessor() {
		ProcessWith bean = create(ProcessWith.T);
		bean.setProcessor(emailServiceProcessor());
		return bean;
	}

	@Override
	@Managed
	public CheckBundle connectivityCheckBundle() {

		CheckBundle bean = create(CheckBundle.T);
		bean.setModule(existingInstances.module());
		bean.getChecks().add(healthCheckProcessor());
		bean.setName("Email Checks");
		bean.setWeight(CheckWeight.under10s);
		bean.setCoverage(CheckCoverage.connectivity);
		bean.setIsPlatformRelevant(false);

		return bean;
	}

	@Managed
	public HealthCheckProcessor healthCheckProcessor() {
		HealthCheckProcessor bean = create(HealthCheckProcessor.T);
		bean.setModule(existingInstances.module());
		bean.setName("Email Check Processor");
		bean.setExternalId(EXTERNAL_ID_EMAIL_HEALTHCHECK_PROCESSOR);
		return bean;
	}

	@Managed
	@Override
	public HttpStatusCode httpStatus500Md() {
		HttpStatusCode bean = create(HttpStatusCode.T);
		bean.setCode(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return bean;
	}
	@Managed
	@Override
	public HttpStatusCode httpStatus501Md() {
		HttpStatusCode bean = create(HttpStatusCode.T);
		bean.setCode(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
		return bean;
	}
	@Managed
	@Override
	public HttpStatusCode httpStatus502Md() {
		HttpStatusCode bean = create(HttpStatusCode.T);
		bean.setCode(HttpServletResponse.SC_BAD_GATEWAY);
		return bean;
	}
	@Managed
	@Override
	public HttpStatusCode httpStatus404Md() {
		HttpStatusCode bean = create(HttpStatusCode.T);
		bean.setCode(HttpServletResponse.SC_NOT_FOUND);
		return bean;
	}
	@Managed
	@Override
	public LogReason logReasonTrace() {
		LogReason bean = create(LogReason.T);
		bean.setLevel(LogLevel.TRACE);
		return bean;
	}

}
