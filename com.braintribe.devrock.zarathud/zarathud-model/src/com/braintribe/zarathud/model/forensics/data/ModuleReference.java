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
 * represents the 'împorter side' of the modules, i.e. the required packages of
 * the terminal retrieved from the dependency.
 * @author pit
 *
 */
public interface ModuleReference extends GenericEntity {
		
	EntityType<ModuleReference> T = EntityTypes.T(ModuleReference.class);
	
	String moduleName = "moduleName";
	String artifactName = "artifactName";
	String requiredPackages = "requiredPackages";
	
	/**
	 * @return
	 */
	String getArtifactName();
	void setArtifactName(String value);
	
	/**
	 * @return - a {@link List} of the packages required (the packages of the types required) 
	 */
	List<String> getRequiredPackages();
	void setRequiredPackages(List<String> value);
	
	
		
}
