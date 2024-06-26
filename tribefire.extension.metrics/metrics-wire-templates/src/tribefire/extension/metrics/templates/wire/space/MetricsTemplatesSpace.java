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
package tribefire.extension.metrics.templates.wire.space;

import java.util.HashSet;
import java.util.Set;

import com.braintribe.logging.Logger;
import com.braintribe.utils.StringTools;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.scope.InstanceConfiguration;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.metrics.model.deployment.connector.InMemoryMetricsConnector;
import tribefire.extension.metrics.model.deployment.connector.MetricsConnector;
import tribefire.extension.metrics.model.deployment.connector.NewRelicMetricsConnector;
import tribefire.extension.metrics.model.deployment.connector.PrometheusMetricsConnector;
import tribefire.extension.metrics.model.deployment.service.MetricsDemoProcessor;
import tribefire.extension.metrics.model.deployment.service.MetricsProcessor;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsCounterAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsInProgressAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsSummaryAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsTimerAspect;
import tribefire.extension.metrics.templates.api.MetricsTemplateContext;
import tribefire.extension.metrics.templates.api.connector.MetricsTemplateConnectorContext;
import tribefire.extension.metrics.templates.api.connector.MetricsTemplateInMemoryConnectorContext;
import tribefire.extension.metrics.templates.api.connector.MetricsTemplateNewRelicConnectorContext;
import tribefire.extension.metrics.templates.api.connector.MetricsTemplatePrometheusConnectorContext;
import tribefire.extension.metrics.templates.util.MetricsTemplateUtil;
import tribefire.extension.metrics.templates.wire.contract.BasicInstancesContract;
import tribefire.extension.metrics.templates.wire.contract.MetricsTemplatesContract;

/**
 *
 */
@Managed
public class MetricsTemplatesSpace implements WireSpace, MetricsTemplatesContract {

	private static final Logger logger = Logger.getLogger(MetricsTemplatesSpace.class);

	@Import
	private BasicInstancesContract basicInstances;

	@Import
	private MetricsMetaDataSpace metricsMetaData;

	@Override
	public void setupMetrics(MetricsTemplateContext context) {
		if (context == null) {
			throw new IllegalArgumentException("The MetricsTemplateContext must not be null.");
		}
		logger.debug(() -> "Configuring METRICS based on:\n" + StringTools.asciiBoxMessage(context.toString(), -1));

		if (context.getAddDemo()) {
			metricsDemoProcessor(context);
		}

		// processing
		metricsServiceProcessor(context);

		// aspect
		metricsCounterAspect(context);

		// metadata
		metricsMetaData.metaData(context);
	}

	// -----------------------------------------------------------------------
	// PROCESSOR
	// -----------------------------------------------------------------------

	@Override
	@Managed
	public MetricsProcessor metricsServiceProcessor(MetricsTemplateContext context) {
		MetricsProcessor bean = context.create(MetricsProcessor.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Service Processor", context));

		return bean;
	}

	@Override
	@Managed
	public MetricsDemoProcessor metricsDemoProcessor(MetricsTemplateContext context) {

		MetricsDemoProcessor bean = context.create(MetricsDemoProcessor.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Demo Processor", context));

		return bean;
	}

	// -----------------------------------------------------------------------
	// CONNECTOR
	// -----------------------------------------------------------------------

	@Override
	public Set<MetricsConnector> metricsConnectors(MetricsTemplateContext context) {
		Set<MetricsConnector> set = new HashSet<>();

		Set<MetricsTemplateConnectorContext> connectorContexts = context.getConnectorContexts();
		connectorContexts.forEach(connectorContext -> {

			MetricsConnector bean = null;
			if (connectorContext instanceof MetricsTemplateInMemoryConnectorContext) {
				bean = inMemoryMetricsConnector(context);
			} else if (connectorContext instanceof MetricsTemplatePrometheusConnectorContext) {
				bean = prometheusMetricsConnector(context);
			} else if (connectorContext instanceof MetricsTemplateNewRelicConnectorContext) {
				bean = newRelicMetricsConnector(context);
			} else {
				throw new IllegalArgumentException("Metrics connectorContext: '" + connectorContext + "' not supported or is not set");
			}

			set.add(bean);
		});

		return set;
	}

	@Managed
	public InMemoryMetricsConnector inMemoryMetricsConnector(MetricsTemplateContext context) {
		InMemoryMetricsConnector bean = context.create(InMemoryMetricsConnector.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Connector (InMemory)", context));

		return bean;
	}

	@Managed
	public PrometheusMetricsConnector prometheusMetricsConnector(MetricsTemplateContext context) {
		PrometheusMetricsConnector bean = context.create(PrometheusMetricsConnector.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Connector (Prometheus)", context));

		bean.setPathIdentifier("prometheus");

		// bean.setScrapingEndpoint(prometheusMetricsScrapingEndpoint(context));

		return bean;
	}

	@Managed
	public NewRelicMetricsConnector newRelicMetricsConnector(MetricsTemplateContext context) {
		NewRelicMetricsConnector bean = context.create(NewRelicMetricsConnector.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Connector (New Relic)", context));

		return bean;
	}

	// @Managed
	// public PrometheusMetricsScrapingEndpoint prometheusMetricsScrapingEndpoint(MetricsTemplateContext context) {
	// PrometheusMetricsScrapingEndpoint bean = context.create(PrometheusMetricsScrapingEndpoint.T,
	// InstanceConfiguration.currentInstance());
	// bean.setModule(context.getMetricsModule());
	// bean.setAutoDeploy(true);
	// bean.setPathIdentifier("prometheus");
	//
	// bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Connector (Prometheus) Scaping
	// Endpoint", context));
	//
	// return bean;
	// }

	// -----------------------------------------------------------------------
	// ASPECT
	// -----------------------------------------------------------------------

	@Override
	@Managed
	public MetricsCounterAspect metricsCounterAspect(MetricsTemplateContext context) {
		MetricsCounterAspect bean = context.create(MetricsCounterAspect.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Counter Aspect", context));

		bean.setMetricsConnectors(metricsConnectors(context));

		return bean;
	}

	@Override
	@Managed
	public MetricsTimerAspect metricsTimerAspect(MetricsTemplateContext context) {
		MetricsTimerAspect bean = context.create(MetricsTimerAspect.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Timer Aspect", context));

		bean.setMetricsConnectors(metricsConnectors(context));

		return bean;
	}

	@Override
	@Managed
	public MetricsSummaryAspect metricsSummaryAspect(MetricsTemplateContext context) {
		MetricsSummaryAspect bean = context.create(MetricsSummaryAspect.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS Summary Aspect", context));

		bean.setMetricsConnectors(metricsConnectors(context));

		return bean;
	}

	@Override
	@Managed
	public MetricsInProgressAspect metricsInProgressAspect(MetricsTemplateContext context) {
		MetricsInProgressAspect bean = context.create(MetricsInProgressAspect.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getMetricsModule());
		bean.setAutoDeploy(true);

		bean.setName(MetricsTemplateUtil.resolveContextBasedDeployableName("METRICS InProgress Aspect", context));

		bean.setMetricsConnectors(metricsConnectors(context));

		return bean;
	}

}
