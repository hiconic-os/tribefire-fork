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
import java.util.function.Function;
import java.util.function.Supplier;

import com.braintribe.cfg.Required;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.gm.model.reason.Reasons;
import com.braintribe.model.deployment.Deployable;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSessionFactory;
import com.braintribe.utils.lcd.LazyInitialized;

import tribefire.extension.scripting.api.CompiledScript;
import tribefire.extension.scripting.api.ScriptingEngineResolver;
import tribefire.extension.scripting.model.deployment.Script;

@Abstract
public class AbstractScriptedProcessor {

	public interface ProcessableScript {
		<R> Maybe<R> processReasoned(Map<String, Object> bindings);
	}

	private ScriptingEngineResolver engineResolver;
	private Function<String, Object> propertyLookup = System::getProperty;
	private PersistenceGmSessionFactory requestSessionFactory;
	private PersistenceGmSessionFactory systemSessionFactory;
	private Deployable deployable;

	public void setPropertyLookup(Function<String, Object> propertyLookup) {
		this.propertyLookup = propertyLookup;
	}

	@Required
	public void setRequestSessionFactory(PersistenceGmSessionFactory requestSessionFactory) {
		this.requestSessionFactory = requestSessionFactory;
	}

	@Required
	public void setSystemSessionFactory(PersistenceGmSessionFactory systemSessionFactory) {
		this.systemSessionFactory = systemSessionFactory;
	}

	@Required
	public void setDeployable(Deployable deployable) {
		this.deployable = deployable;
	}

	@Required
	public void setEngineResolver(ScriptingEngineResolver engineResolver) {
		this.engineResolver = engineResolver;
	}

	/**
	 * TODO update documentation
	 * 
	 * The central common algorithm for scripted service processing. The input data passed to the script are based on "bindings", and are internally
	 * further enhanced with more common data. Scripts are pre-compiled once before they are evaluated (multiple times).
	 * 
	 * @param <R>
	 *            Return object.
	 * @param bindings
	 *            Must be mutable and will be further modified internally.
	 * @return A reasoned return object.
	 */

	public ProcessableScript getProcessableScript(Script script) {

		return new ProcessableScriptImpl(script);
	}

	private class ProcessableScriptImpl implements ProcessableScript {
		private LazyInitialized<Maybe<CompiledScript>> lazyCompiledScript;
		private Script script;
		private ScriptTools scriptTools;
		private String loggerContext;

		ProcessableScriptImpl(Script script) {
			this.script = script;
			this.lazyCompiledScript = new LazyInitialized<>(() -> engineResolver.compile(script));
			this.loggerContext = deployable.entityType().getTypeSignature() + "[externalId=" + deployable.getExternalId() + "]";
			this.scriptTools = new ScriptTools(loggerContext, propertyLookup, requestSessionFactory, systemSessionFactory, script, deployable);
		}

		@Override
		public <R> Maybe<R> processReasoned(Map<String, Object> bindings) {
			bindings.put("$tools", scriptTools);

			Maybe<CompiledScript> compiledScriptMaybe = lazyCompiledScript.get();

			if (compiledScriptMaybe.isUnsatisfied()) {
				return Reasons.build(com.braintribe.gm.model.reason.essential.InternalError.T).text("Script compilation failure.")
						.cause(compiledScriptMaybe.whyUnsatisfied()).toMaybe();
			}

			scriptTools.before();
			try {
				Maybe<R> result = compiledScriptMaybe.get().evaluate(bindings);
				return result;
			} finally {
				scriptTools.after();
			}
		}

	}

}
