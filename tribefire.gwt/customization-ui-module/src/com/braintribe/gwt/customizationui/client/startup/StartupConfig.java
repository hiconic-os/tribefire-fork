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
package com.braintribe.gwt.customizationui.client.startup;

import java.util.function.Supplier;

import com.braintribe.gwt.async.client.Loader;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;


public class StartupConfig {
	private Supplier<?> customizationProvider;
	private Supplier<? extends Loader<?>> preparingLoaderProvider;
	private Supplier<Boolean> canRunProvider;
	
	public Supplier<?> getCustomizationProvider() {
		return customizationProvider;
	}
	
	@Configurable
	public void setCanRunProvider(Supplier<Boolean> canRunProvider) {
		this.canRunProvider = canRunProvider;
	}
	
	@Configurable @Required
	public void setCustomizationProvider(Supplier<?> customizationProvider) {
		this.customizationProvider = customizationProvider;
	}
	
	@Configurable
	public void setPreparingLoaderProvider(Supplier<? extends Loader<?>> preparingLoaderProvider) {
		this.preparingLoaderProvider = preparingLoaderProvider;
	}

	public Supplier<? extends Loader<?>> getPreparingLoaderProvider() {
		return preparingLoaderProvider;
	}
	
	public boolean canRun() throws RuntimeException {
		if (canRunProvider != null)
			return canRunProvider.get();
		else
			return true;
	}
}
