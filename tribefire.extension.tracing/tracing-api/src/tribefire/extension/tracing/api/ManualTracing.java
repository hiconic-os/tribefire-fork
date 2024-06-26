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
package tribefire.extension.tracing.api;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.service.api.ServiceRequestContextBuilder;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapGetter;
import io.opentelemetry.context.propagation.TextMapSetter;
import tribefire.extension.tracing.connector.api.TracingConnector;
import tribefire.extension.tracing.service.TracingInformation;
import tribefire.extension.tracing.service.TracingServiceConstants;

public class ManualTracing<T> implements TracingServiceConstants {

	private TracingConnector tracingConnector;

	private String operationName = "manual";

	// -----------------------------------------------------------------------
	// METHODS
	// -----------------------------------------------------------------------

	public void withTracing(Runnable runnable, ServiceRequestContext requestContext) {
		Objects.requireNonNull(tracingConnector, "TracingConnector needs to be set");

		long start = System.currentTimeMillis();

		Tracer tracer = tracingConnector.tracer();

		ServiceRequestContextBuilder serviceRequestContextBuilder = requestContext.derive();
		Map<String, String> _map = new HashMap<>();
		Optional<Map<String, String>> tracingInformation = requestContext.getAspect(TracingInformation.class);
		if (tracingInformation.isPresent()) {
			_map = tracingInformation.get();
		}

		Map<String, String> map = new HashMap<>(_map);
		
		OpenTelemetry openTelemetry = GlobalOpenTelemetry.get();
		
		Context context = openTelemetry.getPropagators()
		.getTextMapPropagator()
		.extract(Context.current(), map, new TextMapGetter<>() {

			@Override
			public Iterable<String> keys(Map<String, String> carrier) {
				return carrier.keySet();
			}

			@Override
			public String get(Map<String, String> carrier, String key) {
				return carrier.get(key);
			}
		});
		
		SpanBuilder spanBuilder = tracer.spanBuilder(operationName);
		Span span = null;

		// If it's a root context, then the traceId contains zeros only, ie not valid -> it's a parent
		boolean isValid = Span.fromContext(context).getSpanContext().isValid();

		if (!isValid) {
			// let's start the parent span
			
			spanBuilder.setNoParent();
			tracingConnector.addAttributesBeforeExecution(spanBuilder, requestContext, ATTRIBUTE_VALUE_TYPE_PARENT_MANUAL);

			span = spanBuilder.startSpan();
			
			openTelemetry.getPropagators()
			.getTextMapPropagator()
			.inject(Context.current(), map, new TextMapSetter<>() {

				@Override
				public void set(Map<String, String> carrier, String key, String value) {
					carrier.put(key, value);
				}
				
			});

		} else {
			tracingConnector.addAttributesBeforeExecution(spanBuilder, requestContext, ATTRIBUTE_VALUE_TYPE_CHILD_MANUAL);
			span = spanBuilder.startSpan();
		}

		serviceRequestContextBuilder.set(TracingInformation.class, map);

		requestContext = serviceRequestContextBuilder.build();

		long startService = Long.MIN_VALUE;
		try {
			runnable.run();
		} catch (Throwable t) {
			tracingConnector.addAttributesErrorExecution(span, t);
			// nothing time consuming here!
			throw t;
		} finally {
			long end = System.currentTimeMillis();

			long serviceDuration = end - startService;
			long tracingOverhead = startService - start;

			tracingConnector.addAttributesAfterExecution(span, serviceDuration, tracingOverhead);
			if (span != null) {
				span.end();
			}
		}
	}

	public T withTracing(@SuppressWarnings("unused") Supplier<T> s, @SuppressWarnings("unused") ServiceRequestContext requestContext) {
		Objects.requireNonNull(tracingConnector, "TracingConnector needs to be set");
		// TODO: not implemented yet
		return null;
	}

	// -----------------------------------------------------------------------
	// GETTER & SETTER
	// -----------------------------------------------------------------------

	@Configurable
	@Required
	public void setTracingConnector(TracingConnector tracingConnector) {
		this.tracingConnector = tracingConnector;
	}
}
