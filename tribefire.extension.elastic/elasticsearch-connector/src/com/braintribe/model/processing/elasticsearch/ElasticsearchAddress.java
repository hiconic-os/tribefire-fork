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

import com.braintribe.logging.Logger;

public class ElasticsearchAddress {

	private final static Logger logger = Logger.getLogger(ElasticsearchAddress.class);

	protected String clusterName = null;
	protected String host = null;
	protected String nodeName = null;
	protected int port = 9300;
	protected boolean clusterSniff = true;

	public ElasticsearchAddress(String clusterName, String host, int port, String nodeName, boolean clusterSniff) {
		this.clusterName = clusterName;
		this.host = host;
		this.port = port;
		this.nodeName = nodeName;
		this.clusterSniff = clusterSniff;

		if (logger.isDebugEnabled()) {
			logger.debug("Initialized ElasticsearchAddress: " + this.toString());
		}
	}

	public String getClusterName() {
		return clusterName;
	}
	public String getHost() {
		return host;
	}
	public String getNodeName() {
		return nodeName;
	}
	public int getPort() {
		return port;
	}
	public boolean getClusterSniff() {
		return clusterSniff;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Cluster: ");
		sb.append(this.clusterName);
		sb.append(", Host: ");
		sb.append(this.host);
		sb.append(", Port: ");
		sb.append(this.port);
		return sb.toString();
	}
}
