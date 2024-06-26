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

public class SimpleChoiceAnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "simpleChoice");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");
	

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}

	@Test
	public void flat_SimpleChoice() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.simple.choices.flat", "simple.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:SimpleChoiceFlatModel#1.0");		
		process( request, output);
	}
	//@Test
	public void structured_SimpleChoice() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.simple.choices.structured", "simple.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:SimpleChoiceStructuredModel#1.0");
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	
	@Test
	public void flat_SimplestChoice() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.simplest.choices.flat", "simplest.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:SimplestChoiceFlatModel#1.0");		
		process( request, output);
	}
	//@Test
	public void structured_SimplestChoice() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.simplest.choices.structured", "simplest.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:SimplestChoiceStructuredModel#1.0");
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	
	@Test
	public void flat_ReallySimplestChoice() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.really.simplest.choices.flat", "simplest.2.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:ReallySimplestChoiceFlatModel#1.0");		
		process( request, output);
	}
	//@Test
	public void structured_ReallySimplestChoice() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.really.simplest.choices.structured", "simplest.2.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:ReallySimplestChoiceStructuredModel#1.0");
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	
	@Test
	public void flat_ReallySimplestMultipleChoice() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.really.simplest.choices.multiple.flat", "simplest.multiple.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:ReallySimplestMultipleChoiceFlatModel#1.0");		
		process( request, output);
	}
}
