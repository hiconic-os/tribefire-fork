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
package tribefire.extension.elastic.templates.wire.contract;

import com.braintribe.model.elasticsearchdeployment.ElasticsearchConnector;
import com.braintribe.model.elasticsearchdeployment.IndexedElasticsearchConnector;
import com.braintribe.model.elasticsearchdeployment.aspect.ExtendedFulltextAspect;
import com.braintribe.model.elasticsearchdeployment.indexing.ElasticsearchIndexingWorker;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.elastic.templates.api.ElasticTemplateContext;

public interface ElasticTemplatesContract extends WireSpace {

	ElasticsearchConnector connector(ElasticTemplateContext context);

	IndexedElasticsearchConnector indexedConnector(ElasticTemplateContext context);

	ExtendedFulltextAspect fulltextAspect(ElasticTemplateContext context);

	ElasticsearchIndexingWorker worker(ElasticTemplateContext context);
}
