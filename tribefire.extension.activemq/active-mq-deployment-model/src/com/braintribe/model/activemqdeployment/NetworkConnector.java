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
package com.braintribe.model.activemqdeployment;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface NetworkConnector extends StandardIdentifiable {

	final EntityType<NetworkConnector> T = EntityTypes.T(NetworkConnector.class);

	void setName(String name);
	String getName();
	
	void setHost(String host);
	String getHost();
	
	void setPort(Integer port);
	@Initializer("61616")
	Integer getPort();
	
	void setUseExponentialBackOff(Boolean useExponentialBackOff);
	@Initializer("false")
	Boolean getUseExponentialBackOff();
	
	void setInitialReconnectDelay(Long initialReconnectDelay);
	@Initializer("5000l")
	Long getInitialReconnectDelay();

	void setMaxReconnectDelay(Long maxReconnectDelay);
	@Initializer("5000l")
	Long getMaxReconnectDelay();

	void setDuplex(Boolean duplex);
	@Initializer("true")
	Boolean getDuplex();
	
	void setConduitSubscriptions(Boolean conduitSubscriptions);
	@Initializer("true")
	Boolean getConduitSubscriptions();
	
}
