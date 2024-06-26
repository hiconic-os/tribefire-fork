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
package tribefire.extension.xml.schemed.test.roundtrip.funds;

import java.util.Arrays;
import org.junit.experimental.categories.Category;
import com.braintribe.testing.category.KnownIssue;

@Category(KnownIssue.class)
public class FundsLab extends AbstractFundsLab{

	private static final String MODEL_NAME = "com.braintribe.schemedxml.swift.pacs:PacsFlatModel#1.0";
	private static final String MAIN_XSD = "FundsXML_4.1.3.xsd";
	private static final String PACKAGE_NAME = "com.braintribe.schemedxml.funds";
	private static final String MAIN_XML = "FundsXML4_Sample_Document.xml";
	
//
	public void test_sample() {		
		
		runRoundTrip(funds, PACKAGE_NAME, MAIN_XSD, Arrays.asList(MAIN_XML), MODEL_NAME);
	}
	
}
