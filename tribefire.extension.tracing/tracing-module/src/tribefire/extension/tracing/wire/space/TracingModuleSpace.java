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
package tribefire.extension.tracing.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.tracing.model.deployment.connector.JaegerInMemoryTracingConnector;
import tribefire.extension.tracing.model.deployment.connector.JaegerTracingConnector;
import tribefire.extension.tracing.model.deployment.connector.LoggingTracingConnector;
import tribefire.extension.tracing.model.deployment.connector.TracingConnector;
import tribefire.extension.tracing.model.deployment.service.HealthCheckProcessor;
import tribefire.extension.tracing.model.deployment.service.TracingAspect;
import tribefire.extension.tracing.model.deployment.service.TracingProcessor;
import tribefire.extension.tracing.model.deployment.service.demo.DemoTracingProcessor;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class TracingModuleSpace implements TribefireModuleContract {

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
		bindings.bind(LoggingTracingConnector.T)
			.component(TracingConnector.T, tribefire.extension.tracing.connector.api.TracingConnector.class)
			.expertFactory(deployables::loggingTracingConnector);
	
		bindings.bind(JaegerTracingConnector.T)
			.component(TracingConnector.T, tribefire.extension.tracing.connector.api.TracingConnector.class)
			.expertFactory(deployables::jaegerTracingConnector);
		
		bindings.bind(JaegerInMemoryTracingConnector.T)
			.component(TracingConnector.T, tribefire.extension.tracing.connector.api.TracingConnector.class)
			.expertFactory(deployables::jaegerInMemoryTracingConnector);
		
		//----------------------------
		// PROCESSOR
		//----------------------------
		bindings.bind(TracingProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::tracingProcessor);
		
		//----------------------------
		// ASPECT
		//----------------------------
		bindings.bind(TracingAspect.T)
			.component(tfPlatform.binders().serviceAroundProcessor())
			.expertFactory(deployables::tracingAspect);
		
		//----------------------------
		// DEMO
		//----------------------------
		bindings.bind(DemoTracingProcessor.T)
			.component(tfPlatform.binders().serviceProcessor())
			.expertFactory(deployables::demoTracingProcessor);
		
		//----------------------------
		// DEMO
		//----------------------------
		bindings.bind(HealthCheckProcessor.T)
			.component(tfPlatform.binders().checkProcessor())
			.expertFactory(deployables::healthCheckProcessor);	
		//@formatter:on
	}

}
