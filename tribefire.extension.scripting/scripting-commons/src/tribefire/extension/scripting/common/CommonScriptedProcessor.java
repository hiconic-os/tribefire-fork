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
package tribefire.extension.scripting.common;

import java.util.Map;

import com.braintribe.cfg.Required;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.utils.lcd.LazyInitialized;

import tribefire.extension.scripting.api.CompiledScript;
import tribefire.extension.scripting.api.ScriptingEngineResolver;
import tribefire.extension.scripting.model.deployment.Script;

/**
 * Common base class for scripted service processors like {@link ScriptedServiceProcessor}. The common class hold the {@link ScriptEngineResolver},
 * the {@link Script} and a singleton version of the {@link CompiledScript}. Most importantly it offers the central common processReasonedScripted
 * method.
 * 
 *
 */
public class CommonScriptedProcessor extends AbstractScriptedProcessor {

	private Script script;
	private LazyInitialized<ProcessableScript> processableScript = new LazyInitialized<AbstractScriptedProcessor.ProcessableScript>(
			() -> getProcessableScript(script));

	@Required
	public void setScript(Script script) {
		this.script = script;
	}

	public Script getScript() {
		return this.script;
	}

	/**
	 * The central common algorithm for scripted service processing. The input data passed to the script are based on "bindings", and are internally
	 * further enhanced with more common data. Scripts are pre-compiled once before they are evaluated (multiple times).
	 * 
	 * @param <R>
	 *            Return object.
	 * @param bindings
	 *            Must be mutable and will be further modified internally.
	 * @return A reasoned return object.
	 */
	protected <R> Maybe<R> processReasonedScripted(Map<String, Object> bindings) {

		return processableScript.get().processReasoned(bindings);
	}

}
