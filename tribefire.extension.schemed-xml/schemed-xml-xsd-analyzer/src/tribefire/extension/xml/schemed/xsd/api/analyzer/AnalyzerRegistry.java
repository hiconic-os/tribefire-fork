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
package tribefire.extension.xml.schemed.xsd.api.analyzer;

import java.util.List;
import java.util.Map;

import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;

import tribefire.extension.xml.schemed.model.xsd.Element;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public interface AnalyzerRegistry {
	/**
	 * get the {@link SchemaRegistry} that is responsible for the {@link Schema}
	 * @param schema - the {@link Schema}
	 * @return - the corresponding {@link SchemaRegistry}
	 */
	SchemaRegistry getBackingRegistryForSchema( Schema schema);
	
	/**
	 * connect the {@link SchemaRegistry} to the  {@link Schema} it is responsible for
	 * @param schema - the {@link Schema}
	 * @param registry - the {@link SchemaRegistry}
	 */
	void setBackingRegistryForSchema( Schema schema, SchemaRegistry registry);
	
	
	/**
	 * returns all {@link GmType} that were collected during the run
	 * @return - {@link Map} of type signature to {@link GmType}
	 */
	Map<String, GmType> getExtractedTypes();
	
	List<GmMetaModel> getActualSubstitutionModels();
	
	/**
	 * returns the top level elements that were collected plus their associated {@link GmType}, they'll be the possible root types
	 * @return - {@link Map} of {@link Element}'s name to linked {@link GmType}
	 */
	Map<String, GmType> getExtractedTopLevelElements();
	
	
	String getTargetNamespace();
	
	Map<String, String> getPrefixToNamespacesMap();
	
	public Map<String, Namespace> getNamespaces();
	
	boolean getElementQualified();
	
	boolean getAttributeQualified();
	
}
