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

import java.time.Duration;

import com.braintribe.logging.Logger;
import com.braintribe.model.processing.service.api.ProceedContext;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.utils.lcd.CommonTools;

import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.Timer.Builder;
import tribefire.extension.metrics.connector.api.MetricsConnector;

//TODO: add long task timer
public class MetricsTimerAspect extends MetricsAspect {

	private final static Logger logger = Logger.getLogger(MetricsTimerAspect.class);

	@Override
	public Object process(ServiceRequestContext requestContext, ServiceRequest request, ProceedContext proceedContext) {
		boolean metricsActive = true;

		if (metricsActive) {
			logger.trace(() -> "Metrics enabled for request: '" + request + "'");

			Object result = null;
			long start = System.currentTimeMillis();
			try {

				result = proceedContext.proceed(request);

				metricsConnectors.forEach(metricsConnector -> {
					Timer timer = fetchTimer(metricsConnector, request, tagsSuccess);

					timer.record(Duration.ofMillis(System.currentTimeMillis() - start));
				});

				return result;
			} catch (Throwable t) {
				metricsConnectors.forEach(metricsConnector -> {
					Timer timer = fetchTimer(metricsConnector, request, tagsError);

					timer.record(Duration.ofMillis(System.currentTimeMillis() - start));
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

	private Timer fetchTimer(MetricsConnector metricsConnector, ServiceRequest request, String[] tags) {
		//@formatter:off
		Builder builder = Timer
				.builder(name)
				.tags(enrichTags(request, tags));
		//@formatter:on

		if (!CommonTools.isEmpty(description)) {
			builder.description(description);
		}

		// TODO: many other functionalities like percentiles, sla,...

		Timer timer = builder.register(metricsConnector.registry());

		return timer;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

}
