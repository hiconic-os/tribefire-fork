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
package com.braintribe.model.elasticsearchdeployment;

import com.braintribe.model.deployment.connector.Connector;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.DeployableComponent;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@DeployableComponent
public interface ElasticsearchConnector extends Connector {

	final EntityType<ElasticsearchConnector> T = EntityTypes.T(ElasticsearchConnector.class);

	void setClusterName(String clusterName);
	@Initializer("'elasticsearch.cartridge'")
	String getClusterName();

	void setHost(String host);
	@Initializer("'127.0.0.1'")
	String getHost();

	void setPort(int port);
	@Initializer("9300")
	int getPort();

	void setNodeName(String nodeName);
	String getNodeName();

	void setClusterSniff(boolean clusterSniff);
	@Initializer("false")
	@Mandatory
	boolean getClusterSniff();

}
