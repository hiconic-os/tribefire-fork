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
package tribefire.extension.xml.schemed.test.commons.commons;

import java.io.File;
import java.util.List;

import com.braintribe.cfg.Configurable;
import com.braintribe.model.resource.Resource;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ReferencedSchema;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ReferencedSchemata;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.test.commons.xsd.test.resource.ResourceGenerator;
import tribefire.extension.xml.schemed.test.commons.xsd.test.resource.ResourceProvidingSession;

/**
 * @author pit
 *
 */
public class SchemedXmlXsdAnalyzerRequestBuilder {
	protected static File contents = new File( "res");
	private ResourceProvidingSession session = new ResourceProvidingSession();
	
	@Configurable
	public void setSession(ResourceProvidingSession session) {
		this.session = session;
	}
	
	private ResourceProvidingSession getSession() {
		if (session == null) {
			session = new ResourceProvidingSession();
		}
		return session;
	}
	
	
	public SchemedXmlXsdAnalyzerRequest buildPrimerRequest(File input, String packageName, String xsdName, List<String> referencedXsds, String modelName) {
		SchemedXmlXsdAnalyzerRequest request = SchemedXmlXsdAnalyzerRequest.T.create();
				
		request.setSchema( ResourceGenerator.filesystemResourceFromFile( getSession(), new File( input, xsdName)));
		
		if (referencedXsds != null && !referencedXsds.isEmpty()) {
			ReferencedSchemata referencedSchemata = ReferencedSchemata.T.create();		

			for (String referencedXsd : referencedXsds) {
				ReferencedSchema referencedSchema = ReferencedSchema.T.create();		
				Resource schema = ResourceGenerator.filesystemResourceFromFile( getSession(), new File( input, referencedXsd));
				referencedSchema.setSchema(schema);		
				referencedSchema.setUri( referencedXsd);
				referencedSchemata.getReferencedSchemata().add(referencedSchema);
			}						
			request.setReferencedSchemata( referencedSchemata);
		}
		
		request.setSkeletonModelName(modelName);
		request.setTopPackageName(packageName);
		return request;
	}
	
	public SchemedXmlXsdAnalyzerRequest buildPrimerRequest(File input, String packageName, String containerResourceName, String schemaName, String modelName) {
		SchemedXmlXsdAnalyzerRequest request = SchemedXmlXsdAnalyzerRequest.T.create();
				
		request.setContainerResource( ResourceGenerator.filesystemResourceFromFile( getSession(), new File( input, containerResourceName)));
		request.setContainerTerminalSchemaUri(schemaName);
		request.setSkeletonModelName(modelName);
		request.setTopPackageName(packageName);
		return request;
	}
	

}
	
