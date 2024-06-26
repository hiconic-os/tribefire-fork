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
package tribefire.extension.simple.model.deployment.access;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.deployment.Deployable;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * The <code>SimpleInMemoryAccess</code> is a simple access implementation that stores entities from the
 * <code>simple-data-model</code> in memory.
 * <p>
 * This entity type is a so-called <i>denotation type</i> which represents the respective
 * <code>SimpleInMemoryAccess</code> implementation.<br>
 * Denotation types are used to assign {@link Deployable}s to a cartridge and to configure and deploy/undeploy them. All
 * this can be done via the Control Center (or programmatically).<br>
 * During deployment the denotation type instance is passed to the cartridge. The cartridge may then access the
 * denotation type's properties to configure the respective deployable. Denotation types may have any number (i.e.
 * <code>0..n</code>) of configuration properties.
 * <p>
 * Since this is just an example, this denotation type only adds a {@link #getInitializeWithExampleData() single
 * property} to the (standard) {@link IncrementalAccess} properties.
 *
 * @author michael.lafite
 */
public interface SimpleInMemoryAccess extends IncrementalAccess {

	EntityType<SimpleInMemoryAccess> T = EntityTypes.T(SimpleInMemoryAccess.class);

	/**
	 * Specifies whether or not the access shall be initialized with example data (during deployment). This option is
	 * enabled by default.
	 */
	@Initializer("true")
	boolean getInitializeWithExampleData();
	void setInitializeWithExampleData(boolean initializeWithExampleData);
}
