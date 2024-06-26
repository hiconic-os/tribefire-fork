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
package com.braintribe.zarathud.model.forensics;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.forensics.data.ImportedModule;
import com.braintribe.zarathud.model.forensics.data.ModuleReference;

public interface ModuleForensicsResult extends ForensicsResult {
	EntityType<ModuleForensicsResult> T = EntityTypes.T(ModuleForensicsResult.class);
	
	String moduleImports = "moduleImports";
	String requiredImportModules = "requiredImportModules";

	/**
	 * @return - a list of what the terminal requires module-wise
	 */
	List<ModuleReference> getModuleImports();
	void setModuleImports(List<ModuleReference> value);
	
	/**
	 * @return - a {@link List} of modules with their exports (derived from above and 
	 * collated during the full walk
	 */
	List<ImportedModule> getRequiredImportModules();
	void setRequiredImportModules(List<ImportedModule> value);

}
