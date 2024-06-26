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
import com.braintribe.model.meta.GmType;

/**
 * a {@link Substitution} declare a type switch.
 * If only simple types are used, the SchemedXmlMarshaller still can un-/marshall an XML following the XSD,
 * but cannot handle complex types without specific handler. 
 * @author pit
 *
 */
@Description("replaces a type as extracted from the XSD with a already existing GmType")
public interface Substitution extends GenericEntity {
	
	final EntityType<Substitution> T = EntityTypes.T(Substitution.class);

	/**
	 * identifies the type, element or attribute in the xsd whose type is to be substituted,
	 * if the {@link SchemaAddress} element is null, then type itself if switched. 
	 * @return- the {@link SchemaAddress} pointing the place
	 */
	@Description("identifies the type, element or attribute in the xsd whose type is to be substituted, if the element is null, then type itself if switched.")
	@Alias("a")
	@Mandatory
	SchemaAddress getSchemaAddress();
	void setSchemaAddress( SchemaAddress address);

	/** 
	 * @return - the signature of the {@link GmType} to replace the XSD type with
	 */
	@Description("the signature of the GmType to replace the XSD type with")
	@Alias("s")
	@Mandatory
	String getReplacementSignature();
	void setReplacementSignature(String signature);
	
	
	/**  
	 * @return - the global id of the replacing type (null if auto naming as in JTA should happen) 
	 */
	@Description("the global id of the replacing type (null if auto naming as in JTA should happen)")
	@Alias("g")
	String getReplacementGlobalId();
	void setReplacementGlobalId( String globalId);
}
