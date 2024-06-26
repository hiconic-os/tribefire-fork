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
 * a {@link SchemaAddress} identifies a point in a schema, i.e. a type and one of its properties. 
 * if only the parent part is declared, then a type is meant to be targeted, otherwise, 
 * it's the element aka property. If the parent's type null, then a top-level element is to 
 * be targeted. 
 * @author pit
 *
 */
@Description("a SchemaAddress identifies a point in a schema, i.e. a type and one of its properties. If only the parent part is declared, then a type is meant to be targeted, otherwise, it's the element aka property. If the parent's type null, then a top-level element is to be targeted.")
public interface SchemaAddress extends GenericEntity {
	
	final EntityType<SchemaAddress> T = EntityTypes.T(SchemaAddress.class);

	/**	
	 * @return - the name of the complex type that is to be addressed
	 */
	@Description("the name of the complex type that is to be addressed")
	@Alias("t")
	@Mandatory
	String getParent();
	void setParent(String parent);
	
	/**
	 * @return - the name of the property or attribute that is to be addressed (or null if only the type's targeted)
	 */
	@Description("the name of the property or attribute that is to be addressed (or null if only the type's targeted)")
	@Alias("e")
	String getElement();
	void setElement( String element);
}
