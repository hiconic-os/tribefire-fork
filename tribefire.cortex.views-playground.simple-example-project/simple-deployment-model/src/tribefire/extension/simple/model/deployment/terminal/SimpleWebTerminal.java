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
package tribefire.extension.simple.model.deployment.terminal;

import com.braintribe.model.extensiondeployment.WebTerminal;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * The <code>SimpleWebTerminal</code> is a simple servlet which processes {@code GET} requests and returns a basic HTML
 * page with information about the request.
 * <p>
 * This is the denotation type that represents the respective <code>SimpleWebTerminal</code> implementation. For more
 * information on denotation types see
 * {@link tribefire.extension.simple.model.deployment.access.SimpleInMemoryAccess}.
 *
 * @author michael.lafite
 */
public interface SimpleWebTerminal extends WebTerminal {

	EntityType<SimpleWebTerminal> T = EntityTypes.T(SimpleWebTerminal.class);

	/**
	 * Specifies whether or not to print out the HTTP request headers.
	 */
	boolean getPrintHeaders();
	void setPrintHeaders(boolean value);

	/**
	 * Specifies whether or not to print out the HTTP request parameters.
	 */
	boolean getPrintParameters();
	void setPrintParameters(boolean value);

}
