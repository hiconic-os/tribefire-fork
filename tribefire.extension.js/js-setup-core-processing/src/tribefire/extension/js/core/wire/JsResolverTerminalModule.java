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
package tribefire.extension.js.core.wire;

import java.util.List;

import com.braintribe.build.artifacts.mc.wire.buildwalk.BuildDependencyResolverWireModule;
import com.braintribe.build.artifacts.mc.wire.buildwalk.contract.FilterConfigurationContract;
import com.braintribe.build.artifacts.mc.wire.buildwalk.contract.GeneralConfigurationContract;
import com.braintribe.build.artifacts.mc.wire.buildwalk.contract.IntransitiveResolutionContract;
import com.braintribe.wire.api.context.WireContextBuilder;
import com.braintribe.wire.api.module.WireModule;
import com.braintribe.wire.api.module.WireTerminalModule;
import com.braintribe.wire.api.util.Lists;

import tribefire.extension.js.core.wire.contract.JsResolverConfigurationContract;
import tribefire.extension.js.core.wire.contract.JsResolverContract;
import tribefire.extension.js.core.wire.space.JsFilterConfigurationSpace;
import tribefire.extension.js.core.wire.space.JsGeneralConfigurationSpace;
import tribefire.extension.js.core.wire.space.JsIntransitiveResolutionSpace;
import tribefire.extension.js.core.wire.space.JsResolverConfigurationSpace;

/**
 * the {@link WireTerminalModule} for the {@link JsResolverContract}
 * @author pit
 *
 */
public class JsResolverTerminalModule implements WireTerminalModule<JsResolverContract> {

	private JsResolverConfigurationSpace configuration;

	public JsResolverTerminalModule(JsResolverConfigurationSpace configuration) {
		this.configuration = configuration;	
	}
	
	@Override
	public List<WireModule> dependencies() {
		return Lists.list( BuildDependencyResolverWireModule.DEFAULT);											
	}

	@Override
	public void configureContext(WireContextBuilder<?> contextBuilder) {
		WireTerminalModule.super.configureContext(contextBuilder);
		contextBuilder
		// overload ve
		.bindContract(GeneralConfigurationContract.class, JsGeneralConfigurationSpace.class)
		// inject part tuples
		.bindContract(FilterConfigurationContract.class, JsFilterConfigurationSpace.class)
		// overload the standard dependency resolver 
		.bindContract(IntransitiveResolutionContract.class, JsIntransitiveResolutionSpace.class)
		// inject the configuration
		.bindContract( JsResolverConfigurationContract.class, configuration)		
		
		.build();
	}

	
	
	
}
