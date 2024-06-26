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

import java.util.HashMap;
import java.util.Map;

import com.braintribe.utils.collection.impl.AttributeContexts;
import com.braintribe.ve.impl.ContextualizedVirtualEnvironment;
import com.braintribe.ve.impl.OverridingEnvironment;
import com.braintribe.ve.impl.StandardEnvironment;

/**
 * <p>
 * VirtualEnvironment allows to access {@link System#getProperty(String) system properties} and {@link System#getenv() environment variables} via this interface instead of direct access
 * to static methods offered by the JDK. This is neccessary to make the properties and environment variable more agile and different in various parts of a running program.
 * 
 * <p>
 * The following fundamental implementations offer important features that found on the virtualization of the primary static access
 * 
 * <ul>
 * 	<li>direct access to the static methods: {@link StandardEnvironment}
 *  <li>overriding a parent virtual environment: {@link OverridingEnvironment}
 *  <li>accessibility via virtual environment via {@link AttributeContexts} and the type safe attribute {@link VirtualEnvironmentAttribute} and the convenience proxy {@link ContextualizedVirtualEnvironment}
 * </ul>
 * @author Dirk Scheffler
 *
 */
public interface VirtualEnvironment {
	/**
	 * Accesses a virtualized system property by its name
	 * @param name the name of the system property
	 * @return the value of the system property
	 */
	String getProperty(String name);
	
	/**
	 * Accesses a virtualized environment variable by its name
	 * @param name the name of the environment variable
	 * @return the value of the environment variable
	 */
	String getEnv(String name);
	
	
	/**
	 * @return - a {@link Map} of all currently overriding environment variables and their values
	 */
	default Map<String,String> getEnvironmentOverrrides() { return new HashMap<>();}
	
	/**
	 * @return - a {@link Map} of all currently overriding system properties and their values
	 */
	default Map<String,String> getPropertyOverrrides() { return new HashMap<>();}
}
