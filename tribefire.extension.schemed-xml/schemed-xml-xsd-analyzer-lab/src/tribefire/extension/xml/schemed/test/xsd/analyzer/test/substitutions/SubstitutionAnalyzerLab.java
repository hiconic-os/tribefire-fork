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
package tribefire.extension.xml.schemed.test.xsd.analyzer.test.substitutions;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.builder.AnalyzerRequestBuilder;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class SubstitutionAnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "substitution");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}
	
	@Test
	public void flat_SimpleSubstitution() {
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
					.file(new File( input, "subst.simple.1.xsd"))
				.close()
				.packageName("com.braintribe.xsd")
				.modelName( "com.braintribe.xsd.subst:Subst#1.0")
				.substitutionModel()
					.modelName("injected:Injected#1.0")
					.substitution()
						.schemaAddress()
							.type("Root")
							.property("bee")
						.close()
						.replacementSignature("injected.InjectOne")
					.close()
				.close()
		.build();
												
		process( request, output);
	}
}
