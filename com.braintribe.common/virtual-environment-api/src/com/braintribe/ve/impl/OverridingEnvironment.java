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
package com.braintribe.ve.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.braintribe.ve.api.ConfigurableVirtualEnvironment;
import com.braintribe.ve.api.VirtualEnvironment;

/**
 * This {@link VirtualEnvironment} implementation allows to store system properties and environment variables to overriding the ones that are inherited from {@link #getParent()}.
 * 
 * @author Dirk Scheffler
 *
 *
 * added a feature to block properties / env variables to null-out properties/variables of the underlying parent: pass null as value
 *  
 * 
 * @author pit 
 */
public class OverridingEnvironment implements ConfigurableVirtualEnvironment {
	private final VirtualEnvironment parent;
	private static final String nulled = new String("<nulled>");
	private final Map<String, String> properties = new ConcurrentHashMap<>();
	private final Map<String, String> envVariables = new ConcurrentHashMap<>();
	
	/**
	 * Constructs a new {@link OverridingEnvironment}
	 * @param parent the parent {@link VirtualEnvironment} from which it will derive all values that are not overridden
	 */
	public OverridingEnvironment(VirtualEnvironment parent) {
		this.parent = parent;
	}

	/**
	 * Returns the parent {@link VirtualEnvironment} from which system properties and environment variables are derived if not being overridden by this instance
	 */
	public VirtualEnvironment getParent() {
		return parent;
	}
	
	@Override
	public String getProperty(String name) { 
		// filter property 
		String value = properties.computeIfAbsent(name, parent::getProperty);
		if (value == nulled) {
			return null;
		}
		return value;
	}

	@Override
	public String getEnv(String name) {
		// filer env
		String value = envVariables.computeIfAbsent(name, parent::getEnv);
		if (value == nulled) {
			return null;
		}
		return value;
	}
	
	@Override
	public void setProperty(String name, String value) {
		if (value == null) {
			properties.put( name, nulled);
		}
		else {
			properties.put(name, value);
		}
	}
	
	
	@Override
	public void setEnv(String name, String value) {
		if (value == null) {
			envVariables.put(name, nulled);
		}
		else {
			envVariables.put(name, value);
		}
	}
		
	@Override
	public void setProperties(Map<String, String> properties) {
		this.properties.putAll(properties);
	}
	
	@Override
	public void setEnvs(Map<String, String> env) {
		this.envVariables.putAll(env);
	}

	@Override
	public Map<String, String> getEnvironmentOverrrides() {		
		return envVariables;
	}

	@Override
	public Map<String, String> getPropertyOverrrides() {
		return properties;
	}
	
	
}
		