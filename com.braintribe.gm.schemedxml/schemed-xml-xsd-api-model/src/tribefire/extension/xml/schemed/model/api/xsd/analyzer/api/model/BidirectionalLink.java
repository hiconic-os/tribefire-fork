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
 * a {@link BidirectionalLink} describes where a backlink should be added,
 * i.e. where a child should receive a property that points back to its parent. 
 * the backlink will automatically be set be the marshaller during read, yet not written during write.
 *  
 * @author pit
 *
 */
@Description("a {@link BidirectionalLink} describes where a backlink should be added, i.e. where a child should receive a property that points back to its parent. The backlink will automatically be set be the marshaller during read, yet not written during write.")
public interface BidirectionalLink extends GenericEntity {
	
	final EntityType<BidirectionalLink> T = EntityTypes.T(BidirectionalLink.class);

	/**
	 * defines that child that should get the back-linking property
	 * @return - the {@link SchemaAddress} that describes the point in the xsd
	 */
	@Description("the address of the element within the schema that should get the backlink")
	@Alias( "a")
	@Mandatory
	SchemaAddress getSchemaAddress();
	void setSchemaAddress( SchemaAddress address);
	
	/** 
	 * @return - property name of the backlink property to be added to child as defined in the {@link SchemaAddress}
	 */
	@Description("the name of property to contain the backlink")
	@Alias("b")
	@Mandatory
	String getBacklinkProperty();
	void setBacklinkProperty(String poperty);	
	
	
}
