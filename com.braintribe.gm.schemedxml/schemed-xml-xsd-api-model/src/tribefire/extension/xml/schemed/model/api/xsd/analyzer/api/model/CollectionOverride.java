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

import java.util.List;
import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * a {@link CollectionOverride} declares what multiple child should be a {@link Set} rather than a {@link List}<br/>
 * any element that is declared with maxOccurs="unbounded" (actually > 1) will be turned into a collection, default into a {@link List}
 * @author pit
 *
 */
@Description("a CollectionOverride declares what multiple child should be a Set rather than a List,  any element that is declared with maxOccurs=\"unbounded\" (actually > 1) will be turned into a collection, default into a List")
public interface CollectionOverride extends GenericEntity{

final EntityType<CollectionOverride> T = EntityTypes.T(CollectionOverride.class);

	/**
	 * 
	 * @return address - the {@link SchemaAddress} of the the element in the xsd that represents the collection
	 */
	@Description("the address of the element within the schema that is addressed")	
	@Alias("a")
	@Mandatory
	SchemaAddress getSchemaAddress();
	void setSchemaAddress( SchemaAddress address);
	
	/**
	 * @return - true if a {@link Set}, false if a {@link List}
	 */
	@Description("set to true if resulting collection should be a Set, false a List")
	@Alias("s")
	@Initializer("true")
	boolean getCollectionAsSet();
	void setCollectionAsSet( boolean asSet);

}
