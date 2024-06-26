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
package tribefire.extension.elasticsearch.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.extension.elasticsearch.initializer.wire.ElasticsearchInitializerWireModule;
import tribefire.extension.elasticsearch.initializer.wire.contract.ElasticsearchInitializerContract;
import tribefire.extension.elasticsearch.initializer.wire.contract.ElasticsearchInitializerMainContract;
import tribefire.extension.elasticsearch.initializer.wire.contract.ElasticsearchInitializerModelsContract;
import tribefire.extension.elasticsearch.model.api.ElasticsearchRequest;

public class ElasticsearchInitializer extends AbstractInitializer<ElasticsearchInitializerMainContract> {

	@Override
	public WireTerminalModule<ElasticsearchInitializerMainContract> getInitializerWireModule() {
		return ElasticsearchInitializerWireModule.INSTANCE;
	}

	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<ElasticsearchInitializerMainContract> initializerContext,
			ElasticsearchInitializerMainContract initializerMainContract) {

		CoreInstancesContract coreInstances = initializerMainContract.coreInstances();
		ElasticsearchInitializerModelsContract models = initializerMainContract.models();
		coreInstances.cortexServiceModel().getDependencies().add(models.apiModel());
		addMetaDataToModels(initializerMainContract, models.apiModel());
	}

	private void addMetaDataToModels(ElasticsearchInitializerMainContract initializerMainContract, GmMetaModel apiModel) {
		ElasticsearchInitializerContract initializer = initializerMainContract.initializer();

		ModelMetaDataEditor editor = new BasicModelMetaDataEditor(apiModel);

		editor.onEntityType(ElasticsearchRequest.T).addMetaData(initializer.processWithElasticsearch());

	}
}
