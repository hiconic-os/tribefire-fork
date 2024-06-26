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
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.MappingOverride;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class OverrideNameAnalyzerLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "overrideName");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");
	

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}

	@Test
	public void overrideNamesTest() {
		List<String> refs = new ArrayList<>();
		refs.add( "import.xsd");
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.override.name", "overrideName.xsd", refs, "com.braintribe.xsd:OverrideNameFlatModel#1.0");
		
		MappingOverride override1 = MappingOverride.T.create();
		override1.setNameOverride("overridenA");
		SchemaAddress schemaAddress1 = SchemaAddress.T.create();
		schemaAddress1.setElement("a");
		schemaAddress1.setParent("Root");
		override1.setSchemaAddress(schemaAddress1);
		
		request.getMappingOverrides().add( override1);
		
		MappingOverride override2 = MappingOverride.T.create();
		override2.setNameOverride("overridenB");
		SchemaAddress schemaAddress2 = SchemaAddress.T.create();
		schemaAddress2.setElement("b");
		schemaAddress2.setParent("Child");
		override2.setSchemaAddress(schemaAddress2);
		
		request.getMappingOverrides().add( override2);

		MappingOverride override3 = MappingOverride.T.create();
		override3.setNameOverride("Kind");
		SchemaAddress schemaAddress3 = SchemaAddress.T.create();
		schemaAddress3.setParent("Child");
		override3.setSchemaAddress(schemaAddress3);
		
		request.getMappingOverrides().add( override3);
		process( request, output);
	}
}
