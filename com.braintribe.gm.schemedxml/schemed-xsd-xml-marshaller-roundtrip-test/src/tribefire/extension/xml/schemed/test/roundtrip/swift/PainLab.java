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
public class PainLab extends AbstractSwiftLab{

	private static final String COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN_PAIN_FLAT_MODEL_1_0 = "com.braintribe.schemedxml.swift.pain:PainFlatModel#1.0";
	private static final String PAIN_001_001_03_ISO_XSD = "pain.001.001.03.ISO.xsd";
	private static final String PAIN_001_001_02_ISO_XSD = "pain.001.001.02.ISO.xsd";
	private static final String COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN = "com.braintribe.schemedxml.swift.pain";
	private File workingDirectory = new File( swift, "pain");
	
	@Test
	public void test_Pain_1() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN, PAIN_001_001_03_ISO_XSD, Arrays.asList("pain.1.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN_PAIN_FLAT_MODEL_1_0);
	}
	@Test
	public void test_Pain_2() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN, PAIN_001_001_03_ISO_XSD, Arrays.asList("pain.2.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN_PAIN_FLAT_MODEL_1_0);
	}
	@Test
	public void test_Pain_3() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN, PAIN_001_001_03_ISO_XSD, Arrays.asList("pain.3.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN_PAIN_FLAT_MODEL_1_0);
	}
	
	//@Test
	public void test_Pain_parallel() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN, PAIN_001_001_03_ISO_XSD, Arrays.asList("pain.1.xml", "pain.2.xml", "pain.3.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN_PAIN_FLAT_MODEL_1_0, 1, 0);
	}
	
	@Test
	public void test_Pain_bulk_1000() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN, PAIN_001_001_03_ISO_XSD, Arrays.asList("pain.bulk.1000.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN_PAIN_FLAT_MODEL_1_0, 110,10);
	}
	//@Test
	public void test_Pain_bulk_1000_p() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN, PAIN_001_001_03_ISO_XSD, Arrays.asList("pain.bulk.1000.xml", "pain.bulk.1000.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN_PAIN_FLAT_MODEL_1_0);
	}
	
	//@Test
	public void test_Pain_bulk_400000() {		
		runRoundTrip(workingDirectory, COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN, PAIN_001_001_02_ISO_XSD, Arrays.asList("pain.bulk.400000.xml"), COM_BRAINTRIBE_SCHEMEDXML_SWIFT_PAIN_PAIN_FLAT_MODEL_1_0);
	}
}
