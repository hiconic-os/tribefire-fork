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
package com.braintribe.model.artifact.maven.meta;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;


/**
 * represents a plugin of the maven world.. just here for matters of completeness 
 * @author pit
 *
 */
public interface Plugin extends GenericEntity {

	final EntityType<Plugin> T = EntityTypes.T(Plugin.class);
	
	String getName();
	void setName( String name);
	
	String getPrefix();
	void setPrefix( String prefix);
	
	String getArtifactId();
	void setArtifactId( String artifactId);
}
