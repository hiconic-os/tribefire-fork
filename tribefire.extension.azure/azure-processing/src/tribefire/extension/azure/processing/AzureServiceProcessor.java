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
package tribefire.extension.azure.processing;

import java.util.function.Supplier;

import com.azure.core.http.rest.PagedIterable;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.models.BlobContainerProperties;
import com.azure.storage.blob.models.BlobItem;
import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.LifecycleAware;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.query.fluent.EntityQueryBuilder;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.service.impl.AbstractDispatchingServiceProcessor;
import com.braintribe.model.processing.service.impl.DispatchConfiguration;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.query.EntityQuery;

import tribefire.extension.azure.model.api.AzureRequest;
import tribefire.extension.azure.model.api.AzureResult;
import tribefire.extension.azure.model.api.CheckConnection;
import tribefire.extension.azure.model.api.ConnectionStatus;
import tribefire.extension.azure.model.deployment.AzureBlobBinaryProcessor;

public class AzureServiceProcessor extends AbstractDispatchingServiceProcessor<AzureRequest, AzureResult> implements LifecycleAware {

	private final static Logger logger = Logger.getLogger(AzureServiceProcessor.class);

	private Supplier<PersistenceGmSession> cortexSessionSupplier;

	@Override
	protected void configureDispatching(DispatchConfiguration<AzureRequest, AzureResult> dispatching) {
		dispatching.register(CheckConnection.T, this::checkConnection);
	}

	private ConnectionStatus checkConnection(@SuppressWarnings("unused") ServiceRequestContext context, CheckConnection request) {

		ConnectionStatus result = ConnectionStatus.T.create();

		String externalId = request.getAzureBlobBinaryProcessorExternalId();
		PersistenceGmSession cortexSession = cortexSessionSupplier.get();

		//@formatter:off
		EntityQuery query = EntityQueryBuilder.from(AzureBlobBinaryProcessor.T)
				.where()
					.property(AzureBlobBinaryProcessor.externalId).eq(externalId)
				.done();
		//@formatter:on

		AzureBlobBinaryProcessor proc = (AzureBlobBinaryProcessor) cortexSession.query().entities(query).first();

		long start = System.currentTimeMillis();

		try {
			BlobContainerClient client = getContainerClient(proc.getStorageConnectionString(), proc.getContainerName());

			String accountName = client.getAccountName();
			result.setAccountName(accountName);

			PagedIterable<BlobItem> listBlobs = client.listBlobs();
			listBlobs.iterator().next();

			BlobContainerProperties properties = client.getProperties();
			logger.debug(() -> "Read properties: " + properties);

			result.setListBlobsSuccess(true);
		} catch (Exception e) {
			logger.error("Could not get properties or blob iterator from container " + proc.getContainerName(), e);
			result.setListBlobsSuccess(false);
		}

		result.setDurationInMs(System.currentTimeMillis() - start);

		return result;
	}

	private BlobContainerClient getContainerClient(String storageConnectionString, String containerName) {
		// Create a BlobServiceClient object which will be used to create a container client
		BlobServiceClient blobServiceClient = new BlobServiceClientBuilder().connectionString(storageConnectionString).buildClient();

		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		if (blobContainerClient == null) {
			// Create the container and return a container client object
			blobContainerClient = blobServiceClient.createBlobContainer(containerName);
		}
		return blobContainerClient;
	}

	@Override
	public void postConstruct() {
		logger.debug(() -> AzureServiceProcessor.class.getSimpleName() + " deployed.");
	}

	@Override
	public void preDestroy() {
		logger.debug(() -> AzureServiceProcessor.class.getSimpleName() + " undeployed.");
	}
	@Required
	@Configurable
	public void setCortexSessionSupplier(Supplier<PersistenceGmSession> cortexSessionSupplier) {
		this.cortexSessionSupplier = cortexSessionSupplier;
	}

}
