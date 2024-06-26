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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.generics.any;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class AnytypeElementAnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "anytype.element");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}
	
	@Test
	public void flat_AnyElement() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.anytype.element", "undefined.type.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:AnytypeElementFlatModel#1.0");		
		process( request, output);
	}

	@Test
	public void flat_AnyElementNamespace() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.anytype.element.ns", "undefined.type.ns.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:AnytypeElementNsFlatModel#1.0");		
		process( request, output);
	}
	
	@Test
	public void flat_topAnyElement() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.anytype.element.top", "top.undefined.type.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:AnytypeTopElementFlatModel#1.0");		
		process( request, output);
	}

	@Test
	public void flat_topAnyElementNamespace() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.anytype.element.top.ns", "undefined.type.ns.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:AnytypeTopElementNsFlatModel#1.0");		
		process( request, output);
	}

	
}
