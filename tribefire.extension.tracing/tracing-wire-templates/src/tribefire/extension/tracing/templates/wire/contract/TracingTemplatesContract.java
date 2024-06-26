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
package tribefire.extension.tracing.templates.wire.contract;

import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.tracing.model.deployment.connector.TracingConnector;
import tribefire.extension.tracing.model.deployment.service.TracingAspect;
import tribefire.extension.tracing.model.deployment.service.TracingProcessor;
import tribefire.extension.tracing.model.deployment.service.demo.DemoTracingProcessor;
import tribefire.extension.tracing.templates.api.TracingTemplateContext;

public interface TracingTemplatesContract extends WireSpace {

	/**
	 * Setup TRACING with a specified {@link TracingTemplateContext}
	 */
	void setupTracing(TracingTemplateContext context);

	TracingProcessor tracingServiceProcessor(TracingTemplateContext context);

	TracingConnector tracingConnector(TracingTemplateContext context);

	TracingAspect tracingAspect(TracingTemplateContext context);

	DemoTracingProcessor demoTracingProcessor(TracingTemplateContext context);

}
