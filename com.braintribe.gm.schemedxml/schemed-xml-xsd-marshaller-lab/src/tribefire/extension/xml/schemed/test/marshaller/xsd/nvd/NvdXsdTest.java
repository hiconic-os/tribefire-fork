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
package tribefire.extension.xml.schemed.test.marshaller.xsd.nvd;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.test.marshaller.xsd.AbstractXsdMarshallerTest;

public class NvdXsdTest extends AbstractXsdMarshallerTest {
	private static File simple = new File( contents, "nvd");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");
	
	private File vuln_in = new File( input, "vulnerability_0.4.xsd");
	private File vuln_out = new File( output, "vulnerability_0.4.xsd");
	
	private File nvd_in = new File( input, "nvd-cve-feed_2.0.xsd");
	private File nvd_out = new File( output, "nvd-cve-feed_2.0.xsd");
	
	
	@BeforeClass
	public static void before() {
		ensure( output);		
	}

	private void roundtrip(File in, File out) {
		Schema schema = readFile( in);
		System.out.println( schema);
		writeFile( out, schema);
		compare( in, out);
	}
	
	
	@Test
	public void roundtripVuln() {
		roundtrip( vuln_in, vuln_out);
	}
	
	@Test
	public void roundtripNvd() {
		roundtrip( nvd_in, nvd_out);
	}


}
