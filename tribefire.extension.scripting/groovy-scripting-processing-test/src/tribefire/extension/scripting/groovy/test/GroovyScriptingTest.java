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
package tribefire.extension.scripting.groovy.test;

import java.io.FileInputStream;
import java.util.Collections;
import java.util.Map;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import com.braintribe.common.uncheckedcounterpartexceptions.FileNotFoundException;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.model.resource.Resource;

import tribefire.extension.scripting.deployment.model.GroovyScript;
import tribefire.extension.scripting.groovy.GroovyEngine;
import tribefire.extension.scripting.model.ScriptCompileError;
import tribefire.extension.scripting.model.ScriptRuntimeError;

public class GroovyScriptingTest {

	@Test
	public void testGroovy() {
		Maybe<String> resultMaybe = evaluate("test.groovy", Collections.singletonMap("param", "FoOBAR"));

		if (resultMaybe.isUnsatisfied())
			Assertions.fail("unexpected evaluation problem with reason: " + resultMaybe.whyUnsatisfied().asFormattedText());

		String result = resultMaybe.get();

		Assertions.assertThat(result).withFailMessage("Script did not return expected value").isEqualTo("foobar");
	}

	@Test
	public void testBrokenSyntaxGroovy() {
		Maybe<String> resultMaybe = evaluate("broken-syntax.groovy", Collections.singletonMap("param", "FoOBAR"));

		if (resultMaybe.isUnsatisfiedBy(ScriptCompileError.T))
			return;

		Assertions.fail("missing ScriptCompileError from compilation");
	}

	@Test
	public void testBrokenRuntimeGroovy() {
		Maybe<String> resultMaybe = evaluate("broken-runtime.groovy", Collections.singletonMap("param", "FoOBAR"));

		if (resultMaybe.isUnsatisfiedBy(ScriptRuntimeError.T))
			return;

		Assertions.fail("missing ScriptRuntimeError.");
	}

	private <R> Maybe<R> evaluate(String name, Map<String, Object> bindings) {
		GroovyEngine engine = new GroovyEngine();

		GroovyScript script = GroovyScript.T.create();
		script.setSource(buildScriptResource(name));

		return engine.evaluate(script, bindings);
	}

	private Resource buildScriptResource(String name) {
		return Resource.createTransient(() -> new FileInputStream("res/" + name));
	}
}
