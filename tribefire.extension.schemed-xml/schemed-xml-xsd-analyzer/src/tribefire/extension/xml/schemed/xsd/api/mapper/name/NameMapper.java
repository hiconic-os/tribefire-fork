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
package tribefire.extension.xml.schemed.xsd.api.mapper.name;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * handle names (and their transformations between models and XSD) 
 * 
 * @author pit
 *
 */
public interface NameMapper {
	static final String SPACE_REPLACER = "_";
	static final String enumTestPattern = ".[a-zA-Z][a-zA-Z0-9_]*";
	static final String enumReplacePattern = "[-$\\.:/ ]";
	
	
	
	default List<String> getProtectedEnumValues() {
		String [] names = new String [] { 
				"T", 				
		};
		return Arrays.asList( names);
	}
	
	default List<String> getProtectedPropertyNames() {
		
		String [] names = new String [] { 
				"ID", "GLOBALID", "PARTITION", "T", 				
		};
		return Arrays.asList( names);		
	}
	
	
	/**
	 * generate a property name for a collection (out of the single property name)
	 * @param propertyName - the single property name
	 * @return - the name for the collection 
	 */
	String generateCollectionName( String propertyName);
	
	/**
	 * generate a valid name for a java property
	 * @param name - the name as in the XSD
	 * @return - the name that can be used within java
	 */
	String generateJavaCompatiblePropertyName( String name);
	
	
	/**
	 * generate a valid name for a java type (i.e. GmEntityType or GmEnumType)
	 * @param name - the name as in the XSD
	 * @return - the name that can be used within java
	 */
	String generateJavaCompatibleTypeName( String name);
			
	
	/**
	 * translate the passed enumeration values into valid enum constants, and return a translation map 
	 * @param enumeration - a {@link List} with the enumeration values
	 * @return - a {@link Map} of translated-value to original-value
	 */
	Map<String,String> generateJavaCompatibleEnumValues( List<String> enumeration);
	
	/**
	 * generate a type name for the virtual type (an element directly specifies its type without name)
	 * @param propertyName - the name of the property 
	 * @return - the name of the type 
	 */
	String generateJavaCompatibleTypeNameForVirtualPropertyType( String propertyName);	
	
	/**
	 * generate a name for a virtual type which is a restriction of another type 
	 * @param baseName
	 * @return
	 */
	String generateJavaCompatibleTypeNameForVirtualRestrictionType( String baseName);
	
	String generateJavaCompatibleTypeNameForVirtualSequenceType();
	String generateJavaCompatibleTypeNameForVirtualChoiceType(String name);
	String generateJavaCompatibleTypeNameForVirtualChoiceElementType(String name);
	String generateJavaCompatibleTypeNameForVirtualChoiceGroupType();
	String generateJavaCompatibleTypeNameForVirtualChoiceSequenceType();
	String generateJavaCompatibleTypeNameForVirtualChoiceChoiceType();
	
	String generateJavaCompatibleTypeNameForVirtualType(String name);
	
	/**
	 * generate a name for a restricted complex type 
	 * @param base
	 * @return
	 */
	String generateJavaCompatibleTypeNameForVirtualComplexRestrictedType( String base);
	
	/**
	 * generate a name for an extended complex type 
	 * @param base
	 * @return
	 */
	String generateJavaCompatibleTypeNameForVirtualComplexExtendedType( String base);
	
	
	String getOverridingName( String typeName, String propertyName);
			
		
}
