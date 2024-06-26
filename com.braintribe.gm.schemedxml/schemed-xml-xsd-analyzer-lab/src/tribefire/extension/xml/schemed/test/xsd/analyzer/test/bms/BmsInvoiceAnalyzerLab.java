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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.bms;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class BmsInvoiceAnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File working = new File( contents, "BMS/invoice");	
	private static File input = new File( working, "input");
	private static File output = new File( working, "output");
	

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}
	
	@Test
	public void test() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.bms.invoice", "bms.invoice.schemas.zip", "gs1/ecom/Invoice.xsd", "com.braintribe.xsd:BmsInvoiceModel#1.0");		
		process( request, output);
	}

}
