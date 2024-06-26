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
package tribefire.extension.xml.schemed.test.roundtrip.framework;

import java.io.File;
import java.util.List;

import tribefire.extension.xml.schemed.marshaller.commons.ModelPersistenceExpert;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerResponse;
import tribefire.extension.xml.schemed.test.commons.commons.SchemedXmlXsdAnalyzerRequestBuilder;
import tribefire.extension.xml.schemed.test.commons.xsd.test.resource.ResourceProvidingSession;
import tribefire.extension.xml.schemed.xsd.analyzer.SchemedXmlXsdAnalyzer;

/**
 * @author pit
 *
 */
public abstract class AbstractXsdAnalyzerLab {
	protected static File contents = new File( "res");
	private ResourceProvidingSession session = new ResourceProvidingSession();
	private SchemedXmlXsdAnalyzer analyzer;
	private SchemedXmlXsdAnalyzerRequestBuilder requestBuilder;
	
	public AbstractXsdAnalyzerLab() {
		analyzer = new SchemedXmlXsdAnalyzer();
	}
	
	public SchemedXmlXsdAnalyzerRequestBuilder getRequestBuilder() {
		if (requestBuilder == null) {
			requestBuilder = new SchemedXmlXsdAnalyzerRequestBuilder();
			requestBuilder.setSession(session);
		}
		return requestBuilder;
	}
	
	/**
	 * @param request
	 * @param output
	 */
	protected ProcessTuple process( SchemedXmlXsdAnalyzerRequest request, File output) {	
		System.out.println("**** Processing [" + request.getSchema().getName() + "] -> [" + output.getAbsolutePath() + "] ****");
		SchemedXmlXsdAnalyzerResponse response = analyzer.process( request);
	
		System.out.println("**** dumping jar for  [" + response.getSkeletonModel().getName() + "] -> [" + output.getAbsolutePath() + "] ****");
		File jar = ModelPersistenceExpert.dumpModelJar(response.getSkeletonModel(), output);
		
		System.out.println("**** dumping xml for  [" + response.getMappingModel().getName() + "] -> [" + output.getAbsolutePath() + "] ****");
		File xml = ModelPersistenceExpert.dumpMappingModel( response.getMappingModel(), output);
		
		return new ProcessTuple( jar, xml);
	}
	
	protected SchemedXmlXsdAnalyzerRequest buildPrimerRequest(File input, String packageName, String xsdName, List<String> referencedXsds, String modelName) {		
		return getRequestBuilder().buildPrimerRequest(input, packageName, xsdName, referencedXsds, modelName);				
	}
	
		
}
