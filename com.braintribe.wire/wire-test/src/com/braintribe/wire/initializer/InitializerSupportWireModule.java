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
package com.braintribe.wire.initializer;

import java.util.Collections;
import java.util.List;

import com.braintribe.wire.api.context.WireContextBuilder;
import com.braintribe.wire.api.module.WireModule;
import com.braintribe.wire.api.module.WireTerminalModule;
import com.braintribe.wire.api.space.WireSpace;

public class InitializerSupportWireModule<S extends WireSpace> implements WireTerminalModule<S> {
	
	
	private WireTerminalModule<S> customModule;

	public InitializerSupportWireModule(WireTerminalModule<S> customModule) {
		this.customModule = customModule;
		
	}
	
	@Override
	public void configureContext(WireContextBuilder<?> contextBuilder) {
		
		// TODO: special initializer bindings
		// contextBuilder.bindContract(wireSpaceContract, wireSpaceImplementation)
		
		// TODO: see if you have some space packages to announce manually or automaticall via super method
		// WireTerminalModule.super.configureContext(contextBuilder);
	}
	
	@Override
	public List<WireModule> dependencies() {
		return Collections.singletonList(customModule);
	}

	@Override
	public Class<S> contract() {
		return customModule.contract();
	}

}
