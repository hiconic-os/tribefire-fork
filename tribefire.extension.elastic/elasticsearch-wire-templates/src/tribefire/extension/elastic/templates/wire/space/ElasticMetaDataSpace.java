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

import com.braintribe.model.elasticsearchdeployment.indexing.ElasticsearchDeleteMetaData;
import com.braintribe.model.elasticsearchdeployment.indexing.ElasticsearchIndexingMetaData;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.elastic.ElasticConstants;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.scope.InstanceConfiguration;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.elastic.templates.api.ElasticTemplateContext;
import tribefire.extension.elastic.templates.wire.contract.ElasticMetaDataContract;
import tribefire.extension.elastic.templates.wire.contract.ElasticTemplatesContract;

@Managed
public class ElasticMetaDataSpace implements WireSpace, ElasticMetaDataContract {

	@Import
	private ElasticTemplatesContract documentsTemplates;

	@Override
	@Managed
	public GmMetaModel reflectionModel(ElasticTemplateContext context) {
		GmMetaModel rawReflectionModel = (GmMetaModel) context.lookup("model:" + ElasticConstants.REFLECTION_MODEL_QUALIFIEDNAME);

		GmMetaModel model = context.create(GmMetaModel.T, InstanceConfiguration.currentInstance());

		setModelDetails(model, ElasticConstants.REFLECTION_MODEL_QUALIFIEDNAME + "-" + context.getName().toLowerCase(), rawReflectionModel);
		return model;

	}

	@Override
	@Managed
	public GmMetaModel serviceModel(ElasticTemplateContext context) {
		GmMetaModel model = context.create(GmMetaModel.T, InstanceConfiguration.currentInstance());
		GmMetaModel rawServiceModel = (GmMetaModel) context.lookup("model:" + ElasticConstants.SERVICE_MODEL_QUALIFIEDNAME);
		setModelDetails(model, ElasticConstants.SERVICE_MODEL_QUALIFIEDNAME + "-" + context.getName().toLowerCase(), rawServiceModel);
		return model;
	}

	@Override
	public void metaData(ElasticTemplateContext context) {
		// TODO?
	}

	@Override
	@Managed
	public ElasticsearchIndexingMetaData indexingMetaData(ElasticTemplateContext context) {
		ElasticsearchIndexingMetaData bean = context.create(ElasticsearchIndexingMetaData.T, InstanceConfiguration.currentInstance());
		bean.setCascade(true);
		bean.setInherited(true);
		return bean;
	}

	private static void setModelDetails(GmMetaModel targetModel, String modelName, GmMetaModel... dependencies) {
		targetModel.setName(modelName);
		targetModel.setVersion(ElasticConstants.MAJOR_VERSION + ".0");
		if (dependencies != null) {
			for (GmMetaModel dependency : dependencies) {
				if (dependency != null) {
					targetModel.getDependencies().add(dependency);
				}
			}
		}
	}

	@Override
	@Managed
	public ElasticsearchDeleteMetaData deleteMetaData(ElasticTemplateContext context) {
		ElasticsearchDeleteMetaData bean = context.create(ElasticsearchDeleteMetaData.T, InstanceConfiguration.currentInstance());
		bean.setInherited(true);
		return bean;
	}
}
