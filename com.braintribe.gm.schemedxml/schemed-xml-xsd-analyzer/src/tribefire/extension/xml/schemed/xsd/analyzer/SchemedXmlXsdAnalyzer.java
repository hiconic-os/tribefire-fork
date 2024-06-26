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
package tribefire.extension.xml.schemed.xsd.analyzer;

import com.braintribe.model.resource.Resource;

import tribefire.extension.xml.schemed.marshaller.xsd.resolver.BasicSchemaReferenceResolverMk2;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerResponse;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.xsd.analyzer.modelbuilder.ModelBuilder;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.analyzer.BasicAnalyzerRegistry;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.analyzer.BasicNamespaceGenerator;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.BasicSchemaRegistry;
import tribefire.extension.xml.schemed.xsd.api.analyzer.naming.NamespaceGenerator;

public class SchemedXmlXsdAnalyzer  {

	private BasicSchemaReferenceResolverMk2 schemaReferenceResolver = new BasicSchemaReferenceResolverMk2();
	private NamespaceGenerator namespaceGenerator = new BasicNamespaceGenerator();
	private BasicAnalyzerRegistry analyzerRegistry = new BasicAnalyzerRegistry();
	private boolean verbose;
	
	public void setVerbose(boolean verbose) {
		this.verbose = verbose;
	}
	
	
	public SchemedXmlXsdAnalyzerResponse process( SchemedXmlXsdAnalyzerRequest request) {
		Schema schema = null;
		Resource containerResource = request.getContainerResource();
		if (containerResource == null) {			
			schemaReferenceResolver.setReferencedSchemata( request.getReferencedSchemata());
			String uri = request.getSkeletonModelName(); // we have no URI here, so we use the skeleton as URI 
			schema = schemaReferenceResolver.resolve( uri, request.getSchema());
		}
		else {
			schemaReferenceResolver.setContainerResource( containerResource);
			schema = schemaReferenceResolver.resolve( null, request.getContainerTerminalSchemaUri());
			
		}
		BasicSchemaRegistry schemaRegistry = BasicSchemaRegistry.createFromSchema(schema, schemaReferenceResolver, namespaceGenerator, analyzerRegistry, analyzerRegistry);
		schemaRegistry.setVerbose(verbose);
		
		analyzerRegistry.setVerbose( verbose);
		analyzerRegistry.parametrize(request);
		analyzerRegistry.analyze(schemaRegistry);
		
		
		SchemedXmlXsdAnalyzerResponse response = ModelBuilder.buildPrimerResponse(request.getSkeletonModelName(), analyzerRegistry);		
		return response;			
	}
		
	
}
