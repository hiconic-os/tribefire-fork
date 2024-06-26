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

import org.junit.Test;

import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.test.marshaller.xsd.AbstractXsdMarshallerTest;

public class MultipleAnyXsdTest extends AbstractXsdMarshallerTest {
	private File simple = new File( contents, "any");
	private File input = new File( simple, "input");
	private File output = new File( simple, "output");
	
	private File in = new File( input, "any.multiple.xsd");
	private File out = new File( output, "any.multiple.xsd");
	
	@Test
	public void roundtrip() {
		Schema schema = readFile( in);
		System.out.println( schema);
		ensure( output);
		writeFile( out, schema);
		compare(in, out);
	}

}
