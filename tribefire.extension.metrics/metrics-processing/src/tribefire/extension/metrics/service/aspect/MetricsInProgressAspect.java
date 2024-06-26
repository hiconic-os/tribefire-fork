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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.InitializationAware;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.service.api.ProceedContext;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.utils.lcd.CommonTools;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.Gauge.Builder;
import tribefire.extension.metrics.connector.api.MetricsConnector;

public class MetricsInProgressAspect extends MetricsAspect implements InitializationAware {

	private final static Logger logger = Logger.getLogger(MetricsInProgressAspect.class);

	private String baseUnit;

	private Map<String, AtomicInteger> map;

	// -----------------------------------------------------------------------
	// InitializationAware
	// -----------------------------------------------------------------------

	@Override
	public void postConstruct() {
		map = new HashMap<>();
	}

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	@Override
	public Object process(ServiceRequestContext requestContext, ServiceRequest request, ProceedContext proceedContext) {
		boolean metricsActive = true;

		if (metricsActive) {
			logger.trace(() -> "Metrics enabled for request: '" + request + "'");

			String typeSignature = request.entityType().getTypeSignature();

			// fetch actual gauge value per type signature
			AtomicInteger value = map.get(typeSignature);
			if (value == null) {
				synchronized (this) {
					value = map.get(typeSignature);
					if (value == null) {
						value = new AtomicInteger();
						map.put(typeSignature, value);
						for (MetricsConnector metricsConnector : metricsConnectors) {
							fetchGauge(metricsConnector, request, tagsSuccess, value);
						}
					}
				}
			}
			value.incrementAndGet();
			try {
				Object result = proceedContext.proceed(request);
				value.decrementAndGet();
				return result;
			} catch (Throwable t) {
				value.decrementAndGet();
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

	private void fetchGauge(MetricsConnector metricsConnector, ServiceRequest request, String[] tags, AtomicInteger value) {
		//@formatter:off
		Builder<Supplier<Number>> builder = Gauge
				.builder(name, () -> value)
			    .tags(enrichTags(request, tags));
		//@formatter:on

		if (!CommonTools.isEmpty(description)) {
			builder.description(description);
		}
		if (!CommonTools.isEmpty(baseUnit)) {
			builder.baseUnit(baseUnit);
		}
		builder.register(metricsConnector.registry());
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	public void setBaseUnit(String baseUnit) {
		this.baseUnit = baseUnit;
	}

}
