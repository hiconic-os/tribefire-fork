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

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.LifecycleAware;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;

/**
 * This connector creates a connection to elastic, based on the provided credentials.
 *
 */
public class ElasticsearchConnectorImpl implements ElasticsearchConnector, LifecycleAware {

	private final static Logger logger = Logger.getLogger(ElasticsearchConnectorImpl.class);

	protected ElasticsearchAddress address;
	protected ElasticsearchClientImpl client;

	protected String clusterName = "elasticsearch.cartridge";
	protected String host = "127.0.0.1";
	protected int port = 9300;
	protected String nodeName = null;
	protected boolean clusterSniff = false;

	private ClientRegistry clientRegistry;

	@Override
	public void postConstruct() {
		if (this.getClient() != null) {

			if (logger.isDebugEnabled()) {
				logger.debug(ElasticsearchConnectorImpl.class.getSimpleName() + " already set up.");
			}

			return;
		}

		ElasticsearchClientImpl clientImpl = clientRegistry.acquire(this.clusterName, this.host, this.port, this.nodeName, this.clusterSniff);
		this.client = clientImpl;
		this.address = clientImpl.getAddress();

		logger.info("Initialized " + ElasticsearchConnectorImpl.class.getSimpleName() + ".");
	}

	@Override
	public void preDestroy() {
		this.clientRegistry.close(this.client);

		logger.info("ElasticsearchClient connection on " + ElasticsearchConnectorImpl.class.getSimpleName() + "closed.");
	}

	@Configurable
	public void setClusterName(String clusterName) {
		this.clusterName = clusterName;
	}

	@Configurable
	public void setHost(String host) {
		if (host != null && host.trim().length() > 0) {
			this.host = host;
		}
	}

	@Configurable
	public void setPort(Integer port) {
		if (port != null) {
			this.port = port.intValue();
		}
	}

	@Configurable
	public void setClusterSniff(Boolean clusterSniff) {
		if (clusterSniff != null) {
			this.clusterSniff = clusterSniff.booleanValue();
		}
	}

	@Configurable
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public ElasticsearchAddress getAddress() {
		return address;
	}

	public void setAddress(ElasticsearchAddress address) {
		this.address = address;
	}

	@Override
	public ElasticsearchClient getClient() {
		return client;
	}

	@Required
	@Configurable
	public void setClientRegistry(ClientRegistry clientRegistry) {
		this.clientRegistry = clientRegistry;
	}

}
