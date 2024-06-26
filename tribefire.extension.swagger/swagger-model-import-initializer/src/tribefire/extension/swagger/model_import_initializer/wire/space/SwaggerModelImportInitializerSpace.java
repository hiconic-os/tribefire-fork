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
package tribefire.extension.swagger.model_import_initializer.wire.space;

import com.braintribe.model.extensiondeployment.meta.ProcessWith;
import com.braintribe.model.meta.data.MetaData;
import com.braintribe.swagger.deployment.ConvertSwaggerModelProcessor;
import com.braintribe.swagger.deployment.ExportSwaggerModelProcessor;
import com.braintribe.swagger.deployment.ImportSwaggerModelProcessor;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.swagger.model_import_initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.swagger.model_import_initializer.wire.contract.SwaggerModelImportInitializerContract;

@Managed
public class SwaggerModelImportInitializerSpace extends AbstractInitializerSpace implements SwaggerModelImportInitializerContract {

	@Import
	private ExistingInstancesContract existingInstances;
	
	@Import
	private CoreInstancesContract coreInstances;
	
	@Managed
	@Override
	public ImportSwaggerModelProcessor importSwaggerModelProcessor() {
		ImportSwaggerModelProcessor bean = create(ImportSwaggerModelProcessor.T);
		bean.setExternalId("swaggerImportProcessor.serviceProcessor");
		bean.setName("Swagger Import Service Processor");
		return bean;
	}
	
	@Managed
	@Override
	public ConvertSwaggerModelProcessor convertSwaggerModelProcessor() {
		ConvertSwaggerModelProcessor bean = create(ConvertSwaggerModelProcessor.T);
		bean.setExternalId("swaggerConvertProcessor.serviceProcessor");
		bean.setName("Swagger Convert Service Processor");
		return bean;
	}
	
	@Managed
	@Override
	public ExportSwaggerModelProcessor exportSwaggerModelProcessor() {
		ExportSwaggerModelProcessor bean = create(ExportSwaggerModelProcessor.T);
		bean.setExternalId("swaggerExportProcessor.serviceProcessor");
		bean.setName("Swagger Export Service Processor");
		return bean;
	}

	@Managed
	@Override
	public MetaData processWithImportSwaggerModelProcessor() {
		ProcessWith md = create(ProcessWith.T);
		md.setProcessor(importSwaggerModelProcessor());
		return md;
	}
	
	@Managed
	@Override
	public MetaData processWithExportSwaggerModelProcessor() {
		ProcessWith md = create(ProcessWith.T);
		md.setProcessor(exportSwaggerModelProcessor());
		return md;
	}
	
	@Managed
	@Override
	public MetaData processWithConvertSwaggerModelProcessor() {
		ProcessWith md = create(ProcessWith.T);
		md.setProcessor(convertSwaggerModelProcessor());
		return md;
	}
	
}
