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
package tribefire.extension.scripting.api;

import java.util.Map;

import com.braintribe.gm.model.reason.Maybe;

import tribefire.extension.scripting.model.deployment.Script;

/**
 * 
 * Interface for script engines. A ScriptEngine provides an evaluate method to directly execute a Script, and a compile method to first compile a
 * Script.
 * 
 * @author Dirk Scheffler
 *
 * @param <S>
 *            Denotes the concrete script type derived from {@link Script}.
 */
public interface ScriptingEngine<S extends Script> {

	/**
	 * To evaluate a script given the parameters in bindings. 
	 * The method may return a {@linke ScriptingRuntimeError}.
	 * 
	 * @param <T>
	 *            Arbitrary return type, depending on the actual script to be executed. 
	 * @param script
	 *            Script data.
	 * @param bindings
	 *            Parameter bindings as map, which are passed as inputs to the script. 
	 *            
	 * @return A Reasoned return object, wiht a type that depends on the actual script object. 
	 */
	default <T> Maybe<T> evaluate(S script, Map<String, Object> bindings) {
		return compile(script).flatMap(compiledScript -> compiledScript.evaluate(bindings));
	}

	/**
	 * To compile a script. The method may return a {@link ScriptCompileError}.
	 * 
	 * @param script
	 *            Script data.
	 *            
	 * @return A reasoned {@link CompiledScript} object. 
	 */
	Maybe<CompiledScript> compile(S script);
}
