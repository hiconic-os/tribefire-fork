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
package tribefire.extension.elastic.elasticsearch_initializer;

import com.braintribe.model.elasticsearch.service.ElasticRequest;
import com.braintribe.model.elasticsearchdeployment.indexing.ElasticsearchDeleteMetaData;
import com.braintribe.model.elasticsearchdeployment.indexing.ElasticsearchIndexingMetaData;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.model.resource.Resource;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.elastic.elasticsearch_initializer.wire.ElasticsearchInitializerModuleWireModule;
import tribefire.extension.elastic.elasticsearch_initializer.wire.contract.ElasticsearchInitializerModuleMainContract;
import tribefire.extension.elastic.templates.api.ElasticTemplateContext;

/**
 * <p>
 * This {@link AbstractInitializer initializer} initializes targeted accesses with our custom instances available from
 * initializer's contracts.
 * </p>
 */
public class ElasticsearchInitializer extends AbstractInitializer<ElasticsearchInitializerModuleMainContract> {

	@Override
	public WireTerminalModule<ElasticsearchInitializerModuleMainContract> getInitializerWireModule() {
		return ElasticsearchInitializerModuleWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context,
			WiredInitializerContext<ElasticsearchInitializerModuleMainContract> initializerContext,
			ElasticsearchInitializerModuleMainContract initializerMainContract) {
		GmMetaModel cortexModel = initializerMainContract.coreInstancesContract().cortexModel();
		cortexModel.getDependencies().add(initializerMainContract.initializerModelsContract().configuredDeploymentModel());

		GmMetaModel cortexServiceModel = initializerMainContract.coreInstancesContract().cortexServiceModel();
		cortexServiceModel.getDependencies().add(initializerMainContract.initializerModelsContract().configuredServiceModel());

		initializerMainContract.initializerContract().adminServlet();
		initializerMainContract.initializerContract().serviceRequestProcessor();
		initializerMainContract.initializerContract().healthCheckProcessor();
		initializerMainContract.initializerContract().functionalCheckBundle();

		addMetaDataToModels(context, initializerMainContract);

		if (initializerMainContract.propertiesContract().ELASTIC_RUN_SERVICE()) {
			initializerMainContract.initializerContract().service();
		}

		if (initializerMainContract.propertiesContract().ELASTIC_CREATE_DEMO_ACCESS()) {
			setupDefaultConfiguration(context, initializerMainContract);
		}
	}

	private void addMetaDataToModels(PersistenceInitializationContext context, ElasticsearchInitializerModuleMainContract initializerMainContract) {
		ModelMetaDataEditor modelEditor = BasicModelMetaDataEditor
				.create(initializerMainContract.initializerModelsContract().configuredServiceModel()).withEtityFactory(context.getSession()::create)
				.done();
		modelEditor.onEntityType(ElasticRequest.T).addMetaData(initializerMainContract.initializerContract().serviceProcessWith());
	}

	private void setupDefaultConfiguration(PersistenceInitializationContext context,
			ElasticsearchInitializerModuleMainContract initializerMainContract) {
		ElasticTemplateContext templateContext = initializerMainContract.initializerContract().defaultElasticTemplateContext();
		initializerMainContract.initializerContract().demoAccess();

		ElasticsearchIndexingMetaData indexingMetaData = initializerMainContract.elasticMetaDataContract().indexingMetaData(templateContext);
		ElasticsearchDeleteMetaData deleteMetaData = initializerMainContract.elasticMetaDataContract().deleteMetaData(templateContext);

		ModelMetaDataEditor modelEditor = BasicModelMetaDataEditor.create(initializerMainContract.initializerModelsContract().demoDocumentModel())
				.withEtityFactory(context.getSession()::create).done();
		modelEditor.onEntityType(Resource.T) //
				.addPropertyMetaData(indexingMetaData) //
				.addPropertyMetaData(deleteMetaData);
	}
}
