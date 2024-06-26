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
package tribefire.extension.xml.schemed.test.marshaller.xsd.swift;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.test.marshaller.xsd.AbstractXsdMarshallerTest;

public class SwiftXsdTest extends AbstractXsdMarshallerTest {
	private static final String SWIFT_MESSAGE_XSD = "SwiftMessage.xsd";
	private static final String PAIN_001_001_03_ISO_XSD = "pain.001.001.03.ISO.xsd";
	private static final String PACS_008_001_01_ISO_XSD = "pacs.008.001.01.ISO.xsd";
	private static File simple = new File( contents, "swift");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");
	
	@BeforeClass
	public static void before() {
		ensure( output);		
	}
	
	
	@Test
	public void roundtripPacs() {
		File in = new File( input, PACS_008_001_01_ISO_XSD);
		Schema schema = readFile( in);
		System.out.println( schema);
		File out = new File( output, PACS_008_001_01_ISO_XSD);
		writeFile( out, schema);
		compare( in, out);
	}
	
	@Test
	public void roundtripPain() {
		File in = new File( input, PAIN_001_001_03_ISO_XSD);
		Schema schema = readFile( in);
		System.out.println( schema);	
		File out = new File( output, PAIN_001_001_03_ISO_XSD);
		writeFile( out, schema);
		compare( in, out);
	}
	
	@Test
	public void roundtripMsg() {
		File in = new File( input, SWIFT_MESSAGE_XSD);
		Schema schema = readFile( in);
		System.out.println( schema);		
		File out = new File( output, SWIFT_MESSAGE_XSD);
		writeFile( out, schema);
		compare( in, out);
	}


 
	public void failTest()  {
		File in = new File( input, PACS_008_001_01_ISO_XSD);
		File out = new File( input, PAIN_001_001_03_ISO_XSD);
		compare( in, out);
	}
}
