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
package tribefire.extension.scripting.groovy.initializer;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.meta.editor.ModelMetaDataEditor;
import com.braintribe.model.processing.session.api.collaboration.PersistenceInitializationContext;
import com.braintribe.wire.api.module.WireTerminalModule;

import tribefire.cortex.initializer.support.api.WiredInitializerContext;
import tribefire.cortex.initializer.support.impl.AbstractInitializer;
import tribefire.extension.scripting.deployment.model.GroovyScript;
import tribefire.extension.scripting.deployment.model.GroovyScriptingEngine;
import tribefire.extension.scripting.groovy.initializer.wire.GroovyInitializerWireModule;
import tribefire.extension.scripting.groovy.initializer.wire.contract.GroovyInitializerContract;
import tribefire.extension.scripting.groovy.initializer.wire.space.GroovyInitializerSpace;
import tribefire.extension.scripting.model.deployment.meta.EvaluateScriptWith;
import tribefire.module.wire.contract.ModelApiContract;

/**
 * The GroovyInitializer adds the {@link EvaluateScriptWith} type to the meta data of {@link GroovyScript}. 
 * The scripting engine {@link GroovyScriptingEngine} (wrapped into the {@link EvaluateScriptWith}) 
 * is obtained via {@link GroovyInitializerSpace}. 
 * 
 * @author Dirk Scheffler
 */
public class GroovyInitializer extends AbstractInitializer<GroovyInitializerContract> {

	private final ClassLoader classLoader;
	private final ModelApiContract modelApi;

	public GroovyInitializer(ClassLoader classLoader, ModelApiContract modelApi) {
		this.classLoader = classLoader;
		this.modelApi = modelApi;
	}

	@Override
	protected WireTerminalModule<GroovyInitializerContract> getInitializerWireModule() {
		return new GroovyInitializerWireModule(classLoader);
	}

	@Override
	protected void initialize(PersistenceInitializationContext context, WiredInitializerContext<GroovyInitializerContract> initializerContext,
			GroovyInitializerContract initializerContract) {

		GmMetaModel cortexConfigurationModel = initializerContract.cortexConfigurationModel();
		GmMetaModel groovyScriptingModel = initializerContract.groovyScriptingModel();
		cortexConfigurationModel.getDependencies().add(groovyScriptingModel);

		ModelMetaDataEditor modelEditor = modelApi.newMetaDataEditor(cortexConfigurationModel).done();

		modelEditor.onEntityType(GroovyScript.T).addMetaData(initializerContract.evaluateScriptWith());
	}
}
