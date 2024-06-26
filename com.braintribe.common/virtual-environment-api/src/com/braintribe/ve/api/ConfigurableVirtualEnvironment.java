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
package com.braintribe.ve.api;

import java.util.Map;

/**
 * An interface that allows to configure a {@link VirtualEnvironment}
 * @author Dirk Scheffler
 */
public interface ConfigurableVirtualEnvironment extends VirtualEnvironment {

	/**
	 * Configures a single virtual environment variable.
	 * @param name the name of the environment variable
	 * @param value the value of the environment variable
	 */
	void setEnv(String name, String value);

	/**
	 * Configures a number of virtual environment variables
	 * @param env a map mapping from name to value of the environment variables
	 */
	void setEnvs(Map<String, String> env);

	
	/**
	 * Configures a single virtual system property.
	 * @param name the name of the system property
	 * @param value the value of the system property
	 */
	void setProperty(String name, String value);

	/**
	 * Configures a number of virtual system properties
	 * @param env a map mapping from name to value of the system properties
	 */
	void setProperties(Map<String, String> properties);
}
