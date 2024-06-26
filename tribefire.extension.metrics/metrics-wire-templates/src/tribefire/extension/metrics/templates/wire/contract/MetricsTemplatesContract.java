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
package tribefire.extension.metrics.templates.wire.contract;

import java.util.Set;

import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.metrics.model.deployment.connector.MetricsConnector;
import tribefire.extension.metrics.model.deployment.service.MetricsDemoProcessor;
import tribefire.extension.metrics.model.deployment.service.MetricsProcessor;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsCounterAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsInProgressAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsSummaryAspect;
import tribefire.extension.metrics.model.deployment.service.aspect.MetricsTimerAspect;
import tribefire.extension.metrics.templates.api.MetricsTemplateContext;

public interface MetricsTemplatesContract extends WireSpace {

	/**
	 * Setup METRICS with a specified {@link MetricsTemplateContext}
	 */
	void setupMetrics(MetricsTemplateContext context);

	MetricsProcessor metricsServiceProcessor(MetricsTemplateContext context);

	MetricsDemoProcessor metricsDemoProcessor(MetricsTemplateContext context);

	Set<MetricsConnector> metricsConnectors(MetricsTemplateContext context);

	MetricsCounterAspect metricsCounterAspect(MetricsTemplateContext context);

	MetricsTimerAspect metricsTimerAspect(MetricsTemplateContext context);

	MetricsSummaryAspect metricsSummaryAspect(MetricsTemplateContext context);

	MetricsInProgressAspect metricsInProgressAspect(MetricsTemplateContext context);
}
