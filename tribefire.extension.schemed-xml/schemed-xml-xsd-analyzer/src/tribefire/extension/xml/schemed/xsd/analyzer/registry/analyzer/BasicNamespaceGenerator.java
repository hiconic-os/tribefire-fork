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
package tribefire.extension.xml.schemed.xsd.analyzer.registry.analyzer;

import java.util.HashMap;
import java.util.Map;

import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.xsd.api.analyzer.naming.NamespaceGenerator;

public class BasicNamespaceGenerator implements NamespaceGenerator {
	private static final String INJECTED_NAMESPACE_URI ="InjectedNamespace-";
	private static final String INJECTED_NAMESPACE_PREFIX ="insp-";
	private int count = 0;

	private Map<Namespace, Schema> targetNamespaceToSchemaMap = new HashMap<>();
	private Map<Schema, Namespace> schemaToTargetNamespaceMap = new HashMap<>();

	@Override
	public Namespace createNamespace(Schema schema) {
		Namespace namespace = Namespace.T.create();
		count++;
		namespace.setPrefix( INJECTED_NAMESPACE_PREFIX + count);
		namespace.setUri( INJECTED_NAMESPACE_URI + count);
		targetNamespaceToSchemaMap.put(namespace, schema);
		schemaToTargetNamespaceMap.put(schema, namespace);
		return namespace;
	}
	
	
	
	@Override
	public void acknowledgeNamespace(String namespaceUri, Schema schema) {
		Namespace namespace = Namespace.T.create();
		namespace.setPrefix(namespaceUri);
		namespace.setUri( INJECTED_NAMESPACE_URI + count);
		targetNamespaceToSchemaMap.put(namespace, schema);
		schemaToTargetNamespaceMap.put(schema, namespace);		
	}



	public Map<Namespace, Schema> getTargetNamespaceToSchemaMap() {
		return targetNamespaceToSchemaMap;
	}
	
	public Map<Schema, Namespace> getSchemaToTargetNamespaceMap() {
		return schemaToTargetNamespaceMap;
	}

}
