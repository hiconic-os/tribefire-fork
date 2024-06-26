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
package tribefire.extension.xml.schemed.mapping.metadata;

public interface AnyProcessingTokens {
	
	static final String MD_ANY_TYPE = "!AnyType";
	static final String MD_ANY_VALUE = "!any.value";
	static final String MD_ANY_NAME = "!any.name";
	static final String MD_ANY_ATTRIBUTES_TYPE = "!AnyAttributes";
	
	static final String MD_ANY_PROPERTIES = "!any.properties";
	static final String MD_ANY_ATTRIBUTES = "!any.attributes";
	
	static final String TYPE_ANY_TYPE = "AnyType";
	static final String TYPE_ANY_ATTRIBUTE_TYPE = "AnyAttribute";
	static final String TYPE_ANY_PROPERTIES = "properties";
	static final String TYPE_ANY_ATTRIBUTES = "attributes";
	static final String TYPE_ANY_NAME = "name";
	static final String TYPE_ANY_VALUE = "value";
	
	static final String COM_BRAINTRIBE_XML = "com.braintribe.xml";
		
	static final String ANY_TYPE_SIGNATURE = COM_BRAINTRIBE_XML + "." + TYPE_ANY_TYPE;
}
