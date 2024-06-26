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
package tribefire.extension.gcp.templates.wire.space;

import com.braintribe.model.extensiondeployment.meta.BinaryProcessWith;
import com.braintribe.model.gcp.deployment.GcpConnector;
import com.braintribe.model.gcp.deployment.GcpStorageBinaryProcessor;
import com.braintribe.model.gcp.resource.GcpStorageSource;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.resource.configuration.ExternalResourcesContext;
import com.braintribe.utils.lcd.StringTools;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.scope.InstanceConfiguration;

import tribefire.extension.gcp.templates.api.GcpBinaryProcessTemplateContext;
import tribefire.extension.gcp.templates.api.GcpConstants;
import tribefire.extension.gcp.templates.wire.contract.GcpTemplatesContract;

@Managed
public class GcpTemplatesSpace implements GcpTemplatesContract {


	@Override
	@Managed
	public GcpStorageBinaryProcessor gcpStorageBinaryProcessor(GcpBinaryProcessTemplateContext context) {
		GcpStorageBinaryProcessor bean = context.create(GcpStorageBinaryProcessor.T, InstanceConfiguration.currentInstance());
		bean.setCartridge(context.getGcpCartridge());
		bean.setModule(context.getGcpModule());
		bean.setName("GCP Storage Binary Processor "+context.getBucketName());
		bean.setConnector(connector(context));
		bean.setBucketName(context.getBucketName());
		bean.setAutoDeploy(credentialsAvailable(context));
		bean.setPathPrefix(context.getPathPrefix());
		return bean;
	}

	@Override
	@Managed
	public GcpConnector connector(GcpBinaryProcessTemplateContext context) {
		GcpConnector bean = context.create(GcpConnector.T, InstanceConfiguration.currentInstance());
		bean.setCartridge(context.getGcpCartridge());
		bean.setModule(context.getGcpModule());
		bean.setName("GCP Connector "+context.getClientEmail());
		bean.setJsonCredentials(context.getJsonCredentials());
		bean.setPrivateKeyId(context.getPrivateKeyId());
		bean.setPrivateKey(context.getPrivateKey());
		bean.setClientId(context.getClientId());
		bean.setClientEmail(context.getClientEmail());
		bean.setTokenServerUri(context.getTokenServerUri());
		bean.setProjectId(context.getProjectId());
		bean.setAutoDeploy(credentialsAvailable(context));
		return bean;
	}
	
	private boolean credentialsAvailable(GcpBinaryProcessTemplateContext context) {
		return !StringTools.isAllBlank(context.getJsonCredentials(), context.getPrivateKey());
	}

	@Override
	@Managed
	public ExternalResourcesContext externalResourcesContext(GcpBinaryProcessTemplateContext context) {
		ExternalResourcesContext bean = ExternalResourcesContext.builder()
				.setBinaryProcessWith(binaryProcessWithGcp(context))
				.setPersistenceDataModel((GmMetaModel) context.lookup("model:"+GcpConstants.DATA_MODEL_QUALIFIEDNAME))
				.setResourceSourceType(GcpStorageSource.T)
				.build();
		return bean;
	}
	
	@Managed
	private BinaryProcessWith binaryProcessWithGcp(GcpBinaryProcessTemplateContext context) {
		BinaryProcessWith bean = context.create(BinaryProcessWith.T, InstanceConfiguration.currentInstance());
		
		GcpStorageBinaryProcessor processor = gcpStorageBinaryProcessor(context);
		
		bean.setRetrieval(processor);
		bean.setPersistence(processor);
		return bean;
	}

}
