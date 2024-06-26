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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;


public class VirusTotalScanner {

	private static final String URI_VT2_FILE_SCAN = "https://www.virustotal.com/vtapi/v2/file/scan";
	private static final String URI_VT2_FILE_SCAN_REPORT = "https://www.virustotal.com/vtapi/v2/file/report";

	private String apiKey;

	private Gson gsonProcessor;

	public VirusTotalScanner(String apiKey) {
		this.apiKey = apiKey;
		this.gsonProcessor = new Gson();
	}

	public ScanResult scanFile(InputStream inputStream) throws IOException {
		ScanResult scanResult = new ScanResult();
		CloseableHttpClient httpClient = HttpClients.createDefault();

		try {
			HttpPost request = new HttpPost(URI_VT2_FILE_SCAN);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("file", inputStream);
			builder.addTextBody("apikey", apiKey);

			HttpEntity postEntity = builder.build();
			request.setEntity(postEntity);

			CloseableHttpResponse response = httpClient.execute(request);

			try {
				// Get HttpResponse Status
				// System.out.println(response.getProtocolVersion()); // HTTP/1.1
				// System.out.println(response.getStatusLine().getStatusCode()); // 200
				// System.out.println(response.getStatusLine().getReasonPhrase()); // OK
				// System.out.println(response.getStatusLine().toString()); // HTTP/1.1 200 OK

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					// return it as a String
					try (BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8))) {

						String serviceResponse = br.lines().collect(Collectors.joining());
						scanResult = gsonProcessor.fromJson(serviceResponse, ScanResult.class);
					}
				}

			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}
		return scanResult;
	}

	public FileScanReport getScanReport(String resource) throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		FileScanReport fileScanReport = new FileScanReport();

		try {
			HttpPost request = new HttpPost(URI_VT2_FILE_SCAN_REPORT);

			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addTextBody("resource", resource);
			builder.addTextBody("apikey", apiKey);

			HttpEntity postEntity = builder.build();
			request.setEntity(postEntity);

			CloseableHttpResponse response = httpClient.execute(request);

			try {
				// Get HttpResponse Status
				// System.out.println(response.getProtocolVersion()); // HTTP/1.1
				// System.out.println(response.getStatusLine().getStatusCode()); // 200
				// System.out.println(response.getStatusLine().getReasonPhrase()); // OK
				// System.out.println(response.getStatusLine().toString()); // HTTP/1.1 200 OK

				HttpEntity entity = response.getEntity();
				if (entity != null) {
					try (BufferedReader br = new BufferedReader(new InputStreamReader(entity.getContent(), StandardCharsets.UTF_8))) {
						String serviceResponse = br.lines().collect(Collectors.joining());

						fileScanReport = gsonProcessor.fromJson(serviceResponse, FileScanReport.class);
					}
				}

			} finally {
				response.close();
			}
		} finally {
			httpClient.close();
		}
		return fileScanReport;
	}

}
