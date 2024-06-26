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
package tribefire.extension.xml.schemed.xsd.analyzer.registry.context;

import java.util.Stack;

import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.SchemaEntity;
import tribefire.extension.xml.schemed.xsd.api.analyzer.AnalyzerRegistry;
import tribefire.extension.xml.schemed.xsd.api.analyzer.SchemaRegistry;
import tribefire.extension.xml.schemed.xsd.api.analyzer.naming.QPathGenerator;

public class SchemaMappingContext {
	public MappingContext mappingContext;
		
	public SchemaRegistry registry;
	public Schema schema;
	public AnalyzerRegistry analyzerRegistry;	
	public Stack<SchemaEntity> currentEntityStack = new Stack<>();
	
	public QPathGenerator qpathGenerator;
	
	public SchemaMappingContext( MappingContext context) {
		this.mappingContext = context;
	}
	
	public String print() {
		return schema.getSchemaNamespace().print();
	}
}
