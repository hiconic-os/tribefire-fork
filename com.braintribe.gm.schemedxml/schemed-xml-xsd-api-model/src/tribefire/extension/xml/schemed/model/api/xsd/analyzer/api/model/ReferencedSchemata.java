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
package tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model;

import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * the {@link ReferencedSchemata} is the set of xsd to use 
 * 
 * @author pit
 *
 */
@Description("the ReferencedSchemata is the set of referenced xsd by the main xsd")
public interface ReferencedSchemata extends GenericEntity{

	final EntityType<ReferencedSchemata> T = EntityTypes.T(ReferencedSchemata.class);
	
	/**
	 * @return - the list of {@link ReferencedSchema}
	 */
	@Description("the list of ReferencedSchema")
	@Alias("s")
	@Mandatory
	Set<ReferencedSchema> getReferencedSchemata();
	void setReferencedSchemata(Set<ReferencedSchema> schemata);
	
	/**	  
	 * @return - true if they are to be loaded dynamically (from the internet for instance), false if the they exist as resources 
	 */
	@Description("true if they are to be loaded dynamically (from the internet for instance), false if the they exist as resources. default is false")
	@Alias("l")
	@Initializer("false")
	boolean getLoadOnRequirement();
	void setLoadOnRequirement( boolean loadOnRequirement);	

	

}
