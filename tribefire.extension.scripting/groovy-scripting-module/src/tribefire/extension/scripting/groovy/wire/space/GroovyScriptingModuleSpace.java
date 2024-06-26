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
package tribefire.extension.scripting.groovy.wire.space;

import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.context.WireContext;

import tribefire.extension.scripting.deployment.model.GroovyScriptingEngine;
import tribefire.extension.scripting.groovy.GroovyEngine;
import tribefire.extension.scripting.groovy.initializer.GroovyInitializer;
import tribefire.extension.scripting.module.wire.contract.ScriptingBindersContract;
import tribefire.module.api.InitializerBindingBuilder;
import tribefire.module.wire.contract.HardwiredDeployablesContract;
import tribefire.module.wire.contract.ModelApiContract;
import tribefire.module.wire.contract.ModuleReflectionContract;
import tribefire.module.wire.contract.SystemUserRelatedContract;
import tribefire.module.wire.contract.TribefireModuleContract;

/**
 * Wiring of the Groovy scripting engine. Provides the static instance of {@link GroovyEngine} deployable and binds it to the
 * {@link GroovyScriptingEngine} denotation type.
 * 
 * @author Dirk Scheffler
 */
@Managed
public class GroovyScriptingModuleSpace implements TribefireModuleContract {

	@Import
	private ModuleReflectionContract moduleReflection;

	@Import
	protected ScriptingBindersContract scriptingBinding;

	@Import
	private HardwiredDeployablesContract hardwiredDeployables;

	@Import
	private SystemUserRelatedContract systemUserRelated;

	@Import
	private ModelApiContract modelApi;

	@Import
	private WireContext<?> wireContext;

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(GroovyScriptingEngine.T).component(scriptingBinding.scriptingEngine()).expertSupplier(this::groovy);
	}

	/** @return Static instance of a {@link GroovyEngine}. */
	@Managed
	private GroovyEngine groovy() {
		GroovyEngine bean = new GroovyEngine();
		bean.setCortexSession(systemUserRelated.cortexSessionSupplier());
		return bean;
	}

	@Override
	public void bindInitializers(InitializerBindingBuilder bindings) {
		bindings.bind(this::initializeCortex);
	}

	private void initializeCortex(PersistenceInitializationContext context) {
		new GroovyInitializer(moduleReflection.moduleClassLoader(), modelApi).initialize(context, wireContext);
	}

}
