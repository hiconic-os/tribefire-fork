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

import java.util.function.Consumer;

import com.braintribe.utils.collection.impl.AttributeContexts;
import com.braintribe.ve.api.ConfigurableVirtualEnvironment;
import com.braintribe.ve.api.VirtualEnvironment;
import com.braintribe.ve.api.VirtualEnvironmentAttribute;

/**
 * A {@link VirtualEnvironment} implementation that delegates access to the {@link VirtualEnvironment} retrieved with {@link VirtualEnvironmentAttribute} 
 * from the {@link AttributeContexts#peek() current AttributeContext}. If there is no such attribute the delegate will be {@link StandardEnvironment#INSTANCE} 
 * @author Dirk Scheffler
 *
 */
public class ContextualizedVirtualEnvironment implements VirtualEnvironment {
	public static VirtualEnvironment INSTANCE = new ContextualizedVirtualEnvironment();
	
	private ContextualizedVirtualEnvironment() {
		
	}

	/**
	 * This methods creates a new VirtualEnvironment that overrides the from {@link #environment() current environment} with an {@link OverridingEnvironment}.
	 * @param configurer A consumer that is called to configure the newly created {@link OverridingEnvironment} through the {@link ConfigurableVirtualEnvironment} interface before it is returned.
	 */
	public static VirtualEnvironment deriveEnvironment(Consumer<ConfigurableVirtualEnvironment> configurer) {
		ConfigurableVirtualEnvironment environment = new OverridingEnvironment(environment());
		configurer.accept(environment);
		return environment();
	}
	
	/**
	 * Returns the environment from the {@link AttributeContexts#peek() current AttributeContext} if present otherwise {@link StandardEnvironment#INSTANCE}
	 */
	public static VirtualEnvironment environment() {
		return AttributeContexts.peek().findAttribute(VirtualEnvironmentAttribute.class).orElse(StandardEnvironment.INSTANCE);
	}

	@Override
	public String getProperty(String name) {
		return environment().getProperty(name);
	}

	@Override
	public String getEnv(String name) {
		return environment().getEnv(name);
	}

}
