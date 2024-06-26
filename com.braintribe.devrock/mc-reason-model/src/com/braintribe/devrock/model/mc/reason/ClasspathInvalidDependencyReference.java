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
package com.braintribe.devrock.model.mc.reason;

import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;


@SelectiveInformation("Invalid combination of dependency type ${dependencyType} and solution packaging ${packaging} at resolution path ${pathInResolution}")
public interface ClasspathInvalidDependencyReference extends McReason {
	
	EntityType<ClasspathInvalidDependencyReference> T = EntityTypes.T(ClasspathInvalidDependencyReference.class);
	
	String packaging = "packaging";
	String dependencyType = "dependencyType";
	String pathInResolution = "pathInResolution";

	/**
	 * @return - the packaging of the dependency's solution
	 */
	String getPackaging();
	void setPackaging(String value);
	
	/**
	 * @return - the type of the dependency 
	 */
	String getDependencyType();
	void setDependencyType(String value);


	/**
	 * @return - the full path up to the dependency
	 */
	String getPathInResolution();
	void setPathInResolution(String value);

	
	
	

}
