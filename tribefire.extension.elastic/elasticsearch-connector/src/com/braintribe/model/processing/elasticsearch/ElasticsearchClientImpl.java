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

import java.io.Closeable;
import java.net.InetAddress;
import java.net.UnknownHostException;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.ClusterAdminClient;
import org.elasticsearch.client.IndicesAdminClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import com.braintribe.logging.Logger;

public class ElasticsearchClientImpl implements ElasticsearchClient, Closeable {

	private final static Logger logger = Logger.getLogger(ElasticsearchClientImpl.class);

	protected Client client = null;
	protected ElasticsearchAddress address = null;

	protected IndicesAdminClient indicesAdminClient;
	protected ClusterAdminClient clusterAdminClient;

	public ElasticsearchClientImpl(ElasticsearchAddress address) {
		this.address = address;
	}

	@Override
	public void open() throws Exception {
		ClassLoader originalClassLoader = Thread.currentThread().getContextClassLoader();
		Thread.currentThread().setContextClassLoader(ElasticsearchClientImpl.class.getClassLoader());
		try {
			Settings.Builder settings = Settings.builder();

			settings.put("cluster.name", this.address.getClusterName());
			settings.put("client.transport.sniff", this.address.getClusterSniff());

			String nodeName = this.address.getNodeName();

			if (nodeName != null && nodeName.trim().length() > 0) {
				settings.put("node.name", nodeName);
			}

			Settings builtSettings = settings.build();
			String host = this.address.getHost();
			int port = this.address.getPort();

			if (host == null) {
				host = "127.0.0.1";
			}
			if (port == 0) {
				port = 9300;
			}

			try {
				PreBuiltTransportClient pbtc = new PreBuiltTransportClient(builtSettings);
				pbtc.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

				this.client = pbtc;

			} catch (UnknownHostException e) {
				throw new Exception("Could not connect to " + this.address + " with settings " + settings, e);
			}
		} finally {
			try {
				Thread.currentThread().setContextClassLoader(originalClassLoader);
			} catch (Exception e) {
				logger.debug(() -> "Could not set original thread classloader.", e);
			}
		}
	}

	@Override
	public IndicesAdminClient getIndicesAdminClient() {
		if (this.indicesAdminClient == null) {
			this.indicesAdminClient = client.admin().indices();
		}
		return this.indicesAdminClient;
	}

	@Override
	public ClusterAdminClient getClusterAdminClient() {
		if (this.clusterAdminClient == null) {
			this.clusterAdminClient = client.admin().cluster();
		}
		return this.clusterAdminClient;
	}

	@Override
	public Client elastic() {
		return this.client;
	}

	@Override
	public void close() {
		try {
			if (this.client != null) {
				this.client.close();
			}
		} catch (Exception e) {
			logger.error("Error while closing the connection to " + this.address, e);
		}
	}

	public ElasticsearchAddress getAddress() {
		return address;
	}

}
