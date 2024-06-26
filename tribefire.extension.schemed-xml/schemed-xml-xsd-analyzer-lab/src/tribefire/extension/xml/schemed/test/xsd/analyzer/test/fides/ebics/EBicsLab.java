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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.fides.ebics;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.builder.AnalyzerRequestBuilder;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.fides.HasFidesTokens;

public class EBicsLab extends AbstractXsdAnalyzerLab implements HasFidesTokens {
	
	private static final String TEST_MODEL = GRP_CREDIT_SUISSE_FOX + ":fox-ebics-light-envelope-model#" + VERSION_CREDIT_SUISSE_FOX;
	private static final String TEST_XSD = "EbicsLightEnvelopeModel-3.0.xsd";
	private static String TEST_PACKAGE = "com.braintribe.custom.cs.fox.model.ebics.envelope";	
	private static File fides = new File( contents, "ebics");
	private static File input = new File( fides, "input");
	private static File output = new File( fides, "output");
	

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}

	@Test
	public void flat_Full() {
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
				.file(new File( input, TEST_XSD))
			.close()
			.packageName( TEST_PACKAGE)
			.modelName( TEST_MODEL)
			
			.substitutionModel()
			  
				.modelName(GRP_CREDIT_SUISSE_FOX + ":fox-format-model#" + VERSION_CREDIT_SUISSE_FOX)				
				
				.substitution()
					.schemaAddress()
						.type("Agreement_CF")
						.property("EodStatementExportSchedules")
					.close()
					.replacementSignature("com.braintribe.model.time.schedule.Schedule") 
				.close()
				
				.substitution()
					.schemaAddress()
						.type("Agreement_CF")
						.property("IntradayStatementExportSchedules")
					.close()
					.replacementSignature("com.braintribe.model.time.schedule.Schedule") 
				.close()
			
				.substitution()
					.schemaAddress()
						.type("FormatVersion_CF")
						.property("Schemas")
					.close()
					.replacementSignature("com.braintribe.custom.cs.fox.model.format.ResourceBundle") 
				.close()
				
				.substitution()
					.schemaAddress()
						.type("FormatVersion_CF")
						.property("Schematrons")
					.close()
					.replacementSignature("com.braintribe.custom.cs.fox.model.format.ResourceBundle") 
				.close()
				
				.substitution()
					.schemaAddress()
						.type("FormatVersion_CF")
						.property("XsltImport")
					.close()
					.replacementSignature("com.braintribe.custom.cs.fox.model.format.ResourceBundle") 
				.close()
				
				.substitution()
					.schemaAddress()
						.type("FormatVersion_CF")
						.property("XsltExport")
					.close()
					.replacementSignature("com.braintribe.custom.cs.fox.model.format.ResourceBundle") 
				.close()
				
				.substitution()
					.schemaAddress()
						.type("OutboundStatus_CF")
						.property("Resource")
					.close()
					.replacementSignature("com.braintribe.custom.cs.fox.model.format.ResourceBundle") 
				.close()

				.substitution()
					.schemaAddress()
						.type("Rule_CF")
						.property("RuleScript")
					.close()
					.replacementSignature("com.braintribe.model.rule.RuleScript") 
				.close()

			
			.close()
					
		.build();
		
		process( request, output);
	}
	
	
}
