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
package tribefire.extension.azure.templates.wire.space;

import com.braintribe.model.extensiondeployment.meta.BinaryProcessWith;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.resource.configuration.ExternalResourcesContext;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.scope.InstanceConfiguration;

import tribefire.extension.azure.model.deployment.AzureBlobBinaryProcessor;
import tribefire.extension.azure.model.resource.AzureBlobSource;
import tribefire.extension.azure.templates.api.AzureConstants;
import tribefire.extension.azure.templates.api.AzureTemplateContext;
import tribefire.extension.azure.templates.wire.contract.AzureTemplatesContract;

@Managed
public class AzureTemplatesSpace implements AzureTemplatesContract {

	@Override
	@Managed
	public AzureBlobBinaryProcessor binaryProcessor(AzureTemplateContext context) {
		AzureBlobBinaryProcessor bean = context.create(AzureBlobBinaryProcessor.T, InstanceConfiguration.currentInstance());
		bean.setModule(context.getAzureModule());
		bean.setName("Azure Blob Storage Binary Processor " + context.getContainerName());
		bean.setStorageConnectionString(context.getStorageConnectionString());
		bean.setContainerName(context.getContainerName());
		bean.setPathPrefix(context.getPathPrefix());
		return bean;
	}

	@Override
	@Managed
	public ExternalResourcesContext externalResourcesContext(AzureTemplateContext context) {
		ExternalResourcesContext bean = ExternalResourcesContext.builder().setBinaryProcessWith(binaryProcessWithAws(context))
				.setPersistenceDataModel((GmMetaModel) context.lookup("model:" + AzureConstants.DATA_MODEL))
				.setResourceSourceType(AzureBlobSource.T).build();
		return bean;
	}

	@Managed
	private BinaryProcessWith binaryProcessWithAws(AzureTemplateContext context) {
		BinaryProcessWith bean = context.create(BinaryProcessWith.T, InstanceConfiguration.currentInstance());

		AzureBlobBinaryProcessor processor = binaryProcessor(context);

		bean.setRetrieval(processor);
		bean.setPersistence(processor);
		return bean;
	}

}
