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
package tribefire.extension.scripting.statechange_scripting.wire.space;

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.scripting.module.wire.contract.ScriptingContract;
import tribefire.extension.scripting.statechange.processing.ScriptedStateChangeProcessor;
import tribefire.module.api.InitializerBindingBuilder;
import tribefire.module.api.WireContractBindingBuilder;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefirePlatformContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class StateChangeScriptingModuleSpace implements TribefireModuleContract {

	// @Import
	// private TribefirePlatformContract tfPlatform;

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private ScriptingContract scripting;

	//
	// Deployables
	//

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(tribefire.extension.scripting.statechange.model.deployment.ScriptedStateChangeProcessor.T).component(tfPlatform.binders().stateChangeProcessor())
				.expertFactory(this::scriptedStateChangeProcessor);
	}

	@Managed
	private ScriptedStateChangeProcessor scriptedStateChangeProcessor(
			ExpertContext<tribefire.extension.scripting.statechange.model.deployment.ScriptedStateChangeProcessor> context) {
		ScriptedStateChangeProcessor bean = new ScriptedStateChangeProcessor();
		tribefire.extension.scripting.statechange.model.deployment.ScriptedStateChangeProcessor deployable = context.getDeployable();
		bean.setDeployable(deployable);
		bean.setAfterScript(deployable.getAfterScript());
		bean.setBeforeScript(deployable.getBeforeScript());
		bean.setProcessScript(deployable.getProcessScript());
		bean.setEngineResolver(scripting.scriptingEngineResolver());
		bean.setSystemSessionFactory(tfPlatform.systemUserRelated().sessionFactory()); // TODO missing in TribefirePlatformContract
		bean.setRequestSessionFactory(tfPlatform.requestUserRelated().sessionFactory()); // TODO missing in TribefirePlatformContract
		bean.setPropertyLookup(tfPlatform.platformReflection()::getProperty);
		return bean;
	}

}
