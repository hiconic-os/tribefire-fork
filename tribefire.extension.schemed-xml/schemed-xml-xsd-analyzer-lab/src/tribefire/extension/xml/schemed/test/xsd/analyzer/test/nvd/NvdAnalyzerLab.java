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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.nvd;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class NvdAnalyzerLab extends AbstractXsdAnalyzerLab{
	private static File nvd = new File( contents, "nvd");
	private static File input = new File( nvd, "input");
	private static File output = new File( nvd, "output");
	

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}

	@Test
	public void testNvdCveFeedStructured() {
		List<String> refs = new ArrayList<>();
		refs.add( "vulnerability_0.4.xsd");
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.nvd", "nvd-cve-feed_2.0.xsd", refs, "com.braintribe.xsd:NvdStructuredModel#1.0");	
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	
	@Test
	public void testNvdCveFeedFlattend() {
		List<String> refs = new ArrayList<>();
		refs.add( "vulnerability_0.4.xsd");
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.nvd", "nvd-cve-feed_2.0.xsd", refs, "com.braintribe.xsd:NvdFlatModel#1.0");
		process( request, output);
	}

}
