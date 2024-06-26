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
package com.braintribe.model.processing.elasticsearch;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

import com.braintribe.logging.Logger;
import com.braintribe.utils.IOTools;

public class ClientRegistry {

	private final static Logger logger = Logger.getLogger(ClientRegistry.class);

	private ReentrantLock lock = new ReentrantLock();

	private Map<String, ClientEntry> clients = new HashMap<>();

	public ElasticsearchClientImpl acquire(String clusterName, String host, int port, String nodeName, boolean clusterSniff) {

		String key = computeKey(clusterName, host, port);

		lock.lock();
		try {

			ClientEntry entry = clients.computeIfAbsent(key, k -> {

				ClientEntry newEntry = new ClientEntry();

				newEntry.address = new ElasticsearchAddress(clusterName, host, port, nodeName, clusterSniff);
				newEntry.client = new ElasticsearchClientImpl(newEntry.address);
				newEntry.referenceCount = 0;

				try {
					newEntry.client.open();
				} catch (Exception e) {
					throw new RuntimeException("Could not connect to " + newEntry.address, e);
				}

				return newEntry;
			});

			entry.referenceCount++;

			return entry.client;

		} finally {
			lock.unlock();
		}

	}

	public void close(ElasticsearchClientImpl client) {

		if (client == null) {
			return;
		}

		ElasticsearchAddress address = client.getAddress();

		String clusterName = address.getClusterName();
		String host = address.getHost();
		int port = address.getPort();

		String key = computeKey(clusterName, host, port);

		lock.lock();
		try {
			ClientEntry entry = clients.get(key);
			if (entry != null) {

				entry.referenceCount--;

				if (entry.referenceCount <= 0) {
					try {
						IOTools.closeCloseable(entry.client, logger);
					} finally {
						clients.remove(key);
					}
				}
			}
		} finally {
			lock.unlock();
		}
	}

	private class ClientEntry {
		ElasticsearchClientImpl client;
		ElasticsearchAddress address;
		int referenceCount;
	}

	private static String computeKey(String clusterName, String host, int port) {
		String key = "".concat(clusterName).concat("@").concat(host).concat(":").concat("" + port);
		return key;
	}
}
