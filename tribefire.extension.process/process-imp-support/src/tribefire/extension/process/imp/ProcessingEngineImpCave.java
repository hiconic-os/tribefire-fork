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
package tribefire.extension.process.imp;

import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.product.rat.imp.AbstractImpCave;

import tribefire.extension.process.model.deployment.ProcessingEngine;
import tribefire.extension.process.model.scripting.deployment.ScriptedConditionProcessor;
import tribefire.extension.process.model.scripting.deployment.ScriptedTransitionProcessor;
import tribefire.extension.scripting.imp.ScriptedProcessorImp;

/**
 * An {@link AbstractImpCave} specialized in {@link ProcessingEngine} related deployables.
 */
public class ProcessingEngineImpCave extends AbstractImpCave<ProcessingEngine, ProcessingEngineImp> {

	public ProcessingEngineImpCave(PersistenceGmSession session) {
		super(session, "externalId", ProcessingEngine.T);
	}

	public ProcessingEngineImp createProcessingEngine(String externalId) {
		ProcessingEngine processingEngine = session().create(ProcessingEngine.T);
		processingEngine.setExternalId(externalId);
		processingEngine.setName(externalId);

		return buildImp(processingEngine);
	}

	public ProcessDefinitionImpCave definition() {
		return new ProcessDefinitionImpCave(session());
	}

	@Override
	public ProcessingEngineImp buildImp(ProcessingEngine instance) {
		return new ProcessingEngineImp(session(), instance);
	}

	public ScriptedProcessorImp<ScriptedTransitionProcessor> createScriptedTransitionProcessor(String name, String externalId) {
		ScriptedTransitionProcessor scriptedTransitionProcessor = session().create(ScriptedTransitionProcessor.T);
		scriptedTransitionProcessor.setName(name);
		scriptedTransitionProcessor.setExternalId(externalId);
		return new ScriptedProcessorImp<ScriptedTransitionProcessor>(session(), scriptedTransitionProcessor);
	}

	public ScriptedProcessorImp<ScriptedConditionProcessor> createScriptedCondition(String name, String externalId) {
		ScriptedConditionProcessor scriptedCondition = session().create(ScriptedConditionProcessor.T);
		scriptedCondition.setName(name);
		scriptedCondition.setExternalId(externalId);
		return new ScriptedProcessorImp<ScriptedConditionProcessor>(session(), scriptedCondition);
	}
}
