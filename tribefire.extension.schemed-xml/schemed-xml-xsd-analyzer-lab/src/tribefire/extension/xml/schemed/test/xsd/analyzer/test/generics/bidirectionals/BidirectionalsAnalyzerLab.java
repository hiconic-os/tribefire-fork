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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.generics.bidirectionals;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.BidirectionalLink;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class BidirectionalsAnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "bidirectionals");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}
	
	@Test
	public void flat_bidirectional1() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.bidi.unique", "link.1.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:BidiUniqueFlatModel#1.0");

		BidirectionalLink link = BidirectionalLink.T.create();
		
		SchemaAddress schemaAddress1 = SchemaAddress.T.create();
		schemaAddress1.setElement("singleChild");
		schemaAddress1.setParent("Root");
		link.setSchemaAddress(schemaAddress1);		
		link.setBacklinkProperty( "root");
		
		request.getBidirectionalLinks().add(link);
		process( request, output);
	}
	
	@Test
	public void flat_bidirectional2() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.bidi.duplicate", "link.2.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:BidiDuplicateFlatModel#1.0");

		BidirectionalLink link = BidirectionalLink.T.create();
		
		SchemaAddress schemaAddress1 = SchemaAddress.T.create();
		schemaAddress1.setElement("singleChild");
		schemaAddress1.setParent("Root");
		link.setSchemaAddress(schemaAddress1);		
		link.setBacklinkProperty( "root");
		
		request.getBidirectionalLinks().add(link);
		
		link = BidirectionalLink.T.create();
		SchemaAddress schemaAddress2 = SchemaAddress.T.create();
		schemaAddress2.setElement("multipleChild");
		schemaAddress2.setParent("Root");
		link.setSchemaAddress(schemaAddress2);		
		link.setBacklinkProperty( "root");
		
		request.getBidirectionalLinks().add(link);
		process( request, output);
	}

	
}
