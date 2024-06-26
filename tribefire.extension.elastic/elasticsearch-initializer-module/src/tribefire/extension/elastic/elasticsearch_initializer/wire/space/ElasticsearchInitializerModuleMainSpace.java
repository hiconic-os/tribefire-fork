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
package tribefire.extension.elastic.elasticsearch_initializer.wire.space;

import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.elastic.elasticsearch_initializer.wire.contract.ElasticsearchInitializerModuleContract;
import tribefire.extension.elastic.elasticsearch_initializer.wire.contract.ElasticsearchInitializerModuleMainContract;
import tribefire.extension.elastic.elasticsearch_initializer.wire.contract.ElasticsearchInitializerModuleModelsContract;
import tribefire.extension.elastic.elasticsearch_initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.elastic.elasticsearch_initializer.wire.contract.RuntimePropertiesContract;
import tribefire.extension.elastic.templates.wire.contract.ElasticMetaDataContract;

/**
 * @see ElasticsearchInitializerModuleMainContract
 */
@Managed
public class ElasticsearchInitializerModuleMainSpace extends AbstractInitializerSpace implements ElasticsearchInitializerModuleMainContract {

	@Import
	private ElasticsearchInitializerModuleContract initializer;

	@Import
	private ElasticsearchInitializerModuleModelsContract models;

	@Import
	private RuntimePropertiesContract properties;

	@Import
	private ElasticMetaDataContract elasticMetaData;

	@Import
	private ExistingInstancesContract existingInstances;

	@Import
	private CoreInstancesContract coreInstances;

	@Override
	public ElasticsearchInitializerModuleContract initializerContract() {
		return initializer;
	}

	@Override
	public ElasticsearchInitializerModuleModelsContract initializerModelsContract() {
		return models;
	}

	@Override
	public RuntimePropertiesContract propertiesContract() {
		return properties;
	}

	@Override
	public ElasticMetaDataContract elasticMetaDataContract() {
		return elasticMetaData;
	}

	@Override
	public ExistingInstancesContract existingInstancesContract() {
		return existingInstances;
	}

	@Override
	public CoreInstancesContract coreInstancesContract() {
		return coreInstances;
	}
}
