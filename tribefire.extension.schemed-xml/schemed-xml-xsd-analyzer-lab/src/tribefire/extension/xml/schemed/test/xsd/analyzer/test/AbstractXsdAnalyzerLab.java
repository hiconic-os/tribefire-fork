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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.KnownIssue;

import tribefire.extension.xml.schemed.marshaller.commons.ModelPersistenceExpert;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerResponse;
import tribefire.extension.xml.schemed.requestbuilder.builder.AnalyzerRequestBuilder;
import tribefire.extension.xml.schemed.requestbuilder.builder.impl.AnalyzerRequestContext;
import tribefire.extension.xml.schemed.requestbuilder.builder.impl.SchemaReferencesContext;
import tribefire.extension.xml.schemed.xsd.analyzer.SchemedXmlXsdAnalyzer;

/**
 * @author pit
 *
 */
@Category(KnownIssue.class)
public abstract class AbstractXsdAnalyzerLab {
	protected static File contents = new File( "res");
	
	private SchemedXmlXsdAnalyzer analyzer;
	
	
	public AbstractXsdAnalyzerLab() {
		analyzer = new SchemedXmlXsdAnalyzer();
	}
		
	/**
	 * @param request
	 * @param output
	 */
	protected void process( SchemedXmlXsdAnalyzerRequest request, File output) {
		if (request.getSchema() != null) {
			System.out.println("**** Processing [" + request.getSchema().getName() + "] -> [" + output.getAbsolutePath() + "] ****");
		}
		else {
			System.out.println("**** Processing [" + request.getContainerTerminalSchemaUri() + "] -> [" + output.getAbsolutePath() + "] ****");
		}
		SchemedXmlXsdAnalyzerResponse response = analyzer.process( request);
	
		System.out.println("**** dumping jar for  [" + response.getSkeletonModel().getName() + "] -> [" + output.getAbsolutePath() + "] ****");
		ModelPersistenceExpert.dumpModelJar(response.getSkeletonModel(), output);
		
		System.out.println("**** dumping xml for  [" + response.getMappingModel().getName() + "] -> [" + output.getAbsolutePath() + "] ****");
		ModelPersistenceExpert.dumpMappingModel( response.getMappingModel(), output);
	}
	
	protected SchemedXmlXsdAnalyzerRequest buildPrimerRequest(File input, String packageName, String xsdName, List<String> referencedXsds, String modelName) {
		AnalyzerRequestContext analyzerRequestContext = AnalyzerRequestBuilder.request()
								.xsd()
									.file(new File( input, xsdName))
								.close()
								.packageName(packageName)
								.modelName(modelName);
																
		
		if (referencedXsds != null && referencedXsds.size() > 0) {
			SchemaReferencesContext<AnalyzerRequestContext> references = analyzerRequestContext.references();
			for (String referencedXsd : referencedXsds) {
				File reference = new File( input, referencedXsd);
				references.file(reference, referencedXsd);
			}
			references.close();
		}
		
		return analyzerRequestContext.build();
	}
	protected SchemedXmlXsdAnalyzerRequest buildPrimerRequest(File input, String packageName, String resourceName, String xsdName, String modelName) {
		SchemedXmlXsdAnalyzerRequest analyzerRequest = AnalyzerRequestBuilder.request()
														.xsd()
															.archive( new File( input, resourceName), xsdName)
														.close()
														.packageName(packageName)
														.modelName(modelName)
														.build();
														
		return analyzerRequest;				
	}
	
	protected List<String> generateSchemaReferencesFromFiles( File directory) {
		File [] files = directory.listFiles( new FileFilter() {
			
			@Override
			public boolean accept(File file) {				
				if (file.getName().endsWith(".xsd"))
					return true;
				return false;
			}
		});
		List<String> references = new ArrayList<>();
		for (File file : files) {
			references.add( file.getName());
		}
		return references;
	}
	
}
