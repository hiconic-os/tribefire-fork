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
package tribefire.extension.elastic.templates.wire.space;

import com.braintribe.model.elasticsearchdeployment.ElasticsearchConnector;
import com.braintribe.model.elasticsearchdeployment.IndexedElasticsearchConnector;
import com.braintribe.model.elasticsearchdeployment.aspect.ExtendedFulltextAspect;
import com.braintribe.model.elasticsearchdeployment.indexing.ElasticsearchIndexingWorker;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.scope.InstanceConfiguration;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.elastic.templates.api.ElasticTemplateContext;
import tribefire.extension.elastic.templates.wire.contract.ElasticTemplatesContract;

@Managed
public class ElasticTemplatesSpace implements WireSpace, ElasticTemplatesContract {

	@Import
	private ElasticMetaDataSpace documentsMetaData;

	@Override
	@Managed
	public ElasticsearchConnector connector(ElasticTemplateContext context) {
		ElasticsearchConnector bean = context.create(ElasticsearchConnector.T, InstanceConfiguration.currentInstance());
		bean.setCartridge(context.getElasticCartridge());
		bean.setModule(context.getElasticModule());
		bean.setName("Elasticsearch Connector " + context.getName());
		bean.setClusterName(context.getClusterName());
		bean.setClusterSniff(context.getClusterSniff());
		bean.setPort(context.getConnectorPort());
		bean.setHost(context.getConnectorHost());
		bean.setAutoDeploy(context.getDeployConnector());
		return bean;
	}

	@Override
	@Managed
	public IndexedElasticsearchConnector indexedConnector(ElasticTemplateContext context) {
		IndexedElasticsearchConnector bean = context.create(IndexedElasticsearchConnector.T, InstanceConfiguration.currentInstance());
		bean.setCartridge(context.getElasticCartridge());
		bean.setModule(context.getElasticModule());
		bean.setName("Elasticsearch Connector " + context.getName());
		bean.setClusterName(context.getClusterName());
		bean.setClusterSniff(context.getClusterSniff());
		bean.setPort(context.getConnectorPort());
		bean.setHost(context.getConnectorHost());
		bean.setIndex(context.getIndex());
		bean.setAutoDeploy(context.getDeployConnector());
		return bean;
	}

	@Override
	@Managed
	public ExtendedFulltextAspect fulltextAspect(ElasticTemplateContext context) {
		ExtendedFulltextAspect bean = context.create(ExtendedFulltextAspect.T, InstanceConfiguration.currentInstance());
		bean.setCartridge(context.getElasticCartridge());
		bean.setModule(context.getElasticModule());
		bean.setName("Elasticsearch Fulltext Aspect " + context.getName());
		bean.setElasticsearchConnector(indexedConnector(context));
		bean.setMaxFulltextResultSize(context.getMaxFulltextResultSize());
		bean.setMaxResultWindow(context.getMaxResultWindow());
		bean.setWorker(worker(context));
		bean.setAutoDeploy(context.getDeployConnector());
		return bean;
	}

	@Override
	@Managed
	public ElasticsearchIndexingWorker worker(ElasticTemplateContext context) {
		ElasticsearchIndexingWorker bean = context.create(ElasticsearchIndexingWorker.T, InstanceConfiguration.currentInstance());
		bean.setCartridge(context.getElasticCartridge());
		bean.setModule(context.getElasticModule());
		bean.setName("Elasticsearch Worker " + context.getName());
		bean.setElasticsearchConnector(indexedConnector(context));
		bean.setThreadCount(context.getIndexingThreadCount());
		bean.setQueueSize(context.getIndexingQueueSize());
		bean.setAutoDeploy(context.getDeployConnector());
		bean.setAccess(context.getAccess());
		return bean;
	}

}
