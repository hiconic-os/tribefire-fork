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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.issues.swift;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class Swift_CurrencyAndAmountAsRootLab extends AbstractXsdAnalyzerLab {
	private static final String TEST_MODEL = "com.braintribe.xsd.swift.issue.one:SwiftIssueOneFlatModel#1.0";
	private static final String TEST_XSD = "swiftIssue.1.xsd";
	private static String TEST_PACKAGE = "com.braintribe.xsd.swift.issue.one";	
	private static File simple = new File( contents, "issues/one");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}

	@Test
	public void flat_Simple() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, TEST_PACKAGE, TEST_XSD, java.util.Collections.emptyList(), TEST_MODEL);		
		process( request, output);
	}
	
	
}
