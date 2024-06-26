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

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.xml.schemed.model.xsd.archetypes.HasAttributeGroups;
import tribefire.extension.xml.schemed.model.xsd.archetypes.HasAttributes;

public interface Schema extends Annoted, SequenceAware, HasAttributes, HasAttributeGroups {
		
	final EntityType<Schema> T = EntityTypes.T(Schema.class);

	
	String getSchemaPrefix();
	void setSchemaPrefix( String prefix);
	
	Qualification getAttributeFormDefault();
	void setAttributeFormDefault( Qualification qualification);
	
	boolean getAttributeFormDefaultSpecified();
	void setAttributeFormDefaultSpecified( boolean specified);
	
	Qualification getElementFormDefault();
	void setElementFormDefault( Qualification qualification);
	
	boolean getElementFormDefaultSpecified();
	void setElementFormDefaultSpecified( boolean specified);	

	List<Namespace> getNamespaces();
	void setNamespaces( List<Namespace> namespaces);
	
	Namespace getSchemaNamespace();
	void setSchemaNamespace( Namespace namespace);
	
	Namespace getTargetNamespace();
	void setTargetNamespace( Namespace namespace);
	
	Namespace getDefaultNamespace();
	void setDefaultNamespace( Namespace namespace);
	
	String getXmlNs();
	void setXmlNs( String xmlNs);
	
	List<SchemaEntity> getEntities();
	void setEntities( List<SchemaEntity> entities);

	List<Include> getIncludes();
	void setIncludes( List<Include> includes);
	
	List<Import> getImports();
	void setImports( List<Import> imports);
	
	List<Element> getToplevelElements();
	void setToplevelElements( List<Element> elements);

	List<ComplexType> getComplexTypes();
	void setComplexTypes( List<ComplexType> types);
	
	List<SimpleType> getSimpleTypes();
	void setSimpleTypes( List<SimpleType> types);
		
	List<Group> getGroups();
	void setGroups( List<Group> groups);
	

	
		
	
	
}
