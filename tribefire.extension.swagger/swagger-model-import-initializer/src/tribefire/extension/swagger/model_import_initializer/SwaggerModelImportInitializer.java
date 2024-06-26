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
package tribefire.extension.swagger.model_import_initializer;

import java.util.Set;

import com.braintribe.model.ddra.DdraConfiguration;
import com.braintribe.model.ddra.DdraMapping;
import com.braintribe.model.ddra.DdraUrlMethod;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.BasicModelMetaDataEditor;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.swagger.ConvertSwaggerFromUrlToModel;
import com.braintribe.swagger.ConvertSwaggerModelRequest;
import com.braintribe.swagger.ExportSwaggerModelRequest;
import com.braintribe.swagger.ImportSwaggerModelRequest;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.swagger.model_import_initializer.wire.SwaggerModelImportInitializerWireModule;
import tribefire.extension.swagger.model_import_initializer.wire.contract.SwaggerModelImportInitializerMainContract;

public class SwaggerModelImportInitializer extends AbstractInitializer<SwaggerModelImportInitializerMainContract> {

	@Override
	public WireTerminalModule<SwaggerModelImportInitializerMainContract> getInitializerWireModule() {
		return SwaggerModelImportInitializerWireModule.INSTANCE;
	}
	
	@Override
	public void initialize(PersistenceInitializationContext context, WiredInitializerContext<SwaggerModelImportInitializerMainContract> initializerContext,
			SwaggerModelImportInitializerMainContract initializerMainContract) {

		GmMetaModel cortexModel = initializerMainContract.coreInstances().cortexModel();
		GmMetaModel cortexServiceModel = initializerMainContract.coreInstances().cortexServiceModel();

		cortexModel.getDependencies()
				.add(initializerMainContract.existingInstances().deploymentModel());

		cortexServiceModel.getDependencies()
				.add(initializerMainContract.existingInstances().dataModel());
		cortexServiceModel.getDependencies()
				.add(initializerMainContract.existingInstances().serviceModel());

		addMetadataToEntities(initializerMainContract);
		configureDdraMappings(initializerContext, initializerMainContract);
	
	}

	private void addMetadataToEntities(SwaggerModelImportInitializerMainContract initializerMainContract) {
		ModelMetaDataEditor modelEditor = new BasicModelMetaDataEditor(
				initializerMainContract.existingInstances().serviceModel());
		modelEditor.onEntityType(ImportSwaggerModelRequest.T).addMetaData(initializerMainContract.initializer().processWithImportSwaggerModelProcessor());
		modelEditor.onEntityType(ExportSwaggerModelRequest.T).addMetaData(initializerMainContract.initializer().processWithExportSwaggerModelProcessor());
		modelEditor.onEntityType(ConvertSwaggerModelRequest.T).addMetaData(initializerMainContract.initializer().processWithConvertSwaggerModelProcessor());
	}
	
	private void configureDdraMappings(WiredInitializerContext<SwaggerModelImportInitializerMainContract> initializerContext, SwaggerModelImportInitializerMainContract initializerMainContract) {
		DdraConfiguration config = initializerMainContract.existingInstances().ddraConfig();
		Set<DdraMapping> mappings = config.getMappings();
		mappings.add(ddraMapping("/swagger/convert-from-url", initializerContext.lookup("type:"+ConvertSwaggerFromUrlToModel.T.getTypeSignature())));
	}
	
	@Managed
	private DdraMapping ddraMapping(String path, GmEntityType type) {
		DdraMapping bean = DdraMapping.T.create();
		bean.setGlobalId("ddra:/"+path);
		bean.setPath(path);
		bean.setRequestType(type);
		bean.setMethod(DdraUrlMethod.GET);
		bean.setDefaultServiceDomain("cortex");
		return bean;
	}
	
}
