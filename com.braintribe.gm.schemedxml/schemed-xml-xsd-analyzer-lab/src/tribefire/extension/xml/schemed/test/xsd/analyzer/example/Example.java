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
package tribefire.extension.xml.schemed.test.xsd.analyzer.example;

import java.io.File;

import tribefire.extension.xml.schemed.marshaller.commons.ModelPersistenceExpert;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerResponse;
import tribefire.extension.xml.schemed.requestbuilder.builder.AnalyzerRequestBuilder;
import tribefire.extension.xml.schemed.xsd.analyzer.SchemedXmlXsdAnalyzer;


/**
 * a functional example of how to call the {@link SchemedXmlXsdAnalyzer} locally (file system)
 * 
 * @author pit
 *
 */
public class Example {
		
	/**
	 * @param args
	 */
	public static void main( String [] args) {
		
		// parameterize the request
		String inputDirectory = args[0]; // the directory where the input files reside		
		String packageName = args[1]; // the name of the package the generated types should be in
		String modelName = args[2]; // the fully qualified name of the model, i.e. <groupId>:<artifactId>#<version>
		String xsdName = args[3]; // either the file name of the main XSD or the name of the entry in the zip file, if resourceName's set
		String resourceName = null; // the name of the zip file if multiple XSDs are required
			
		// building the request
		SchemedXmlXsdAnalyzerRequest analyzerRequest;
		File input = new File( inputDirectory);
		if (args.length > 4) {
			resourceName = args[4];
			analyzerRequest = AnalyzerRequestBuilder.request()
								.xsd()
									.archive( new File( input, resourceName), xsdName)
								.close()
								.packageName(packageName)
								.modelName(modelName)
								.build();																	 			
		}
		else {
			analyzerRequest = AnalyzerRequestBuilder.request()
					.xsd()
						.file( new File( input, xsdName))
					.close()
					.packageName(packageName)
					.modelName(modelName)
					.build();			
		}
		// running the analyzer
		SchemedXmlXsdAnalyzer analyzer = new SchemedXmlXsdAnalyzer();
		SchemedXmlXsdAnalyzerResponse analyzerResponse = analyzer.process(analyzerRequest);		
		
		// generating some output
		File output = new File( input, "output");
		// the skeleton as artifact, i.e. jar, pom, sources.jar
		ModelPersistenceExpert.dumpModelJar( analyzerResponse.getSkeletonModel(), output);
		// dump the mapping model
		ModelPersistenceExpert.dumpMappingModel( analyzerResponse.getMappingModel(), output);
		 
	}

}
