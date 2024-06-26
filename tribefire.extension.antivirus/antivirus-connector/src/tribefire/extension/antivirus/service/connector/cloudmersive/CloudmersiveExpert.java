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
package tribefire.extension.antivirus.service.connector.cloudmersive;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.check.service.CheckStatus;
import com.braintribe.model.resource.Resource;
import com.braintribe.utils.FileTools;
import com.braintribe.utils.RandomTools;
import com.cloudmersive.client.ScanApi;
import com.cloudmersive.client.invoker.ApiClient;
import com.cloudmersive.client.invoker.ApiException;
import com.cloudmersive.client.invoker.Configuration;
import com.cloudmersive.client.invoker.auth.ApiKeyAuth;
import com.cloudmersive.client.model.VirusScanResult;

import tribefire.extension.antivirus.connector.api.AbstractAntivirusConnector;
import tribefire.extension.antivirus.model.deployment.repository.configuration.CloudmersiveSpecification;
import tribefire.extension.antivirus.model.service.result.CloudmersiveAntivirusResult;
import tribefire.extension.antivirus.service.connector.virustotal.VirusTotalExpert;

public class CloudmersiveExpert extends AbstractAntivirusConnector<CloudmersiveAntivirusResult> {

	private static final Logger logger = Logger.getLogger(VirusTotalExpert.class);

	private ScanApi scanApi;
	private CloudmersiveSpecification providerSpecification;

	@Override
	public CloudmersiveAntivirusResult scanViaExpert() {
		File tmpFile = null;
		try (InputStream in = resource.openStream()) {

			String name = resourceName(resource);

			tmpFile = FileTools.createNewTempFile(RandomTools.getRandom32CharactersHexString(true) + "_" + name);

			Files.copy(in, tmpFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
			VirusScanResult scanResult = scanApi.scanFile(tmpFile);

			boolean infected = !scanResult.isCleanResult();
			String message = "Viruses found: " + (scanResult.getFoundViruses() == null ? "0" : scanResult.getFoundViruses().size());
			CloudmersiveAntivirusResult result = createResult(CloudmersiveAntivirusResult.T, infected, message);

			if (scanResult.getFoundViruses() != null) {
				scanResult.getFoundViruses().forEach(v -> {
					result.getVirusNames().add(v.getVirusName());
				});
			}

			return result;
		} catch (ApiException e) {
			throw new IllegalArgumentException("Error when trying to scan file via Cloudmersive API - " + resourceInformation(resource), e);
		} catch (IOException e) {
			throw new UncheckedIOException(
					"Errorwhen trying to scan file via Cloudmersive - could not create temp file;  - " + resourceInformation(resource), e);
		} finally {
			FileTools.deleteFileSilently(tmpFile);
		}
	}

	// ***************************************************************************************************
	// Initialization
	// ***************************************************************************************************

	public static CloudmersiveExpert forScanForVirus(CloudmersiveSpecification context, Resource resource) {
		return createExpert(CloudmersiveExpert::new, (expert) -> {
			ApiClient defaultClient = Configuration.getDefaultApiClient();

			ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
			Apikey.setApiKey(context.getApiKey());

			ScanApi scanApi = new ScanApi();
			expert.setScanApi(scanApi);
			expert.setResource(resource);
			expert.setProviderType(context.entityType().getShortName());
		});
	}

	public static CloudmersiveExpert forHealthCheck(CloudmersiveSpecification providerSpecification, Resource resource) {
		return createExpert(CloudmersiveExpert::new, (expert) -> {
			expert.setProviderSpecification(providerSpecification);
			expert.setResource(resource);
		});
	}

	public void setScanApi(ScanApi scanApi) {
		this.scanApi = scanApi;
	}


	@Override
	public CheckResultEntry healthCheck() {
		CheckResultEntry checkResultEntry = CheckResultEntry.T.create();
		checkResultEntry.setName("Cloudmersive check");

		ApiClient defaultClient = Configuration.getDefaultApiClient();

		ApiKeyAuth Apikey = (ApiKeyAuth) defaultClient.getAuthentication("Apikey");
		Apikey.setApiKey(providerSpecification.getApiKey());

		ScanApi scanApi = new ScanApi();
		File tmpFile = FileTools.createNewTempFile("HealthCheck.tmp");

		try {
			VirusScanResult scanResult = scanApi.scanFile(tmpFile);

			if (scanResult.isCleanResult()) {
				checkResultEntry.setCheckStatus(CheckStatus.ok);
				checkResultEntry.setMessage(String.format("API is available on: %s", "https://api.cloudmersive.com"));
			} else {
				String errorMessage = String.format("API returned with error.");
				checkResultEntry.setCheckStatus(CheckStatus.fail);
				checkResultEntry.setMessage(errorMessage);
				logger.debug(() -> errorMessage);
			}
		} catch (ApiException e) {
			String errorMessage = String.format("Could not reach API! Url: %s, Reason: %s", "https://api.cloudmersive.com", e.getMessage());
			checkResultEntry.setCheckStatus(CheckStatus.fail);
			checkResultEntry.setMessage(errorMessage);
			logger.debug(() -> errorMessage, e);
		}
		return checkResultEntry;
	}

	public void setProviderSpecification(CloudmersiveSpecification providerSpecification) {
		this.providerSpecification = providerSpecification;
	}
}
