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
package tribefire.extension.metrics.service.aspect;

import com.braintribe.cfg.Configurable;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.service.api.ProceedContext;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.utils.lcd.CommonTools;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Counter.Builder;
import tribefire.extension.metrics.connector.api.MetricsConnector;

public class MetricsCounterAspect extends MetricsAspect {

	private final static Logger logger = Logger.getLogger(MetricsCounterAspect.class);

	private String baseUnit;

	@Override
	public Object process(ServiceRequestContext requestContext, ServiceRequest request, ProceedContext proceedContext) {
		boolean metricsActive = true;

		if (metricsActive) {
			logger.trace(() -> "Metrics enabled for request: '" + request + "'");

			Object result = null;
			try {
				result = proceedContext.proceed(request);

				metricsConnectors.forEach(metricsConnector -> {
					Counter counter = fetchCounter(metricsConnector, request, tagsSuccess);

					counter.increment();
				});

				return result;
			} catch (Throwable t) {
				metricsConnectors.forEach(metricsConnector -> {
					Counter counter = fetchCounter(metricsConnector, request, tagsError);

					counter.increment();
				});
				throw t;
			}
		} else {
			logger.trace(() -> "Metrics disabled for request: '" + request + "'");
			Object result = proceedContext.proceed(request);
			return result;
		}
	}

	// -----------------------------------------------------------------------
	// HELPERS
	// -----------------------------------------------------------------------

	private Counter fetchCounter(MetricsConnector metricsConnector, ServiceRequest request, String[] tags) {
		//@formatter:off
		Builder builder = Counter
				.builder(name)
				.tags(enrichTags(request, tags));
		//@formatter:on

		if (!CommonTools.isEmpty(description)) {
			builder.description(description);
		}
		if (!CommonTools.isEmpty(baseUnit)) {
			builder.baseUnit(baseUnit);
		}
		Counter counter = builder.register(metricsConnector.registry());
		return counter;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

}
