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
package com.braintribe.zarathud.model.forensics.data;

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * represents the module information of a dependency to the terminal
 *  
 * @author pit
 *
 */
public interface ImportedModule extends GenericEntity {
		
	EntityType<ImportedModule> T = EntityTypes.T(ImportedModule.class);
	
	String moduleName = "moduleName";
	String artifactName = "artifactName";
	String requiredExports = "requiredExports";

	/**
	 * @return - the proposed name of the module (the common root of a type references)
	 */
	String getModuleName();
	void setModuleName(String value);
	
	/**
	 * @return - the name of the artifact that should get the module data 
	 */
	String getArtifactName();
	void setArtifactName(String value);

	
	/**
	 * @return - a {@link List} of the required exports in the imported module
	 */
	List<String> getRequiredExports();
	void setRequiredExports(List<String> value);


}
