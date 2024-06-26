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
package tribefire.extension.opentracing.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.braintribe.cfg.InitializationAware;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.service.api.ProceedContext;
import com.braintribe.model.processing.service.api.ServiceAroundProcessor;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;

import io.opentracing.Span;
import io.opentracing.Tracer;
import io.opentracing.propagation.TextMap;
import io.opentracing.util.GlobalTracer;

//TODO: check if jaeger-client is necessary - maybe jaeger-core is enough
public class OpentracingAspect implements ServiceAroundProcessor<ServiceRequest, Object>, InitializationAware {

	private final static Logger logger = Logger.getLogger(OpentracingAspect.class);

	private tribefire.extension.opentracing.model.deployment.service.OpentracingAspect deployable;

	private Tracer tracer;

	// -----------------------------------------------------------------------
	// InitializationAware
	// -----------------------------------------------------------------------

	@Override
	public void postConstruct() {

		// SamplerConfiguration samplerConfig = new SamplerConfiguration("const", 1);
		// ReporterConfiguration reporterConfig = new ReporterConfiguration(true, null, null, null, null);
		// Configuration config = new Configuration(service, samplerConfig, reporterConfig);
		//
		// // Get the actual OpenTracing-compatible Tracer.
		// Tracer tracer = config.getTracer();

		// jaeger
		// TODO: Dependency issue/build
//		//@formatter:off
//		SamplerConfiguration samplerConfig = new SamplerConfiguration()
//	                .withType(ConstSampler.TYPE)
//	                .withParam(1);
//	            SenderConfiguration senderConfig = new SenderConfiguration()
//	                .withAgentHost("localhost")
//	                .withAgentPort(5775);
////	                .withAgentPort(5778);
//	            ReporterConfiguration reporterConfig = new ReporterConfiguration()
//	                .withLogSpans(true)
//	                .withFlushInterval(1000)
//	                .withMaxQueueSize(10000)
//	                .withSender(senderConfig);
//        tracer = new Configuration("myservice").withSampler(samplerConfig).withReporter(reporterConfig).getTracer();
//        //@formatter:on

		// ZIPKIN
		//@formatter:off

//		OkHttpSender sender = OkHttpSender.create(
//	                "http://" +
//	                    config.getProperty("zipkin.reporter_host") + ":" +
//	                    config.getProperty("zipkin.reporter_port") + "/api/v1/spans");
//	            Reporter<Span> reporter = AsyncReporter.builder(sender).build();
//	            tracer = BraveTracer.create(Tracing.newBuilder()
//	                .localServiceName(componentName)
//	                .spanReporter(reporter)
//	                .build());
	   //@formatter:on

		boolean registerIfAbsent = GlobalTracer.registerIfAbsent(tracer);
		// GlobalTracer.register(tracer);
		logger.info(() -> "register: '" + registerIfAbsent + "'");

	}

	// -----------------------------------------------------------------------
	// AROUND ASPECT
	// -----------------------------------------------------------------------

	@Override
	public Object process(ServiceRequestContext requestContext, ServiceRequest request, ProceedContext proceedContext) {

		Map<String, Object> metaData = request.getMetaData();

		String typeName = request.entityType().getTypeName();

		// ---------------
		Span span = null;

		//@formatter:off
//		Tracer.SpanBuilder spanBuilder;
//
//		MapTextMap textMap = new MapTextMap();
//		textMap.put("foo", "bar");
//		SpanContext spanContext = tracer.extract(Format.Builtin.TEXT_MAP, textMap);
//		if (spanContext == null) {
//			spanBuilder = tracer.buildSpan(typeName);
//			span = spanBuilder.start();
//
//		} else {
//			spanBuilder = tracer.buildSpan(typeName).asChildOf(spanContext);
//
//			Iterator<Map.Entry<String, String>> iterator = spanContext.baggageItems().iterator();
//			Map.Entry<String, String> entry = iterator.next();
//		}
		//@formatter:on
		// ---------------

		span = GlobalTracer.get().buildSpan("aaaaa").start();

		// #####################

		try {
			logger.info(() -> "Dummy Enable Opentracing.... - BEFORE");
			Object result = proceedContext.proceed(request);
			logger.info(() -> "Dummy Enable Opentracing.... - AFTER");

			return result;
		} catch (Throwable t) {
			throw t;
		} finally {
			if (span != null) {
				span.finish();
			}
		}
	}

	// -----------------------------------------------------------------------
	// HELPER CLASS
	// -----------------------------------------------------------------------

	private static class MapTextMap implements TextMap {

		final Map<String, String> map = new HashMap<String, String>();

		@Override
		public Iterator<Map.Entry<String, String>> iterator() {
			return map.entrySet().iterator();
		}

		@Override
		public void put(String key, String value) {
			map.put(key, value);
		}
	}
}
