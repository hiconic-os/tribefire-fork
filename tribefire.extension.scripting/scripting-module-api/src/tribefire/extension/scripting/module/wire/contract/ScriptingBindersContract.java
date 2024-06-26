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
package tribefire.extension.scripting.module.wire.contract;

import com.braintribe.model.processing.deployment.api.ComponentBinder;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.scripting.model.deployment.ScriptingEngine;

/**
 * Scripting module contract for connecting a {@link ScriptingEngine} denotation type to a an 
 * actual (API) expert {@link tribefire.extension.scripting.api.ScriptingEngine}. 
 * 
 * @author Dirk Scheffler
 *
 */
public interface ScriptingBindersContract extends WireSpace {

	ComponentBinder<ScriptingEngine, tribefire.extension.scripting.api.ScriptingEngine<?>> scriptingEngine();
}
