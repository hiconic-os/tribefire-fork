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
package tribefire.extension.graphux.graph_ux_initializer.wire.space;

import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;

import tribefire.extension.graphux.graph_ux_initializer.wire.contract.GraphUxInitializerContract;
import tribefire.extension.graphux.graph_ux_initializer.wire.contract.GraphUxInitializerModelsContract;
import tribefire.extension.graphux.model.deployment.GraphUxService;
import tribefire.extension.graphux.graph_ux_initializer.wire.contract.ExistingInstancesContract;

@Managed
public class GraphUxInitializerSpace extends AbstractInitializerSpace implements GraphUxInitializerContract {

	@Import
	private GraphUxInitializerModelsContract models;
	
	@Import
	private ExistingInstancesContract existingInstances;
	
	@Import
	private CoreInstancesContract coreInstances;

	@Managed
	@Override
	public GraphUxService graphUxProcessor() {
		GraphUxService bean = create(GraphUxService.T);
		bean.setExternalId("service.graphux");
		
		bean.setName(GraphUxService.T.getShortName());
		bean.setModule(existingInstances.graphUxModule());

		return bean;
	}
	
	@Managed
	@Override
	public MetaData processWithGraphUxProcessor() {
		ProcessWith bean = create(ProcessWith.T);
		bean.setProcessor(graphUxProcessor());
		return bean;
	}
}
