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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.iso13606;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class Iso13606AnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File tarmed = new File( contents, "iso13606");	
	private static File input = new File( tarmed, "input");
	private static File output = new File( tarmed, "output");
	

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}
	
	@Test
	public void testFlattened() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.iso13606", "en13606-XMLSchema-v20150312.zip", "EN13606-RM.xsd", "com.braintribe.xsd:Iso13606Model#1.0");		
		process( request, output);
	}

}
