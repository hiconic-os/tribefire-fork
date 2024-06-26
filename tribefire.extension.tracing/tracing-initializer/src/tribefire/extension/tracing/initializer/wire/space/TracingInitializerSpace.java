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
package tribefire.extension.tracing.initializer.wire.space;

import static com.braintribe.utils.lcd.CollectionTools2.asSet;

import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.model.logging.LogLevel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.cortex.model.check.CheckCoverage;
import tribefire.cortex.model.check.CheckWeight;
import tribefire.extension.tracing.initializer.wire.contract.DemoTracingConnector;
import tribefire.extension.tracing.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.tracing.initializer.wire.contract.TracingInitializerContract;
import tribefire.extension.tracing.model.deployment.service.DefaultAttribute;
import tribefire.extension.tracing.model.deployment.service.HealthCheckProcessor;
import tribefire.extension.tracing.templates.api.TracingTemplateContext;
import tribefire.extension.tracing.templates.api.connector.TracingTemplateConnectorContext;
import tribefire.extension.tracing.templates.api.connector.TracingTemplateInMemoryJaegerConnectorContext;
import tribefire.extension.tracing.templates.api.connector.TracingTemplateJaegerConnectorContext;
import tribefire.extension.tracing.templates.api.connector.TracingTemplateLoggingConnectorContext;
import tribefire.extension.tracing.templates.api.connector.reporterconfiguration.TracingTemplateReporterConfigurationContext;
import tribefire.extension.tracing.templates.api.connector.samplerconfiguration.TracingTemplateConstantSamplerContext;
import tribefire.extension.tracing.templates.api.connector.samplerconfiguration.TracingTemplateSamplerConfigurationContext;
import tribefire.extension.tracing.templates.api.connector.senderconfiguration.TracingTemplateHttpSenderConfigurationContext;
import tribefire.extension.tracing.templates.wire.contract.TracingTemplatesContract;

@Managed
public class TracingInitializerSpace extends AbstractInitializerSpace implements TracingInitializerContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private TracingTemplatesContract tracingTemplates;

	@Override
	public void setupDefaultConfiguration(DemoTracingConnector demoTracingConnector) {
		tracingTemplates.setupTracing(defaultTracingTemplateContext(demoTracingConnector));
	}

	private TracingTemplateConnectorContext defaultTracingTemplateConnectorContext() {
		//@formatter:off
		TracingTemplateConnectorContext context = TracingTemplateLoggingConnectorContext.builder()
				.setTenant("default")
				.setDefaultAttributes(asSet(
						// BEFORE execution
						DefaultAttribute.ATTRIBUTE_TYPE,
						DefaultAttribute.ATTRIBUTE_ENTITY_TYPE,
						DefaultAttribute.ATTRIBUTE_REQUEST,
						DefaultAttribute.ATTRIBUTE_DOMAIN_ID,
						DefaultAttribute.ATTRIBUTE_USER,
						DefaultAttribute.ATTRIBUTE_ROLES,
						DefaultAttribute.ATTRIBUTE_PARTITION,
						DefaultAttribute.ATTRIBUTE_INSTANCE_ID,
						DefaultAttribute.ATTRIBUTE_NODE_ID,
						DefaultAttribute.ATTRIBUTE_APPLICATION_ID,
						DefaultAttribute.ATTRIBUTE_HOST_ADDRESS_IPV4,
						DefaultAttribute.ATTRIBUTE_HOST_ADDRESS_IPV6,
						DefaultAttribute.ATTRIBUTE_TIMESTAMP,
						DefaultAttribute.ATTRIBUTE_TIMESTAMP_ISO8601,

						// ERROR execution
						DefaultAttribute.ATTRIBUTE_STACK,
						DefaultAttribute.ATTRIBUTE_ERROR,

						// AFTER execution
						DefaultAttribute.ATTRIBUTE_RESULT,
						DefaultAttribute.ATTRIBUTE_SERVICE_DURATION,
						DefaultAttribute.ATTRIBUTE_TRACING_OVERHEAD,
						DefaultAttribute.ATTRIBUTE_NOTIFICATION_MESSAGE,
						DefaultAttribute.ATTRIBUTE_NOTIFICATION_DETAIL_MESSAGE
					))
				.build();
		//@formatter:on
		return context;
	}
	private TracingTemplateConnectorContext loggingTracingTemplateConnectorContext() {
		//@formatter:off
		TracingTemplateConnectorContext context = TracingTemplateLoggingConnectorContext.builder()
				.setLogLevel(LogLevel.INFO)
				.setLogAttributes(true)
				.setTenant("default")
				.build();
		//@formatter:on
		return context;
	}
	private TracingTemplateConnectorContext jaegerTracingTemplateConnectorContext() {
		//@formatter:off
		TracingTemplateHttpSenderConfigurationContext senderConfigurationContext = TracingTemplateHttpSenderConfigurationContext.builder()
				.setEndpoint("http://localhost:4317")
				.build();
		//@formatter:on

		//@formatter:off
		TracingTemplateReporterConfigurationContext reporterConfigurationContext = TracingTemplateReporterConfigurationContext.builder()
				.setFlushInterval(1000)
				.setMaxQueueSize(10000)
				.setSenderConfigurationContext(senderConfigurationContext)
				.build();
		//@formatter:on

		//@formatter:off
		TracingTemplateSamplerConfigurationContext samplerConfigurationContext = TracingTemplateConstantSamplerContext.builder()
				.build();
		//@formatter:on

		//@formatter:off
		TracingTemplateConnectorContext context = TracingTemplateJaegerConnectorContext.builder()
				.setReporterConfigurationContext(reporterConfigurationContext)
				.setSamplerConfigurationContext(samplerConfigurationContext)
				.setTenant("default")
				.build();
		//@formatter:on
		return context;
	}
	private TracingTemplateConnectorContext inMemoryJaegerTracingTemplateConnectorContext() {
		//@formatter:off
		TracingTemplateConnectorContext context = TracingTemplateInMemoryJaegerConnectorContext.builder()
				.setTenant("default")
			.build();
		//@formatter:on
		return context;
	}

	@Managed
	public TracingTemplateContext defaultTracingTemplateContext(DemoTracingConnector demoTracingConnector) {

		TracingTemplateConnectorContext connectorContext;
		if (demoTracingConnector != null) {
			switch (demoTracingConnector) {
				case LOGGING:
					connectorContext = loggingTracingTemplateConnectorContext();
					break;
				case JAEGER_IN_MEMORY:
					connectorContext = inMemoryJaegerTracingTemplateConnectorContext();
					break;
				case JAEGER:
					connectorContext = jaegerTracingTemplateConnectorContext();
					break;
				default:
					throw new IllegalArgumentException(DemoTracingConnector.class.getSimpleName() + ": '" + demoTracingConnector + "' not supported");
			}
		} else {
			connectorContext = defaultTracingTemplateConnectorContext();
		}

		boolean addDemo = false;
		if (demoTracingConnector != null) {
			addDemo = true;
		}

		//@formatter:off
		TracingTemplateContext bean = TracingTemplateContext.builder()
				.setAddDemo(addDemo)
				.setContext("default")
				.setEntityFactory(super::create)
				.setTracingModule(existingInstances.module())
				.setConnectorContext(connectorContext)
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
		bean.setName("TRACING Checks");
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
		bean.setName("TRACING Health Check");
		bean.setExternalId("tracing.healthzProcessor");
		return bean;
	}

}
