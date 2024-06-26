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
package tribefire.extension.xml.schemed.model.xsd;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * see https://www.w3schools.com/xml/el_union.asp
 * @author pit
 *
 */
public interface Union extends SchemaEntity, Annoted, SequenceAware {
	
	final EntityType<Union> T = EntityTypes.T(Union.class);

	java.util.List<QName> getSimpleTypeReferences();
	void setSimpleTypeReferences( java.util.List<QName> types);
	
	java.util.List<SimpleType> getSimpleTypes();
	void setSimpleTypes( java.util.List<SimpleType> types);
}
