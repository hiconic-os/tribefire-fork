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
package tribefire.extension.simple.model.deployment.service;

import com.braintribe.model.extensiondeployment.access.AccessRequestProcessor;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * The <code>SimpleEchoService</code> is a simple service that receives a message via a <code>SimpleEchoRequest</code> and echoes them by returning a
 * <code>SimpleEchoResponse</code>.<br>
 * This is just a very simple example for a (DDSA) service.
 * <p>
 * This is the denotation type that represents the respective <code>SimpleEchoService</code> implementation. For more information on denotation types
 * see {@link tribefire.extension.simple.model.deployment.access.SimpleInMemoryAccess}.
 *
 * @author michael.lafite
 */
public interface SimpleEchoService extends AccessRequestProcessor {

	EntityType<SimpleEchoService> T = EntityTypes.T(SimpleEchoService.class);

	/**
	 * An (optional) delay in milliseconds.
	 */
	Long getDelay();
	void setDelay(Long delay);

	/**
	 * Specifies how many times the message will be echoed. Default is <code>1</code>.
	 */
	@Initializer("1")
	Integer getEchoCount();
	void setEchoCount(Integer echoCount);
}
