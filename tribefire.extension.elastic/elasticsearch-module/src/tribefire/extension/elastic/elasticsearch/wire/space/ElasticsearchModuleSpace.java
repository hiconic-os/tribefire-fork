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
package tribefire.extension.elastic.elasticsearch.wire.space;

import com.braintribe.model.elasticsearchdeployment.ElasticsearchConnector;
import com.braintribe.model.elasticsearchdeployment.ElasticsearchService;
import com.braintribe.model.elasticsearchdeployment.IndexedElasticsearchConnector;
import com.braintribe.model.elasticsearchdeployment.admin.ElasticsearchAdminServlet;
import com.braintribe.model.elasticsearchdeployment.aspect.ExtendedFulltextAspect;
import com.braintribe.model.elasticsearchdeployment.indexing.ElasticsearchIndexingWorker;
import com.braintribe.model.elasticsearchdeployment.service.ElasticServiceProcessor;
import com.braintribe.model.elasticsearchdeployment.service.HealthCheckProcessor;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class ElasticsearchModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private ElasticsearchDeployablesSpace deployables;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(HealthCheckProcessor.T) //
				.component(tfPlatform.binders().checkProcessor()) //
				.expertSupplier(this.deployables::healthCheckProcessor);

		bindings.bind(ElasticsearchService.T) //
				.component(tfPlatform.binders().webTerminal()) //
				.expertFactory(deployables::service);

		bindings.bind(ElasticsearchConnector.T) //
				.component(com.braintribe.model.processing.elasticsearch.ElasticsearchConnector.class) //
				.expertFactory(deployables::connector);

		bindings.bind(IndexedElasticsearchConnector.T) //
				.component(IndexedElasticsearchConnector.T, com.braintribe.model.processing.elasticsearch.IndexedElasticsearchConnector.class) //
				.expertFactory(deployables::indexedConnector) //
				.component(ElasticsearchConnector.T, com.braintribe.model.processing.elasticsearch.ElasticsearchConnector.class) //
				.expertFactory(deployables::connector);

		bindings.bind(ExtendedFulltextAspect.T) //
				.component(tfPlatform.binders().accessAspect()) //
				.expertFactory(deployables::fulltextAspect);

		bindings.bind(ElasticServiceProcessor.T) //
				.component(tfPlatform.binders().accessRequestProcessor()) //
				.expertSupplier(deployables::reflectionProcessor);

		bindings.bind(ElasticsearchAdminServlet.T) //
				.component(tfPlatform.binders().webTerminal()) //
				.expertSupplier(deployables::adminServlet);

		bindings.bind(ElasticsearchIndexingWorker.T) //
				.component(com.braintribe.model.processing.elasticsearch.indexing.ElasticsearchIndexingWorker.class) //
				.expertFactory(deployables::worker) //
				.component(tfPlatform.binders().worker()) //
				.expertFactory(deployables::worker);
	}
}
