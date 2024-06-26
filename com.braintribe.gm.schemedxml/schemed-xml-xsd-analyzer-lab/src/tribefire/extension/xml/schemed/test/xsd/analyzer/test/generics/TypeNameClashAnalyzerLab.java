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

public class TypeNameClashAnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "typeClash");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");
	
	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}

	@Test
	public void structured_TypeClash() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.typeclash.structured", "typeclash.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:TypeClashModel#1.0");
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	
	@Test
	public void flat_TypeClash() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.typeclash.flat", "typeclash.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:TypeClashFlatModel#1.0");
		process( request, output);
	}
	
	@Test
	public void structured_TypeAndAttrClash() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.typeattr.clash.structured", "typeAndAttributeClash.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:TypeAndAttrClashModel#1.0");
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	
	@Test
	public void flat_TypeAndAttrClash() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.typeattr.clash.flat", "typeAndAttributeClash.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:TypeAndAttrClashFlatModel#1.0");
		process( request, output);
	}
	
	@Test
	public void structured_TypeAndDivAttrClash() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.typeattrdiv.clash.structured", "typeAndAttributeClash2.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:TypeAndAttrDivClashModel#1.0");
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	
	@Test
	public void flat_TypeAndDivAttrClash() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.typeattrdiv.clash.flat", "typeAndAttributeClash2.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:TypeAndAttrDivClashFlatModel#1.0");
		process( request, output);
	}
	
	@Test
	public void structured_SingleTypeAndDivAttrClash() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.singletypeattrdiv.clash.structured", "typeAndAttributeClash3.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:SingleTypeAndAttrDivClashModel#1.0");
		request.setExposeChoice(true);
		request.setExposeSequence(true);
		process( request, output);
	}
	
	@Test
	public void flat_SingleTypeAndDivAttrClash() {
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.singletypeattrdiv.clash.flat", "typeAndAttributeClash3.xsd", java.util.Collections.emptyList(), "com.braintribe.xsd:SingleTypeAndAttrDivClashFlatModel#1.0");
		process( request, output);
	}
}
