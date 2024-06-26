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
package com.braintribe.devrock.zarathud.model.context;

import java.util.Map;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * handling the maven/ant/tb integration 
 * @author pit
 *
 */
@Abstract
public interface ResolutionAspect extends GenericEntity {
	
	EntityType<ResolutionAspect> T = EntityTypes.T(ResolutionAspect.class);
	String settingsFilename = "settingsFilename";
	String repositoryLocation = "repositoryLocation";
	String environmentOverrides = "environmentOverrides";

	/**
	 * deprecated : standard setup uses a 'environment sensitive configuration' wire module 
	 * @return - the settings.xml file to use 
	 */
	@Deprecated
	String getSettingsFilename();
	@Deprecated
	void setSettingsFilename(String value);
	
	/**
	 * deprecated : standard setup uses a 'environment sensitive configuration' wire module
	 * @return - the name of the local repository to use 
	 */
	@Deprecated	
	String getRepositoryLocation();
	@Deprecated
	void setRepositoryLocation(String value);
	
	/**
	 * allows tweaking of the interval virtual enviroment : environment variables
	 * @return - a {@link Map} of variable-name to variable-value
	 */
	Map<String,String> getEnvironmentOverrides();
	void setEnvironmentOverrides(Map<String,String> value);
	
	



}
