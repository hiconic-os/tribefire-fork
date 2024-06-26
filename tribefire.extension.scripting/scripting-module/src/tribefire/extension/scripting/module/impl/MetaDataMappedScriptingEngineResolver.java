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
package tribefire.extension.scripting.module.impl;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.gm.model.reason.essential.NotFound;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.deployment.api.DeployRegistry;
import com.braintribe.model.processing.deployment.api.DeployedUnit;
import com.braintribe.model.processing.session.api.managed.ModelAccessory;

import tribefire.extension.scripting.api.ScriptingEngineResolver;
import tribefire.extension.scripting.model.deployment.Script;
import tribefire.extension.scripting.model.deployment.ScriptingEngine;
import tribefire.extension.scripting.model.deployment.meta.EvaluateScriptWith;

/**
 * Implementation of {@link ScriptingEngineResolver} with access to the cortex accessory. It will 
 * search the registered cortex deployables for a matching expert {@line tribefire.extension.scripting.api.ScriptingEngine}
 * given a specific {@link Script} type. 
 * 
 * @author Dirk Scheffler
 *
 */
public class MetaDataMappedScriptingEngineResolver implements ScriptingEngineResolver {

	private ModelAccessory cortexModelAccessory;
	// private DeployedComponentResolver deployedComponentResolver;
	private DeployRegistry deployRegistry;

	@Configurable
	@Required
	public void setCortexModelAccessory(ModelAccessory cortexModelAccessory) {
		this.cortexModelAccessory = cortexModelAccessory;
	}

	// TODO: ask Peter to expose this resolver in api module contracts. UPDATE: he added this. 
	// @Configurable @Required
	// public void setDeployedComponentResolver(DeployedComponentResolver deployedComponentResolver) {
	// this.deployedComponentResolver = deployedComponentResolver;
	// }

	@Configurable
	@Required
	public void setDeployRegistry(DeployRegistry deployRegistry) {
		this.deployRegistry = deployRegistry;
	}

	@Override
	public <S extends Script> Maybe<tribefire.extension.scripting.api.ScriptingEngine<S>> resolveEngine(EntityType<S> scriptType) {

		EvaluateScriptWith evaluateScriptWith = cortexModelAccessory.getCmdResolver().getMetaData().entityType(scriptType).meta(EvaluateScriptWith.T)
				.exclusive();

		if (evaluateScriptWith == null) {
			return Reasons.build(NotFound.T).text("Script type " + scriptType.getTypeSignature() + " is not mapped to a ScriptingEngine.").toMaybe();
		}

		ScriptingEngine engine = evaluateScriptWith.getEngine();

		// tribefire.extension.scripting.api.ScriptingEngine<S> expertProxy = deployedComponentResolver.resolve(engine, ScriptingEngine.T);

		DeployedUnit deployedUnit = deployRegistry.resolve(engine);

		if (deployedUnit == null)
			return Reasons.build(NotFound.T).text("ScriptEngine type " + engine + " is not deployed.").toMaybe();

		tribefire.extension.scripting.api.ScriptingEngine<S> expert = deployedUnit.findComponent(ScriptingEngine.T);

		if (expert == null)
			return Reasons.build(NotFound.T).text("ScriptEngine expert component for " + engine + " is missing.").toMaybe();

		return Maybe.complete(expert);
	}

}
