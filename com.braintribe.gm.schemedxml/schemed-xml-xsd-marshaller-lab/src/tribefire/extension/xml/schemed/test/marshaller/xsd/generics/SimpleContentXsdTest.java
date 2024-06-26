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
package tribefire.extension.xml.schemed.test.marshaller.xsd.generics;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.test.marshaller.xsd.AbstractXsdMarshallerTest;

public class SimpleContentXsdTest extends AbstractXsdMarshallerTest {
	private static File simple = new File( contents, "simpleContent");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");
	
	
	@BeforeClass
	public static void before() {
		ensure( output);
	}
	@Test
	public void roundtrip() {
		Schema schema = readFile( new File( input, "simpleContent.xsd"));
		System.out.println( schema);		
		writeFile( new File( output, "simpleContent.xsd"), schema);
		
	}
	
	@Test
	public void roundtripDerived() {
		Schema schema = readFile( new File( input, "simpleContentDerived.xsd"));
		System.out.println( schema);
		writeFile( new File( output, "simpleContentDerived.xsd"), schema);
		
	}
	
	@Test
	public void roundtripRedirect() {
		File in = new File( input, "simpleContentRedirect.xsd");
		Schema schema = readFile( in);
		System.out.println( schema);
		
		File out = new File( output, "simpleContentRedirect.xsd");
		writeFile( out, schema);
		compare(in, out);
		
	}

}
