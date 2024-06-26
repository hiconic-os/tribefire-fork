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
package tribefire.extension.simple.wire.space;

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.provider.Holder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.simple.deployables.access.SimpleInMemoryAccess;
import tribefire.extension.simple.deployables.service.SimpleEchoServiceProcessor;
import tribefire.extension.simple.deployables.terminal.SimpleWebTerminal;
import tribefire.extension.simple.model.deployment.service.SimpleEchoService;
import tribefire.module.wire.contract.TribefireWebPlatformContract;
import tribefire.module.wire.contract.WebPlatformResourcesContract;

/**
 * This space class hosts configuration of deployables based on their denotation types.
 */
@Managed
public class SimpleDeployablesSpace implements WireSpace { 

	@Import
	private TribefireWebPlatformContract tfPlatform;
	
	@Import
	private WebPlatformResourcesContract resources;
	
	// == State Change Processors == //

	// == Accesses == //
	/**
	 * Creates and configures a new {@link SimpleInMemoryAccess}.
	 */
	@Managed
	public SimpleInMemoryAccess simpleInMemoryAccess(
			ExpertContext<tribefire.extension.simple.model.deployment.access.SimpleInMemoryAccess> context) {
		// get denotation type which holds configuration settings
		tribefire.extension.simple.model.deployment.access.SimpleInMemoryAccess denotationType = context.getDeployable();

		// create new access instance
		SimpleInMemoryAccess access = new SimpleInMemoryAccess();

		// configure access
		access.setInitializeWithExampleData(denotationType.getInitializeWithExampleData());
		access.setMetaModelProvider(new Holder<>(denotationType.getMetaModel()));		
		
		return access;
	}

	// == Apps == //
	/**
	 * Creates and configures a new {@link SimpleWebTerminal}.
	 */
	@Managed
	public SimpleWebTerminal simpleWebTerminal(
			ExpertContext<tribefire.extension.simple.model.deployment.terminal.SimpleWebTerminal> context) {
		// get denotation type which holds configuration settings
		tribefire.extension.simple.model.deployment.terminal.SimpleWebTerminal denotationType = context.getDeployable();

		// create new web terminal instance
		SimpleWebTerminal terminal = new SimpleWebTerminal();

		// configure web terminal
		terminal.setPrintRequestHeaders(denotationType.getPrintHeaders());
		terminal.setPrintRequestParameters(denotationType.getPrintParameters());

		return terminal;
	}

	// == Service Processors == //
	/**
	 * Creates and configures a new {@link SimpleEchoServiceProcessor}.
	 */
	@Managed
	public SimpleEchoServiceProcessor simpleEchoServiceProcessor(ExpertContext<SimpleEchoService> context) {
		// get denotation type which holds configuration settings
		SimpleEchoService denotationType = context.getDeployable();

		// create new service processor instance
		SimpleEchoServiceProcessor processor = new SimpleEchoServiceProcessor();

		// configure service processor
		processor.setDelay(denotationType.getDelay());
		processor.setEchoCount(denotationType.getEchoCount());

		return processor;
	}

	// == Jobs == //
	
	
	// == Helpers == //

}
