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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.addidas;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class AddiasLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "addidas");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}


	@Test
	public void inboundASNBridge() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.addias.inbound.asn.bridge", "InboundASNBridge.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:InBoundASNBridgeModel#1.0");
		process( request, output);
	}
	
	@Test
	public void invoiceBridge() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.addias.invoice.bridge", "InvoiceBridge.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:InvoiceBridgeModel#1.0");
		process( request, output);
	}
	@Test
	public void itemMasterBridge() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.addias.item.master.bridge", "ItemMasterBridge.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:ItemMasterBridgeModel#1.0");
		process( request, output);
	}
	@Test
	public void pickTicketBridge() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.addias.pick.ticket.bridge", "PickTicketBridge.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:PickTicketBridgeModel#1.0");
		process( request, output);
	}	
	@Test
	public void pixBridge() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.addias.pix.bridge", "PixBridge.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:PixBridgeModel#1.0");
		process( request, output);
	}

}