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

import com.braintribe.provider.Holder;
import com.google.gwt.core.client.GWT;

public class ModuleContextNameProvider extends Holder<String> {
	
	private static final ModuleContextNameProvider instance = new ModuleContextNameProvider();
	
	public static ModuleContextNameProvider getInstance() {
		return instance;
	}
	
	public ModuleContextNameProvider() {
		String baseUrl = GWT.getModuleBaseURL();
		if (baseUrl.endsWith("/"))
			baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
		
		String tokens[] = baseUrl.split("/");
		
		String contextName = tokens[tokens.length - 2];
		accept(contextName);
	}
}
