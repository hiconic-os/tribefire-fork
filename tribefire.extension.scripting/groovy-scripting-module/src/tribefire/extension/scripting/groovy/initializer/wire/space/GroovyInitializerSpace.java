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
package tribefire.extension.scripting.groovy.initializer.wire.space;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.cortex.initializer.support.integrity.wire.contract.CoreInstancesContract;
import tribefire.cortex.initializer.support.wire.space.AbstractInitializerSpace;
import tribefire.extension.scripting.deployment.model.GroovyScriptingEngine;
import tribefire.extension.scripting.groovy.initializer.wire.contract.ExistingInstancesContract;
import tribefire.extension.scripting.groovy.initializer.wire.contract.GroovyInitializerContract;
import tribefire.extension.scripting.model.deployment.meta.EvaluateScriptWith;

/**
 * Wiring of the {@link GroovyInitializerContract} to the {@link ExistingInstancesContract}. This
 * provides an instance of the {@link GroovyScriptingEngine} wrapped into an {@link EvaluateScriptWith} object.
 * 
 */
@Managed
public class GroovyInitializerSpace extends AbstractInitializerSpace implements GroovyInitializerContract {

	@Import
	private CoreInstancesContract coreInstances;
	
	@Import
	private ExistingInstancesContract existingInstances;

	@Override
	public GmMetaModel cortexConfigurationModel() {
		return coreInstances.cortexModel();
	}
	
	@Override
	public GmMetaModel groovyScriptingModel() {
		return existingInstances.groovyScriptingModel();
	}
	
	@Override
	@Managed
	public EvaluateScriptWith evaluateScriptWith() {
		EvaluateScriptWith bean = create(EvaluateScriptWith.T);
		bean.setEngine(groovyScriptingEngine());
		return bean;
	}

	@Managed
	private GroovyScriptingEngine groovyScriptingEngine() {
		GroovyScriptingEngine bean = create(GroovyScriptingEngine.T);
		bean.setExternalId("script-engine-groovy");
		return bean;
	}
}
