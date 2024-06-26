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
package tribefire.extension.metrics.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.metrics.model.deployment.connector.InMemoryMetricsConnector;
import tribefire.extension.metrics.model.deployment.connector.MetricsConnector;
import tribefire.extension.metrics.model.deployment.connector.NewRelicMetricsConnector;
import tribefire.extension.metrics.model.deployment.connector.PrometheusMetricsConnector;
import tribefire.extension.metrics.model.deployment.health.HealthCheckProcessor;
import tribefire.extension.metrics.model.deployment.service.MetricsDemoProcessor;
import tribefire.extension.metrics.model.deployment.service.MetricsProcessor;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsCounterAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsInProgressAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsSummaryAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsTimerAspect;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class MetricsModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private DeployablesSpace deployables;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		//@formatter:off
		//----------------------------
		// CONNECTOR
		//----------------------------
//		bindings.bind(PrometheusMetricsConnector.T)
//			.component(MetricsConnector.T, tribefire.extension.metrics.connector.api.MetricsConnector.class)
//			.expertFactory(deployables::prometheusMetricsConnector);
//		bindings.bind(PrometheusMetricsScrapingEndpoint.T)
//			.component(tfPlatform.binders().webTerminal())
//			.expertFactory(deployables::prometheusMetricsScrapingEndpoint);
		
		bindings.bind(PrometheusMetricsConnector.T)
			.component(tfPlatform.binders().webTerminal())
			.expertFactory(deployables::prometheusMetricsScrapingEndpoint)
			.component(MetricsConnector.T, tribefire.extension.metrics.connector.api.MetricsConnector.class)
			.expertFactory(deployables::prometheusMetricsConnector);
		
		
		bindings.bind(InMemoryMetricsConnector.T)
			.component(MetricsConnector.T, tribefire.extension.metrics.connector.api.MetricsConnector.class)
			.expertFactory(deployables::inMemoryMetricsConnector);		

		bindings.bind(NewRelicMetricsConnector.T)
			.component(MetricsConnector.T, tribefire.extension.metrics.connector.api.MetricsConnector.class)
			.expertFactory(deployables::newRelicMetricsConnector);		
		
		
		
		//----------------------------
		// PROCESSOR
		//----------------------------
		bindings.bind(MetricsProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::metricsProcessor);
		
		bindings.bind(MetricsDemoProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::metricsDemoProcessor);		
		//----------------------------
		// ASPECT
		//----------------------------
		bindings.bind(MetricsCounterAspect.T)
			.component(tfPlatform.binders().serviceAroundProcessor())
			.expertFactory(deployables::metricsCounterAspect);
		
		bindings.bind(MetricsTimerAspect.T)
			.component(tfPlatform.binders().serviceAroundProcessor())
			.expertFactory(deployables::metricsTimerAspect);

		bindings.bind(MetricsSummaryAspect.T)
			.component(tfPlatform.binders().serviceAroundProcessor())
			.expertFactory(deployables::metricsSummaryAspect);
		
		bindings.bind(MetricsInProgressAspect.T)
			.component(tfPlatform.binders().serviceAroundProcessor())
			.expertFactory(deployables::metricsInProgressAspect);
		
		
		//----------------------------
		// HEALTH
		//----------------------------
		bindings.bind(HealthCheckProcessor.T)
			.component(tfPlatform.binders().checkProcessor())
			.expertFactory(deployables::healthCheckProcessor);	
		
		//@formatter:on
	}
}
