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
package tribefire.extension.tracing.connector.api;

import java.util.Date;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.notification.Level;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.model.time.TimeSpan;

import io.opentelemetry.api.OpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanBuilder;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.sdk.testing.exporter.InMemorySpanExporter;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import tribefire.extension.tracing.model.deployment.service.DefaultAttribute;

/**
 *
 */
public interface TracingConnector {

	public CheckResultEntry health();

	// -----------------------------------------------------------------------
	// MISC
	// -----------------------------------------------------------------------

	public void initialize();

	boolean tracingEnabled();

	Date disableTracingAt();

	public OpenTelemetry openTelemetry();

	public Tracer tracer();
	
	public SdkTracerProvider tracerProvider();

	boolean tracingActive(ServiceRequest request, ServiceRequestContext requestContext);

	// -----------------------------------------------------------------------
	// CONFIGURE TRACING
	// -----------------------------------------------------------------------

	void enableTracing(TimeSpan enableDuration);

	void disableTracing();

	void changeComponentName(String _componentName);

	void changeAddAttributesFromNotificationsMessage(Level _addAttributesFromNotificationsMessage);

	void changeAddAttributesFromNotificationsDetailsMessage(Level addAttributesFromNotificationsDetailsMessage);

	void populateDefaultAttributes(Set<DefaultAttribute> _attributes);

	void populateCustomAttributes(Map<String, String> _customAttributesRegistry);

	void populateEntityTypeInclusions(Set<String> _entityTypeInclusions);

	void populateEntityTypeHierarchyInclusions(Set<String> _entityTypeHierarchyInclusions);

	void populateEntityTypeExclusions(Set<String> _entityTypeExclusions);

	void populateEntityTypeHierarchyExclusions(Set<String> _entityTypeHierarchyExclusions);

	void populateUserInclusions(Set<String> _userInclusions);

	void populateUserExclusions(Set<String> _userExclusions);

	// -----------------------------------------------------------------------
	// ATTACHING ATTRIBUTES
	// -----------------------------------------------------------------------

	void addAttributesBeforeExecution(SpanBuilder spanBuilder, ServiceRequestContext requestContext, String type);

	void addAttributesBeforeExecution(SpanBuilder spanBuilder, ServiceRequest request, ServiceRequestContext requestContext, String type);

	void addAttributesErrorExecution(Span span, Throwable t);

	void addAttributesAfterExecution(Span span, Long duration, Long tracingOverhead);

	void addAttributesAfterExecution(Span span, Object result, Long duration, Long tracingOverhead);

	// -----------------------------------------------------------------------
	// CONFIGURE TRACING
	// -----------------------------------------------------------------------

	default InMemorySpanExporter spanExporter() {
		throw new IllegalStateException("Only to be used by JaegerInMemoryConnector");
	}

}