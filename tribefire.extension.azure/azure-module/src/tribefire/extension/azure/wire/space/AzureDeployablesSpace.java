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
package tribefire.extension.azure.wire.space;

import com.braintribe.logging.Logger;
import com.braintribe.model.cache.CacheOptions;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.utils.lcd.StringTools;
import com.braintribe.utils.stream.tracking.InputStreamTracker;
import com.braintribe.utils.stream.tracking.OutputStreamTracker;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.azure.model.deployment.AzureBlobBinaryProcessor;
import tribefire.extension.azure.processing.AzureBlobBinaryProcessorImpl;
import tribefire.extension.azure.processing.AzureServiceProcessor;
import tribefire.extension.azure.processing.HealthCheckProcessor;
import tribefire.module.wire.contract.ResourceProcessingContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

@Managed
public class AzureDeployablesSpace implements WireSpace {

	private static Logger logger = Logger.getLogger(AzureDeployablesSpace.class);

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private ResourceProcessingContract resourceProcessing;

	@Managed
	public AzureBlobBinaryProcessorImpl binaryProcessor(ExpertContext<AzureBlobBinaryProcessor> context) {
		AzureBlobBinaryProcessor deployable = context.getDeployable();

		AzureBlobBinaryProcessorImpl bean = new AzureBlobBinaryProcessorImpl();

		CacheOptions cacheOptions = deployable.getCacheOptions();
		if (cacheOptions != null) {
			bean.setCacheType(cacheOptions.getType());
			bean.setCacheMaxAge(cacheOptions.getMaxAge());
			bean.setCacheMustRevalidate(cacheOptions.getMustRevalidate());
		}

		bean.setPathPrefix(deployable.getPathPrefix());
		bean.setStorageConnectionString(deployable.getStorageConnectionString());
		bean.setContainerName(deployable.getContainerName());
		bean.setStreamPipeFactory(tfPlatform.resourceProcessing().streamPipeFactory());

		bean.setDownloadInputStreamTracker(downloadInputStreamTracker());
		bean.setDownloadOutputStreamTracker(downloadOutputStreamTracker());

		logger.debug(() -> "Created AzureBlobBinaryProcessorImpl with Connection String "
				+ StringTools.simpleObfuscatePassword(deployable.getStorageConnectionString()) + " and Container " + deployable.getContainerName());

		return bean;
	}

	@Managed
	private InputStreamTracker downloadInputStreamTracker() {
		InputStreamTracker bean = new InputStreamTracker();
		return bean;
	}
	@Managed
	private OutputStreamTracker downloadOutputStreamTracker() {
		OutputStreamTracker bean = new OutputStreamTracker();
		return bean;
	}

	@Managed
	public HealthCheckProcessor healthCheckProcessor() {
		HealthCheckProcessor bean = new HealthCheckProcessor();
		bean.setSessionFactory(tfPlatform.systemUserRelated().sessionFactory());
		bean.setDownloadInputStreamTracker(downloadInputStreamTracker());
		bean.setDownloadOutputStreamTracker(downloadOutputStreamTracker());
		return bean;
	}

	@Managed
	public AzureServiceProcessor azureServiceProcessor() {
		AzureServiceProcessor bean = new AzureServiceProcessor();
		bean.setCortexSessionSupplier(tfPlatform.systemUserRelated().cortexSessionSupplier());
		return bean;
	}

}
