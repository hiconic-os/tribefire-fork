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
package tribefire.extension.antivirus.service.connector.clamav;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.braintribe.logging.Logger;
import com.braintribe.model.check.service.CheckResultEntry;
import com.braintribe.model.check.service.CheckStatus;
import com.braintribe.model.resource.Resource;

import tribefire.extension.antivirus.connector.api.AbstractAntivirusConnector;
import tribefire.extension.antivirus.model.deployment.repository.configuration.ClamAVSpecification;
import tribefire.extension.antivirus.model.service.result.ClamAVAntivirusResult;

public class ClamAVExpert extends AbstractAntivirusConnector<ClamAVAntivirusResult> {

	private static final Logger logger = Logger.getLogger(ClamAVExpert.class);

	private ClamScan clamScan;
	private ClamAVSpecification providerSpecification;

	@Override
	public ClamAVAntivirusResult scanViaExpert() {
		ScanResult scanResult = clamScan.scan(resource);

		boolean infected = scanResult.getStatus().equals(ScanResult.Status.FAILED);
		String message = scanResult.getStatus().name();

		ClamAVAntivirusResult result = createResult(ClamAVAntivirusResult.T, infected, message);

		// ClamAV specific results
		result.setStatus(scanResult.getStatus().name());
		result.setSignature(scanResult.getSignature());

		if (scanResult.getException() != null) {
			result.setException(scanResult.getException().toString());
		}

		return result;
	}

	// ***************************************************************************************************
	// Initialization
	// ***************************************************************************************************

	public static ClamAVExpert forScanForVirus(ClamAVSpecification context, Resource resource) {
		return createExpert(ClamAVExpert::new, (expert) -> {
			ClamScan clamScan = new ClamScan(context.getURL(), context.getPort(), context.getTimeout());
			expert.setClamScan(clamScan);
			expert.setResource(resource);
			expert.setProviderType(context.entityType().getShortName());
		});
	}

	public static ClamAVExpert forHealthCheck(ClamAVSpecification providerSpecification, Resource resource) {
		return createExpert(ClamAVExpert::new, (expert) -> {
			expert.setProviderSpecification(providerSpecification);
			expert.setResource(resource);
		});
	}

	@Override
	public CheckResultEntry healthCheck() {
		CheckResultEntry checkResultEntry = CheckResultEntry.T.create();
		checkResultEntry.setName("ClamAV check");

		String host = providerSpecification.getURL();
		Integer port = providerSpecification.getPort();

		try (Socket socket = new Socket()) {
			socket.setSoTimeout(5);
			socket.connect(new InetSocketAddress(host, port));

			if (socket.isConnected()) {
				checkResultEntry.setCheckStatus(CheckStatus.ok);
				checkResultEntry.setMessage(String.format("Connect was successful! Host: %s, Port: %s", host, port));
			} else {
				String errorMessage = String.format("Could connect to socket but connection is not established! Host: %s, Port: %s", host, port);
				checkResultEntry.setCheckStatus(CheckStatus.fail);
				checkResultEntry.setMessage(errorMessage);
				logger.debug(() -> errorMessage);
			}
		} catch (IOException e) {
			String errorMessage = String.format("Could not connect! Host: %s, Port: %s, Reason: %s", host, port, e.getMessage());
			checkResultEntry.setCheckStatus(CheckStatus.fail);
			checkResultEntry.setMessage(errorMessage);
			logger.debug(() -> errorMessage, e);
		}

		return checkResultEntry;
	}

	// Getters, Setters

	public void setClamScan(ClamScan clamScan) {
		this.clamScan = clamScan;
	}

	public void setProviderSpecification(ClamAVSpecification providerSpecification) {
		this.providerSpecification = providerSpecification;
	}
}
