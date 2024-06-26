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
package tribefire.descriptor.model;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * @author peter.gazdik
 */
public interface ModulesDescriptor extends GenericEntity {

	EntityType<ModulesDescriptor> T = EntityTypes.T(ModulesDescriptor.class);

	/**
	 * Each {@link ModuleDescriptor} contains an information necessary to load that module, and the order in this list determines the order in which
	 * them modules are loaded. In general, if a module A depends on module B, then B must be loaded before A.
	 */
	List<ModuleDescriptor> getModules();
	void setModules(List<ModuleDescriptor> modules);

}