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
package tribefire.extension.graphux.graph_ux.test.wire.space;

import com.braintribe.gm.service.access.api.AccessProcessingConfiguration;
import com.braintribe.gm.service.access.wire.common.contract.AccessProcessingConfigurationContract;
import com.braintribe.gm.service.wire.common.contract.CommonServiceProcessingContract;
import com.braintribe.gm.service.wire.common.contract.ServiceProcessingConfigurationContract;
import com.braintribe.model.access.smood.basic.SmoodAccess;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.processing.securityservice.commons.service.InMemorySecurityServiceProcessor;
import com.braintribe.model.processing.service.common.ConfigurableDispatchingServiceProcessor;
import com.braintribe.model.service.api.ServiceRequest;
import com.braintribe.testing.tools.gm.GmTestTools;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.context.WireContextConfiguration;

import tribefire.extension.graphux.deployables.service.GraphUxServiceProcessor;
import tribefire.extension.graphux.graph_ux.test.wire.contract.GraphUxTestContract;
import tribefire.extension.graphux.model.service.GraphUxServiceRequest;
import tribefire.extension.simple.model.data.Company;

@Managed
public class GraphUxTestSpace implements GraphUxTestContract {
	
	private SmoodAccess access;
	
	@Import
	private AccessProcessingConfigurationContract accessProcessingConfiguration;

	@Import
	private ServiceProcessingConfigurationContract serviceProcessingConfiguration;
	
	@Import
	private CommonServiceProcessingContract commonServiceProcessing;
	
	@Override
	public void onLoaded(WireContextConfiguration configuration) {
		accessProcessingConfiguration.registerAccessConfigurer(this::configureAccesses);
		serviceProcessingConfiguration.registerServiceConfigurer(this::configureServices);
		serviceProcessingConfiguration.registerSecurityConfigurer(this::configureSecurity);
	}
	
	private void configureAccesses(AccessProcessingConfiguration configuration) {	
		configuration.registerAccess(access());
		configuration.registerAccessRequestProcessor(GraphUxServiceRequest.T, graphUxServiceProcessor());
	}
	
	private void configureServices(ConfigurableDispatchingServiceProcessor bean) {
		bean.removeInterceptor("auth"); // remove this line if you want your requests authorized while testing	
	}

	private void configureSecurity(InMemorySecurityServiceProcessor bean) {
		// TODO configure security IF your requests are to be authorized while testing
		// (make sure the 'auth' interceptor is not removed in that case in the 'configureServices' method)
	}
	
	@Override
	public Evaluator<ServiceRequest> evaluator() {
		return commonServiceProcessing.evaluator();
	}
	
	@Override
	public SmoodAccess access() {
		if (access == null) access = GmTestTools.newSmoodAccessMemoryOnly("test.access", Company.T.getModel().getMetaModel());
		return access;
	}
	
	@Managed
	private GraphUxServiceProcessor graphUxServiceProcessor() {
		GraphUxServiceProcessor bean = new GraphUxServiceProcessor();
		return bean;
	}
	
	
	
}
