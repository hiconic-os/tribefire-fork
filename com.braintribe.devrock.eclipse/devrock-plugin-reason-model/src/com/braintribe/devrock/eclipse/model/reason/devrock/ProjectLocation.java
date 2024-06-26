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
package com.braintribe.devrock.eclipse.model.reason.devrock;

import com.braintribe.devrock.model.mc.cfg.origination.Origination;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * actually, it's an origination 
 * @author pit
 *
 */
@SelectiveInformation("Project located in filesystem at ${location}")
public interface ProjectLocation extends PluginReason, Origination {
	
	EntityType<ProjectLocation> T = EntityTypes.T(ProjectLocation.class);

	String location = "location";
	
	/**
	 * @return - path to projects location in the filesystem
	 */
	String getLocation();
	void setLocation(String value);

}
