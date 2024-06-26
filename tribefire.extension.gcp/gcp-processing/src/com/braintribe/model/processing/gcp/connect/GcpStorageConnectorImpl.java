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
package com.braintribe.model.processing.gcp.connect;

import java.io.ByteArrayInputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Collections;
import java.util.Map;

import org.apache.commons.collections4.map.LRUMap;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.exception.Exceptions;
import com.braintribe.logging.Logger;
import com.braintribe.model.processing.gcp.connect.impl.GcpStorageImpl;
import com.braintribe.utils.StringTools;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

public class GcpStorageConnectorImpl implements GcpStorageConnector {

	private final static Logger logger = Logger.getLogger(GcpStorageConnectorImpl.class);

	private com.braintribe.model.gcp.deployment.GcpConnector connector;

	private GcpStorage storage = null;

	private Map<String,GcpBucket> ensuredBucketNames = Collections.synchronizedMap(new LRUMap<>(256));

	@Override
	public GcpStorage getStorage() {
		if (storage == null) {
			synchronized(this) {

				if (storage == null) {

					try {

						if (connector == null) {
							throw new IllegalStateException("The connector is not set.");
						}

						ServiceAccountCredentials credentials = null;
						String jsonCredentials = connector.getJsonCredentials();
						if (!StringTools.isBlank(jsonCredentials)) {

							credentials = ServiceAccountCredentials.fromStream(new ByteArrayInputStream(jsonCredentials.getBytes(StandardCharsets.UTF_8)));

						} else {

							String encodedKey = connector.getPrivateKey();
							encodedKey = encodedKey.replace("-----BEGIN PRIVATE KEY-----", "");
							encodedKey = encodedKey.replace("-----END PRIVATE KEY-----", "");
							encodedKey = encodedKey.replaceAll("\\s+","");
							byte [] pkcs8EncodedBytes = Base64.getDecoder().decode(encodedKey);
							PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
							KeyFactory kf = KeyFactory.getInstance("RSA");
							PrivateKey privKey = kf.generatePrivate(keySpec);

							credentials = ServiceAccountCredentials.newBuilder()
									.setPrivateKeyId(connector.getPrivateKey())
									.setPrivateKey(privKey)
									.setClientEmail(connector.getClientEmail())
									.setClientId(connector.getClientId())
									.setTokenServerUri(new URI(connector.getTokenServerUri()))
									.setProjectId(connector.getProjectId())
									.build();

						}

						Storage nativeStorage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
						this.storage = new GcpStorageImpl(nativeStorage);
						

					} catch(Exception e) {
						throw Exceptions.unchecked(e, "Could not create the Storage connector.");
					}
				}
			}
		}
		return storage;
	}

	@Configurable
	@Required
	public void setConnector(com.braintribe.model.gcp.deployment.GcpConnector connector) {
		this.connector = connector;
	}

	@Override
	public GcpBucket ensureBucket(String bucketName) {

		if (bucketName == null) {
			throw new IllegalArgumentException("The bucket name must not be null.");
		}

		return ensuredBucketNames.computeIfAbsent(bucketName, bn -> {

			GcpStorage storage = getStorage();
			return storage.getOrCreate(bucketName);

		});
	}

	public boolean deleteBucket(String bucketName) {

		if (bucketName == null) {
			throw new IllegalArgumentException("The bucket name must not be null.");
		}

		GcpStorage storage = getStorage();
		return storage.deleteBucket(bucketName);

	}
}
