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
package tribefire.extension.xml.schemed.marshaller.xsd;

public interface HasSchemaTokens {

	static final String WWW_SCHEMA_DEF = "http://www.w3.org/2001/XMLSchema";
	
	static final String SCHEMA = "schema";
	static final String ID = "id";
	static final String ATTRIBUTE_FORM_DEFAULT = "attributeFormDefault";
	static final String ELEMENT_FORM_DEFAULT = "elementFormDefault";
	static final String BLOCK_DEFAULT = "blockDefault";
	static final String FINAL_DEFAULT = "finalDefault";
	
	static final String TARGET_NAMESPACE = "targetNamespace";
	static final String VERSION = "version";
	static final String XMLNS = "xmlns";
	
	static final String INCLUDE = "include";
	static final String IMPORT = "import";
	static final String SCHEMA_LOCATION = "schemaLocation";
	static final String NAMESPACE = "namespace";
	
	static final String REDEFINE = "redefine";
	static final String COMMENT = "comment";
	
	static final String ANNOTATION = "annotation";
	static final String APP_INFO = "appInfo";
	static final String SOURCE = "source";
	static final String DOCUMENTATION = "documentation";
	static final String LANGUAGE = "lang";
	
	static final String SIMPLE_TYPE = "simpleType";
	static final String NAME = "name";
	static final String ABSTRACT = "abstract";
	
	static final String SIMPLE_CONTENT = "simpleContent";
	static final String COMPLEX_CONTENT = "complexContent";
	
	static final String MIXED = "mixed";
	static final String BLOCK = "block";
	static final String FINAL = "final";
	
	static final String COMPLEX_TYPE ="complexType";
	static final String GROUP = "group";
	static final String SEQUENCE = "sequence";
	static final String CHOICE = "choice";
	static final String ALL = "all";
	static final String ANY = "any";
	
	static final String ATTRIBUTE = "attribute";
	static final String DEFAULT = "default";
	static final String FIXED = "fixed";
	static final String FORM = "form";
	static final String USE = "use";
	
	static final String ATTRIBUTE_GROUP = "attributeGroup";
	
	static final String ELEMENT = "element";
	
	static final String RESTRICTION = "restriction";
	static final String EXTENSION = "extension";
	static final String LIST = "list";
	static final String ITEM_TYPE ="itemType";
	static final String UNION = "union";
	static final String MEMBER_TYPES = "memberTypes";
	
	
	static final String UNIQUE = "unique";
	static final String KEY="key";
	static final String KEY_REF="keyref";
	static final String REF="ref";
	
	static final String TYPE="type";
	
	static final String MAX_OCCURS = "maxOccurs";
	static final String MIN_OCCURS = "minOccurs";
	static final String UNBOUNDED = "unbounded";
	
	static final String BASE = "base";
	static final String MIN_EXCLUSIVE = "minExclusive";
	static final String MIN_INCLUSIVE = "minInclusive";
	static final String MAX_EXCLUSIVE = "maxExclusive";
	static final String MAX_INCLUSIVE = "maxInclusive";
	static final String TOTAL_DIGITS = "totalDigits";
	static final String FRACTION_DIGITS = "fractionDigits";
	static final String LENGTH = "length";
	static final String MIN_LENGTH = "minLength";
	static final String MAX_LENGTH = "maxLength";
	static final String ENUMERATIION = "enumeration";
	static final String WHITE_SPACE = "whiteSpace";
	static final String PATTERN = "pattern";
	
	static final String VALUE = "value";
	
	static final String [] KNOWN_ATTRIBUTES = new String [] {ATTRIBUTE_FORM_DEFAULT, ELEMENT_FORM_DEFAULT, TARGET_NAMESPACE}; 
	
}
