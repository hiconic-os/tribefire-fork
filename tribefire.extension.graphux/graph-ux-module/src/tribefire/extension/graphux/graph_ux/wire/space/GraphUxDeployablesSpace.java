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
package tribefire.extension.graphux.graph_ux.wire.space;

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.module.wire.contract.TribefireWebPlatformContract;
import tribefire.extension.graphux.deployables.service.GraphUxServiceProcessor;
import tribefire.extension.graphux.model.deployment.GraphUxService;

@Managed
public class GraphUxDeployablesSpace implements WireSpace {

	@Import
	private TribefireWebPlatformContract tfPlatform;
	// == Service Processors == //
	/**
	 * Creates and configures a new {@link GraphUxServiceProcessor}.
	 */
	@Managed
	public GraphUxServiceProcessor graphUxServiceProcessor(ExpertContext<GraphUxService> context) {
		// get denotation type which holds configuration settings
		GraphUxService denotationType = context.getDeployable();

		// create new service processor instance
		GraphUxServiceProcessor processor = new GraphUxServiceProcessor();

		// configure service processor
//		processor.setDelay(denotationType.getDelay());
//		processor.setEchoCount(denotationType.getEchoCount());

		return processor;
	}
}
