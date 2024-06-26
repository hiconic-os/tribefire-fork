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
package tribefire.extension.xml.schemed.test.roundtrip.swift;

import java.io.File;
import java.util.Arrays;

import org.junit.Test;
import org.junit.experimental.categories.Category;

import com.braintribe.testing.category.KnownIssue;
@Category(KnownIssue.class)
public class PacsLab extends AbstractSwiftLab{

	private static final String COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PACS_PACS_FLAT_MODEL_1_0 = "com.braintribe.schemedxml.swift.pacs:PacsFlatModel#1.0";
	private static final String PACS_008_001_01_ISO_XSD = "pacs.008.001.01.ISO.xsd";
	private static final String COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PACS = "com.braintribe.schemedxml.swift.pacs";
	private File workingDirectory = new File( swift, "pacs");
	
	@Test
	public void test_Pacs_a() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PACS, PACS_008_001_01_ISO_XSD, Arrays.asList("pacs.008.01.01.a.xml", "pacs.008.01.01.a.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PACS_PACS_FLAT_MODEL_1_0);
	}
	@Test
	public void test_Pacs_b() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PACS, PACS_008_001_01_ISO_XSD, Arrays.asList("pacs.008.01.01.b.xml", "pacs.008.01.01.b.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PACS_PACS_FLAT_MODEL_1_0);
	}
}
