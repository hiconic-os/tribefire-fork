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
package com.braintribe.model.elasticsearchreflection.nodeinformation;

import java.util.Map;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface DiscoveryNode extends StandardIdentifiable {

	final EntityType<DiscoveryNode> T = EntityTypes.T(DiscoveryNode.class);

	TransportAddress getAddress();
	void setAddress(TransportAddress address);

	Map<String, String> getAttributes();
	void setAttributes(Map<String, String> attributes);

	String getHostAddress();
	void setHostAddress(String hostAddress);

	String getHostName();
	void setHostName(String hostName);

	String getDiscoveryNodeId();
	void setDiscoveryNodeId(String discoveryNodeId);

	String getName();
	void setName(String name);

	String getVersion();
	void setVersion(String version);

}
