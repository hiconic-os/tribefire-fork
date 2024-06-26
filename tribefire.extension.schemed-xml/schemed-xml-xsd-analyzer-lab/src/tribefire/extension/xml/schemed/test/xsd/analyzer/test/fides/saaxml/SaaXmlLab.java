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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.fides.saaxml;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.builder.AnalyzerRequestBuilder;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.fides.HasFidesTokens;

public class SaaXmlLab extends AbstractXsdAnalyzerLab implements HasFidesTokens{
	private static final String TEST_MODEL = GRP_CREDIT_SUISSE_FOX + ":fox-envelope-model#" + VERSION_CREDIT_SUISSE_FOX;
	private static final String TEST_XSD = "SAA_XML_v2_0_2.xsd";
	private static String TEST_PACKAGE = "com.braintribe.model.fidesv.envelope";	
	private static File fides = new File( contents, "saa_xml");
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
					
		.build();
		
		process( request, output);
	}
	
	
}
