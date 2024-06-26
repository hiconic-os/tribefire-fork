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

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.CollectionOverride;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.xsd.test.util.TestUtil;
import tribefire.extension.xml.schemed.test.xsd.analyzer.test.AbstractXsdAnalyzerLab;

public class OverrideListLab extends AbstractXsdAnalyzerLab {
	private static File simple = new File( contents, "overrideList");
	private static File input = new File( simple, "input");
	private static File output = new File( simple, "output");
	

	@BeforeClass
	public static void beforeClass() {
		TestUtil.ensure(output);
	}

	@Test
	public void overrideCollectionTypeTest() {
		List<String> refs = new ArrayList<>();
		refs.add( "import.xsd");
		SchemedXmlXsdAnalyzerRequest request = buildPrimerRequest( input, "com.braintribe.override.collection", "overrideName.xsd", refs, "com.braintribe.xsd:OverrideCollectionModel#1.0");
		
		CollectionOverride collectionOverride = CollectionOverride.T.create();
		collectionOverride.setCollectionAsSet(true);
		SchemaAddress schemaAddress2 = SchemaAddress.T.create();
		schemaAddress2.setElement("b");
		schemaAddress2.setParent("Child");
		collectionOverride.setSchemaAddress(schemaAddress2);
		request.getCollectionOverrides().add( collectionOverride);
		
		process( request, output);
	}
}
