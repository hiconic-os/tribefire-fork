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
package tribefire.extension.xml.schemed.xsd.analyzer.registry.schema;

import java.util.HashMap;
import java.util.Map;

import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class NamespaceRegistry {
	private Map<String,String> prefixToNamespaceUriMap = new HashMap<>();
	private Map<String,String> namespaceUriToPrefixMap = new HashMap<>();
	
	public static NamespaceRegistry createFromSchema( Schema schema) {
		NamespaceRegistry registry = new NamespaceRegistry();
		for (Namespace namespace : schema.getNamespaces()) {
			registry.prefixToNamespaceUriMap.put( namespace.getPrefix(), namespace.getUri());
			registry.namespaceUriToPrefixMap.put(namespace.getUri(), namespace.getPrefix());
		}
		return registry;
	}
	
	public String getPrefixForNamespaceUri( String uri) {
		return namespaceUriToPrefixMap.get(uri);
	}
	
	public String getUriForNamespacePrefix( String prefix) {
		return prefixToNamespaceUriMap.get(prefix);
	}
}
