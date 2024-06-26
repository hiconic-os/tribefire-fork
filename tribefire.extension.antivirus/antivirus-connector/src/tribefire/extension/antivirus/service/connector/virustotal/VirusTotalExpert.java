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
package tribefire.extension.antivirus.service.connector.virustotal;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.check.service.CheckStatus;
import com.braintribe.model.resource.Resource;

import tribefire.extension.antivirus.connector.api.AbstractAntivirusConnector;
import tribefire.extension.antivirus.model.deployment.repository.configuration.VirusTotalSpecification;
import tribefire.extension.antivirus.model.service.result.VirusTotalAntivirusResult;

public class VirusTotalExpert extends AbstractAntivirusConnector<VirusTotalAntivirusResult> {

	private static final Logger logger = Logger.getLogger(VirusTotalExpert.class);

	private VirusTotalScanner virusTotalScanner;
	private VirusTotalSpecification providerSpecification;

	@Override
	public VirusTotalAntivirusResult scanViaExpert() {
		try (InputStream in = resource.openStream()) {
			ScanResult scanResult = virusTotalScanner.scanFile(in);

			tribefire.extension.antivirus.service.connector.virustotal.FileScanReport report = virusTotalScanner.getScanReport(scanResult.getResource());

			int pollInterval = providerSpecification.getPollIntervalInSec();
			int pollDuration = providerSpecification.getPollDurationInSec();

			int count = 1;
			// according to JavaDoc of FileScanReport it should be 1 if the result is available and 0 if not. We saw
			// also null and -1 in a valid use case; therefore: everything > 0 is success, the others mean waiting
			while (((report.getResponseCode() == null || report.getResponseCode() <= 0) && count * pollInterval < pollDuration)) {
				TimeUnit.SECONDS.sleep(pollInterval);
				report = virusTotalScanner.getScanReport(scanResult.getResource());
				count++;

				int timeSpent = count * pollInterval;

				if (logger.isDebugEnabled()) {
					logger.debug("Waiting for VirusTotal to be finished. Got reponse code: '" + report.getResponseCode() + "' time spent: '"
							+ timeSpent + "'sec, max: '" + pollDuration + "'sec - " + resourceInformation(resource));
				}
			}

			Integer responseCode = report.getResponseCode();
			if (responseCode != null && responseCode == 1) {

				boolean infected = report.getPositives() != 0;
				String message = report.getVerboseMessage();

				VirusTotalAntivirusResult result = createResult(VirusTotalAntivirusResult.T, infected, message);

				result.setResourceID(report.getResource());
				result.setPermalink(report.getPermalink());
				result.setScanDate(report.getScanDate());

				return result;
			} else {
				throw new IllegalArgumentException("Error while getting response while using VirusTotal. Expected '1' but was: '" + responseCode
						+ "'- " + resourceInformation(resource));
			}
		} catch (UnsupportedEncodingException ex) {
			throw new IllegalArgumentException("Unsupported Encoding Format while using VirusTotal - " + resourceInformation(resource), ex);
		} catch (Exception ex) {
			throw new IllegalArgumentException("Error while scanning using VirusTotal. - " + resourceInformation(resource), ex);
		}
	}

	// ***************************************************************************************************
	// Initialization
	// ***************************************************************************************************

	public static VirusTotalExpert forScanForVirusAPI(VirusTotalSpecification context, Resource resource) {
		return createExpert(VirusTotalExpert::new, (expert) -> {
			expert.setVirusTotalScanner(new VirusTotalScanner(context.getApiKey()));
			expert.setResource(resource);
			expert.setProviderSpecification(context);
			expert.setProviderType(context.entityType().getShortName());
		});
	}

	public static VirusTotalExpert forHealthCheck(VirusTotalSpecification context, Resource resource) {
		return createExpert(VirusTotalExpert::new, (expert) -> {
			expert.setProviderSpecification(context);
			expert.setResource(resource);
		});
	}

	@Override
	public CheckResultEntry healthCheck() {
		CheckResultEntry checkResultEntry = CheckResultEntry.T.create();
		checkResultEntry.setName("VirusTotal check");

		String url = "https://www.virustotal.com/vtapi/v2/file/scan";

		try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
			HttpPost request = new HttpPost(url);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("file", new ByteArrayInputStream("".getBytes()));
			builder.addTextBody("apikey", providerSpecification.getApiKey());

			HttpEntity postEntity = builder.build();
			request.setEntity(postEntity);

			CloseableHttpResponse response = httpClient.execute(request);

			if (response.getStatusLine().getStatusCode() == 200) {
				checkResultEntry.setCheckStatus(CheckStatus.ok);
				checkResultEntry.setMessage(String.format("API is available on: %s", url));
			} else {
				String errorMessage = String.format("API returned with error. Status code: %s, ReasonPhrase: %s", response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
				checkResultEntry.setCheckStatus(CheckStatus.fail);
				checkResultEntry.setMessage(errorMessage);
				logger.debug(() -> errorMessage);
			}
		} catch (IOException e) {
			String errorMessage = String.format("Could not reach API! Url: %s, Reason: %s", url, e.getMessage());
			checkResultEntry.setCheckStatus(CheckStatus.fail);
			checkResultEntry.setMessage(errorMessage);
			logger.debug(() -> errorMessage, e);
		}

		return checkResultEntry;
	}

	public void setVirusTotalScanner(VirusTotalScanner virusTotalScanner) {
		this.virusTotalScanner = virusTotalScanner;
	}

	public void setProviderSpecification(VirusTotalSpecification providerSpecification) {
		this.providerSpecification = providerSpecification;
	}

}
