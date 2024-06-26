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
import java.io.IOException;
import java.nio.file.Files;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.test.marshaller.xsd.AbstractXsdMarshallerTest;

public class IncludeXsdTest extends AbstractXsdMarshallerTest {
	private static File include = new File( contents, "include");
	private static File input = new File( include, "input");
	private static File output = new File( include, "output");
	
	
	private File inBase = new File( input, "base.xsd");
	private File outBase = new File( output, "base.xsd");

	@BeforeClass
	public static void before() {
		ensure( output);
	}

	private void roundtrip(File in, File out) {
		Schema schema = readFile( in);
		System.out.println( schema);
		
		
		// copy base 
		try {
			Files.copy( inBase.toPath(), outBase.toPath());
		} catch (IOException e) {		
		}
		writeFile( out, schema);
		compare( in, out);
	}

	@Test
	public void roundTrip1() {
		roundtrip( new File( input, "include.1.xsd"), new File( output, "include.1.xsd"));
	}
	@Test
	public void roundTrip2() {
		roundtrip( new File( input, "include.2.xsd"), new File( output, "include.2.xsd"));
	}
	@Test
	public void roundTrip3() {
		roundtrip( new File( input, "include.3.xsd"), new File( output, "include.3.xsd"));
	}
}
