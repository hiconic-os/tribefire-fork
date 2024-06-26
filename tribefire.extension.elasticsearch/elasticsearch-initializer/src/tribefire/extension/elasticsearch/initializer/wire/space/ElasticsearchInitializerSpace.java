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
package tribefire.extension.elasticsearch.initializer.wire.space;

import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.elasticsearch.initializer.wire.contract.ElasticsearchInitializerContract;
import tribefire.extension.elasticsearch.initializer.wire.contract.ElasticsearchInitializerModelsContract;
import tribefire.extension.elasticsearch.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.elasticsearch.model.deployment.ElasticsearchProcessor;

@Managed
public class ElasticsearchInitializerSpace extends AbstractInitializerSpace implements ElasticsearchInitializerContract {

	@Import
	private ElasticsearchInitializerModelsContract models;

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Managed
	private ElasticsearchProcessor elasticsearchProcessor() {
		ElasticsearchProcessor bean = create(ElasticsearchProcessor.T);

		bean.setName("Elasticsearch Processor");
		bean.setExternalId("serviceProcessor.elasticsearch");
		bean.setModule(existingInstances.elasticsearchModule());
		return bean;
	}

	@Override
	@Managed
	public ProcessWith processWithElasticsearch() {
		ProcessWith bean = create(ProcessWith.T);

		bean.setProcessor(elasticsearchProcessor());

		return bean;
	}

}
