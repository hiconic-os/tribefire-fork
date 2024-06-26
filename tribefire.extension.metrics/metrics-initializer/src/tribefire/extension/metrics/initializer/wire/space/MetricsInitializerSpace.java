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
package tribefire.extension.metrics.initializer.wire.space;

import java.util.HashSet;
import java.util.Set;

import com.braintribe.model.extensiondeployment.check.CheckBundle;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.cortex.model.check.CheckCoverage;
import tribefire.cortex.model.check.CheckWeight;
import tribefire.extension.metrics.initializer.wire.contract.DemoMetricsConnector;
import tribefire.extension.metrics.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.metrics.initializer.wire.contract.MetricsInitializerContract;
import tribefire.extension.metrics.model.deployment.health.HealthCheckProcessor;
import tribefire.extension.metrics.templates.api.MetricsTemplateContext;
import tribefire.extension.metrics.templates.api.connector.MetricsTemplateConnectorContext;
import tribefire.extension.metrics.templates.api.connector.MetricsTemplateInMemoryConnectorContext;
import tribefire.extension.metrics.templates.api.connector.MetricsTemplateNewRelicConnectorContext;
import tribefire.extension.metrics.templates.api.connector.MetricsTemplatePrometheusConnectorContext;
import tribefire.extension.metrics.templates.wire.contract.MetricsTemplatesContract;

@Managed
public class MetricsInitializerSpace extends AbstractInitializerSpace implements MetricsInitializerContract {

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Import
	private MetricsTemplatesContract metricsTemplates;

	@Override
	public void setupDefaultConfiguration(DemoMetricsConnector demoMetricsConnect) {
		MetricsTemplateContext metricsTemplateContext = demoMetricsTemplateContext(demoMetricsConnect);
		metricsTemplates.setupMetrics(metricsTemplateContext);
	}

	@Managed
	public MetricsTemplateContext demoMetricsTemplateContext(DemoMetricsConnector demoMetricsConnector) {

		Set<MetricsTemplateConnectorContext> connectorContexts = new HashSet<>();

		MetricsTemplateConnectorContext connectorContext;
		if (demoMetricsConnector != null) {
			switch (demoMetricsConnector) {
				case PROMETHEUS:
					connectorContext = demoPrometheusMetricsTemplateConnectorContext();
					break;
				case IN_MEMORY:
					connectorContext = demoInMemoryMetricsTemplateConnectorContext();
					break;
				case NEW_RELIC:
					connectorContext = demoNewRelicMetricsTemplateConnectorContext();
					break;
				default:
					throw new IllegalArgumentException(DemoMetricsConnector.class.getSimpleName() + ": '" + demoMetricsConnector + "' not supported");
			}
		} else {
			connectorContext = defaultMetricsTemplateConnectorContext();
		}

		connectorContexts.add(connectorContext);

		boolean addDemo = true;

		//@formatter:off
		MetricsTemplateContext bean = MetricsTemplateContext.builder()
				.setAddDemo(addDemo)
				.setContext("default")
				.setEntityFactory(super::create)
				.setMetricsModule(existingInstances.module())
				.setConnectorContexts(connectorContexts)
				.setLookupFunction(super::lookup)
				.setLookupExternalIdFunction(super::lookupExternalId)
			.build();
		//@formatter:on

		return bean;
	}

	// -----------------------------------------------------------------------
	// DEMO
	// -----------------------------------------------------------------------

	private MetricsTemplateConnectorContext demoInMemoryMetricsTemplateConnectorContext() {
		//@formatter:off
		MetricsTemplateConnectorContext context = MetricsTemplateInMemoryConnectorContext.builder()
				.build();
		//@formatter:on
		return context;
	}

	private MetricsTemplateConnectorContext demoPrometheusMetricsTemplateConnectorContext() {
		//@formatter:off
		MetricsTemplateConnectorContext context = MetricsTemplatePrometheusConnectorContext.builder()
				.build();
		//@formatter:on
		return context;
	}

	private MetricsTemplateConnectorContext demoNewRelicMetricsTemplateConnectorContext() {
		//@formatter:off
		MetricsTemplateConnectorContext context = MetricsTemplateNewRelicConnectorContext.builder()
				.build();
		//@formatter:on
		return context;
	}

	private MetricsTemplateConnectorContext defaultMetricsTemplateConnectorContext() {
		return demoInMemoryMetricsTemplateConnectorContext();
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
		bean.setName("METRICS Checks");
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
		bean.setName("METRICS Health Check");
		bean.setExternalId("metrics.healthzProcessor");
		return bean;
	}

}
