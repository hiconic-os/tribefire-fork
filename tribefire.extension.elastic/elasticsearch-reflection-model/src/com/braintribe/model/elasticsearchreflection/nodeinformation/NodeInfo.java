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

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface NodeInfo extends StandardIdentifiable {

	final EntityType<NodeInfo> T = EntityTypes.T(NodeInfo.class);

	Set<String> getHeaders();
	void setHeaders(Set<String> header);

	String getHostName();
	void setHostName(String hostName);

	JvmInfo getJvmInfo();
	void setJvmInfo(JvmInfo jvmInfo);

	DiscoveryNode getNode();
	void setNode(DiscoveryNode node);

	OsInfo getOsInfo();
	void setOsInfo(OsInfo osInfo);

	PluginsAndModules getPluginsAndModules();
	void setPluginsAndModules(PluginsAndModules pluginsAndModules);

	ProcessInfo getProcessInfo();
	void setProcessInfo(ProcessInfo processInfo);

	Map<String, String> getServiceAttributes();
	void setServiceAttributes(Map<String, String> serviceAttributes);

	Map<String, String> getSettings();
	void setSettings(Map<String, String> settings);

	List<ThreadPoolInfo> getThreadPoolInfos();
	void setThreadPoolInfos(List<ThreadPoolInfo> threadPoolInfos);

	TransportInfo getTransport();
	void setTransport(TransportInfo transport);

	String getVersion();
	void setVersion(String version);

}
