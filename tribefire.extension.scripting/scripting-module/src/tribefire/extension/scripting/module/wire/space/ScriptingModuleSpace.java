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
package tribefire.extension.scripting.module.wire.space;

import com.braintribe.model.processing.deployment.api.ComponentBinder;
import com.braintribe.model.processing.deployment.api.ComponentBinders;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.scripting.api.ScriptingEngine;
import tribefire.extension.scripting.model.deployment.Script;
import tribefire.extension.scripting.module.impl.MetaDataMappedScriptingEngineResolver;
import tribefire.extension.scripting.module.wire.contract.ScriptingBindersContract;
import tribefire.extension.scripting.module.wire.contract.ScriptingContract;
import tribefire.module.api.WireContractBindingBuilder;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * The scripting extension provides access to {@link Script}s linked to {@link ScriptingEngine}s 
 * based on their type. Both, script resources as well as script engine experts must be deployed 
 * separately in cortex.   
 * The {@link MetaDataMappedScriptingEngineResolver} provides the option to obtain a concrete
 * {@link tribefire.extension.scripting.api.ScriptingEngine} expert given a specific script type. 
 * 
 * @author Dirk Scheffler
 */
@Managed
public class ScriptingModuleSpace implements TribefireModuleContract, ScriptingBindersContract, ScriptingContract {

	@Import
	private TribefireWebPlatformContract tribefireWebPlatform;

	//
	// WireContracts
	//

	@Override
	public void bindWireContracts(WireContractBindingBuilder bindings) {
		bindings.bind(ScriptingBindersContract.class, this);
		bindings.bind(ScriptingContract.class, this);
	}

	@Override
	public ComponentBinder<tribefire.extension.scripting.model.deployment.ScriptingEngine, ScriptingEngine<?>> scriptingEngine() {
		return ComponentBinders.binder(tribefire.extension.scripting.model.deployment.ScriptingEngine.T,
				ScriptingEngine.class);
	}

	@Managed
	@Override
	public MetaDataMappedScriptingEngineResolver scriptingEngineResolver() {
		MetaDataMappedScriptingEngineResolver bean = new MetaDataMappedScriptingEngineResolver();
		//bean.setDeployedComponentResolver(tribefireWebPlatform.deployment().);
		bean.setDeployRegistry(tribefireWebPlatform.deployment().deployRegistry());
		bean.setCortexModelAccessory(tribefireWebPlatform.systemUserRelated().cortexModelAccessorySupplier().get());
		return bean;
	}
}
