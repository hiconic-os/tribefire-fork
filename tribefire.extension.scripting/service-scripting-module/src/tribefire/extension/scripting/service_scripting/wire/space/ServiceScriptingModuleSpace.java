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
package tribefire.extension.scripting.service_scripting.wire.space;

import com.braintribe.model.deployment.Deployable;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.scripting.api.ScriptingEngine;
import tribefire.extension.scripting.api.ScriptingEngineResolver;
import tribefire.extension.scripting.common.CommonScriptedProcessor;
import tribefire.extension.scripting.model.deployment.Script;
import tribefire.extension.scripting.module.wire.contract.ScriptingContract;
import tribefire.extension.scripting.service.model.deployment.ScriptedCheckProcessor;
import tribefire.extension.scripting.service.model.deployment.ScriptedServiceAroundProcessor;
import tribefire.extension.scripting.service.model.deployment.ScriptedServicePostProcessor;
import tribefire.extension.scripting.service.model.deployment.ScriptedServicePreProcessor;
import tribefire.extension.scripting.service.model.deployment.ScriptedServiceProcessor;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * Scripted service module for scripted services ({@link ScriptingModuleSpace}) to bind cortex-deployable scripted service processors to available
 * expert implementations. Supported are
 * <ul>
 * <li>{@link ScriptedServiceProcessor}</li>
 * <li>{@link ScriptedServicePostProcessor}</li>
 * <li>{@link ScriptedServiceAroundProcessor}</li>
 * <li>{@link ScriptedServicePreProcessor}</li>
 * </ul>
 * Each experts has a {@link Script} and a {@link ScriptingEngineResolver}, which can be used to obtain a {@link ScriptingEngine} expert to evaluate
 * the script.
 * 
 * @author Dirk Scheffler
 */
@Managed
public class ServiceScriptingModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	@Import
	private ScriptingContract scripting;

	//
	// Deployables
	//

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(ScriptedServiceProcessor.T) //
				.component(tfPlatform.binders().serviceProcessor()) //
				.expertFactory(this::scriptedServiceProcessor);
		bindings.bind(ScriptedServicePreProcessor.T) //
				.component(tfPlatform.binders().servicePreProcessor()) //
				.expertFactory(this::scriptedServicePreProcessor);
		bindings.bind(ScriptedServicePostProcessor.T) //
				.component(tfPlatform.binders().servicePostProcessor()) //
				.expertFactory(this::scriptedServicePostProcessor);
		bindings.bind(ScriptedServiceAroundProcessor.T) //
				.component(tfPlatform.binders().serviceAroundProcessor()) //
				.expertFactory(this::scriptedServiceAroundProcessor);
		bindings.bind(ScriptedCheckProcessor.T) //
				.component(tfPlatform.binders().checkProcessor()) //
				.expertFactory(this::scriptedCheckProcessor);
	}

	private tribefire.extension.scripting.service.processing.ScriptedServiceProcessor scriptedServiceProcessor(
			ExpertContext<ScriptedServiceProcessor> context) {
		tribefire.extension.scripting.service.processing.ScriptedServiceProcessor bean = new tribefire.extension.scripting.service.processing.ScriptedServiceProcessor();
		ScriptedServiceProcessor deployable = context.getDeployable();
		bean.setScript(deployable.getScript());
		configureScriptedProcessor(deployable, bean);
		return bean;
	}

	private tribefire.extension.scripting.service.processing.ScriptedServicePreProcessor scriptedServicePreProcessor(
			ExpertContext<ScriptedServicePreProcessor> context) {
		tribefire.extension.scripting.service.processing.ScriptedServicePreProcessor bean = new tribefire.extension.scripting.service.processing.ScriptedServicePreProcessor();
		ScriptedServicePreProcessor deployable = context.getDeployable();
		bean.setScript(deployable.getScript());
		configureScriptedProcessor(deployable, bean);
		return bean;
	}

	private tribefire.extension.scripting.service.processing.ScriptedServicePostProcessor scriptedServicePostProcessor(
			ExpertContext<ScriptedServicePostProcessor> context) {
		tribefire.extension.scripting.service.processing.ScriptedServicePostProcessor bean = new tribefire.extension.scripting.service.processing.ScriptedServicePostProcessor();
		ScriptedServicePostProcessor deployable = context.getDeployable();
		bean.setScript(deployable.getScript());
		configureScriptedProcessor(deployable, bean);
		return bean;
	}

	private tribefire.extension.scripting.service.processing.ScriptedServiceAroundProcessor scriptedServiceAroundProcessor(
			ExpertContext<ScriptedServiceAroundProcessor> context) {
		tribefire.extension.scripting.service.processing.ScriptedServiceAroundProcessor bean = new tribefire.extension.scripting.service.processing.ScriptedServiceAroundProcessor();
		ScriptedServiceAroundProcessor deployable = context.getDeployable();
		bean.setScript(deployable.getScript());
		configureScriptedProcessor(deployable, bean);
		return bean;
	}

	private tribefire.extension.scripting.service.processing.ScriptedCheckProcessor scriptedCheckProcessor(
			ExpertContext<? extends ScriptedCheckProcessor> context) {
		tribefire.extension.scripting.service.processing.ScriptedCheckProcessor bean = new tribefire.extension.scripting.service.processing.ScriptedCheckProcessor();
		ScriptedCheckProcessor deployable = context.getDeployable();
		bean.setScript(deployable.getScript());
		configureScriptedProcessor(deployable, bean);
		return bean;
	}

	private void configureScriptedProcessor(Deployable deployable, CommonScriptedProcessor bean) {
		bean.setDeployable(deployable);
		bean.setSystemSessionFactory(tfPlatform.systemUserRelated().sessionFactory());
		bean.setRequestSessionFactory(tfPlatform.requestUserRelated().sessionFactory());
		bean.setEngineResolver(scripting.scriptingEngineResolver());
		bean.setPropertyLookup(tfPlatform.platformReflection()::getProperty);
	}

}
