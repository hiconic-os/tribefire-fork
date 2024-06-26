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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.generics;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class NamingAnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "naming");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");
	
		
	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}


	private void structured_Naming(String packageSuffix, String xsd, String model) {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.naming." + packageSuffix + ".flat", xsd + ".xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:" + model + "StructuredModel#1.0");
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	private void flat_Naming(String packageSuffix, String xsd, String model) {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.naming." + packageSuffix + ".structured", xsd + ".xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:" + model + "FlattenedModel#1.0");
		process( request, output);
	}
	
	@Test
	public void flatSimpleNaming() {
		flat_Naming("simple", "naming", "Naming");
	}
	@Test
	public void structuredSimpleNaming() {
		structured_Naming("simple", "naming", "Naming");
	}
	
	@Test
	public void flatVirtualNaming() {
		flat_Naming("virtual", "virtualNaming", "VirtualNaming");
	}
	@Test
	public void structuredVirtualNaming() {
		structured_Naming("virtual", "virtualNaming", "VirtualNaming");
	}
	
	@Test
	public void flatPropagateNaming() {
		flat_Naming("propagate", "propagateNaming", "PropagateNaming");
	}
	@Test
	public void structuredPropagateNaming() {
		structured_Naming("propagate", "propagateNaming", "PropagateNaming");
	}
	
	
}
