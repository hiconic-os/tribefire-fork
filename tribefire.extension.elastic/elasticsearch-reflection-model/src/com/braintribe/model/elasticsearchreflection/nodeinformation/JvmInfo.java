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

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface JvmInfo extends StandardIdentifiable {

	final EntityType<JvmInfo> T = EntityTypes.T(JvmInfo.class);

	String getBootClassPath();
	void setBootClassPath(String bootClassPath);

	String getClassPath();
	void setClassPath(String classPath);

	List<String> getInputArguments();
	void setInputArguments(List<String> inputArguments);

	Mem getMem();
	void setMem(Mem mem);

	Long getPid();
	void setPid(Long pid);

	Date getStartTime();
	void setStartTime(Date startTime);

	Map<String, String> getSystemProperties();
	void setSystemProperties(Map<String, String> systemProperties);

	String getVersion();
	void setVersion(String version);

	String getVmName();
	void setVmName(String vmName);

	String getVmVendor();
	void setVmVendor(String vmVendor);

	String getVmVersion();
	void setVmVersion(String vmVersion);

}
