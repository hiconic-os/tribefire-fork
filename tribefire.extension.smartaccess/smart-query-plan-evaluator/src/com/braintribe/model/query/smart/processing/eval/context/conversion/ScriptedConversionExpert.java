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
package com.braintribe.model.query.smart.processing.eval.context.conversion;

import static com.braintribe.utils.lcd.CollectionTools2.asMap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.braintribe.logging.Logger;
import com.braintribe.model.accessdeployment.smart.meta.conversion.ScriptedConversion;
import com.braintribe.model.processing.smartquery.eval.api.ConversionDirection;
import com.braintribe.model.processing.smartquery.eval.api.SmartConversionExpert;
import com.braintribe.model.query.smart.processing.SmartQueryEvaluatorRuntimeException;

import tribefire.extension.scripting.api.CompiledScript;
import tribefire.extension.scripting.api.ScriptingEngineResolver;
import tribefire.extension.scripting.model.deployment.Script;

/**
 * @author peter.gazdik
 */
public class ScriptedConversionExpert implements SmartConversionExpert<ScriptedConversion> {

	private ScriptingEngineResolver scriptingEngineResolver;

	private final ConcurrentMap<Script, CompiledScript> scriptCache = new ConcurrentHashMap<>();

	private static final Logger log = Logger.getLogger(ScriptedConversionExpert.class);

	public void setScriptingEngineResolver(ScriptingEngineResolver scriptingEngineResolver) {
		this.scriptingEngineResolver = scriptingEngineResolver;
	}

	@Override
	public Object convertValue(ScriptedConversion conversion, Object value, ConversionDirection direction) {
		Script script = conversion.getScript();

		log.pushContext(loggerContextFor(script));
		try {
			Map<String, Object> bindings = createBindings(value, direction, script);

			CompiledScript compiledScript = acquireCompiledScriptFor(script);

			return compiledScript.evaluate(bindings).get();

		} catch (Exception e) {
			throw new SmartQueryEvaluatorRuntimeException("Error while converting value: '" + value + "' with script: '" + script.getSource()
					+ "' of type '" + script.entityType().getTypeSignature() + "'", e);
		} finally {
			log.popContext();
		}

	}

	private String loggerContextFor(Script script) {
		return script.entityType().getTypeSignature() + "[id=" + script.getId() + "]";
	}

	private Map<String, Object> createBindings(Object value, ConversionDirection direction, Script script) {
		Map<Object, Object> tools = asMap("logger", log, "script", script);
		ScriptedConversionContext context = new ScriptedConversionContext(value, direction);

		return asMap("$tools", tools, "$context", context);
	}

	private CompiledScript acquireCompiledScriptFor(Script script) {
		CompiledScript result = scriptCache.get(script);
		if (result == null) {
			result = scriptingEngineResolver.compile(script).get();

			scriptCache.put(script, result); // we don't care if we overwrite an existing value
		}

		return result;
	}

	public static class ScriptedConversionContext {
		private final Object value;
		private final ConversionDirection direction;

		public ScriptedConversionContext(Object value, ConversionDirection direction) {
			this.value = value;
			this.direction = direction;
		}

		public boolean getIsForward() {
			return direction == ConversionDirection.smart2Delegate;
		}

		public Object getValue() {
			return value;
		}
	}

}
