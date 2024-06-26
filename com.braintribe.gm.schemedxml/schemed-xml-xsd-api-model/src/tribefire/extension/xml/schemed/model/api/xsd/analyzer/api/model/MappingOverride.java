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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * a mapping override overrides the automatic naming procedure in the analyzer.
 * 
 * @author pit
 *
 */
@Description("a mapping override overrides the automatic naming procedure in the analyer.")
public interface MappingOverride extends GenericEntity{

final EntityType<MappingOverride> T = EntityTypes.T(MappingOverride.class);

	/**
	 * identifies the type or element or attribute in the xsd.<br7>
	 * if the {@link SchemaAddress} has no value set in #SchemaAddress.element, then the type is changed,
	 * otherwise the resulting property (mapped either from element or attribute) is changed 
	 * @return address - the {@link SchemaAddress} to the element 
	 */
	@Description("identifies the type or element or attribute in the xsd. If the SchemaAddress has no value set in SchemaAddress.element, then the type is changed, otherwise the resulting property (mapped either from element or attribute) is changed")
	@Alias("a")
	@Mandatory
	SchemaAddress getSchemaAddress();
	void setSchemaAddress( SchemaAddress address);
	
	/**
	 * 
	 * @return- the name to use instead of the automatic map
	 */
	@Description("the name to use instead of the automatically generated name")
	@Alias("n")
	@Mandatory
	String getNameOverride();
	void setNameOverride( String name);	

}
