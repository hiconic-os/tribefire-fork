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
package tribefire.extension.xml.schemed.test.requestbuilder;

import java.io.File;

import org.junit.Test;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;
import tribefire.extension.xml.schemed.requestbuilder.builder.AnalyzerRequestBuilder;
import tribefire.extension.xml.schemed.test.requestbuilder.validator.Validator;

/**
 * not only (not yet) a lab, but a place to show examples of the builder for {@link SchemedXmlXsdAnalyzerRequest}
 * 
 * @author pit
 *
 */
public class AnalyzerRequestBuilderLab {

	
	/**
	 * the simplest form of a request: one XSD file into one model 
	 */
	@Test
	public void singleXSD() {
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
					.file( new File( "main.xsd"))
				.close()
				.modelName("my-group:my-artifact#1.0")
				.packageName("com.braintribe.xsd")
			.build();
		
		Validator.validate(request);
	}
	
	/**
	 * multiple XSD into one model, XSD are packaged into ZIP
	 */
	@Test
	public void multipleXSDinZip() {
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
					.archive( new File( "archive.zip"), "Schemas/gs1/ecom/main.xsd")
				.close()
				.modelName("my-group:my-artifact#1.0")
				.packageName("com.braintribe.xsd")
			.build();
		Validator.validate(request);
	}
	
	/**
	 * multiple XSD into one model, XSD are single files
	 */
	@Test
	public void multipleXSD() {	
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
				.file( new File( "main.xsd"))
				.close()
				.modelName("my-group:my-artifact#1.0")
				.packageName("com.braintribe.xsd")
				.references()
				.file( new File( "import1.xsd"), "import1.xsd")
				.file( new File( "import2.xsd"), "import2.xsd")
				.close()
				
				.build();
		
		Validator.validate( request);		
	}
	
	/**
	 * overriding the names of types and properties
	 */
	@Test
	public void nameOverrides() {
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
					.file( new File( "main.xsd"))
				.close()
				.modelName("my-group:my-artifact#1.0")
				.packageName("com.braintribe.xsd")
				
				.nameOverride()
					.overridingName("myFirstName")
			
					.schemaAddress()
						.type("first-xsd-type")
						.property("first-xsd-property")
					.close()
			
				.close()
				
				.nameOverride()
					.overridingName("mySecondName")
			
					.schemaAddress()
						.type("second-xsd-type")
						.property("second-xsd-property")
					.close()		
				.close()				
			.build();
		Validator.validate(request);
	}
	
	/**
	 * overriding the types of collections (from List to Set)
	 */
	@Test
	public void collectionOverrides() {
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
					.file( new File( "main.xsd"))
				.close()
				.modelName("my-group:my-artifact#1.0")
				.packageName("com.braintribe.xsd")
				
				.collectionOverride()
					.asSet()			
					.schemaAddress()
						.type("xsd-type")
						.property("collection-property")
					.close()
			
				.close()
											
			.build();
		Validator.validate(request);
	}

	
	/**
	 * wire bidirectional properties 
	 */
	@Test
	public void bidirectionals() {
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
					.file( new File( "main.xsd"))
				.close()
				.modelName("my-group:my-artifact#1.0")
				.packageName("com.braintribe.xsd")
				.bidirectional()
					.schemaAddress()
						.type( "first-xsd-type")
						.property( "first-xsd-collection")
					.close()
					.property( "first-backlink-property")
				.close()
				.bidirectional()
				.schemaAddress()
					.type( "second-xsd-type")
					.property( "second-xsd-collection")
				.close()
				.property( "second-backlink-property")
			.close()
		.build();
		
		Validator.validate( request);
	}
	
	
	/*
	 * substitution 
	 */
	@Test
	public void substitution() {
		SchemedXmlXsdAnalyzerRequest request = AnalyzerRequestBuilder.request()
				.xsd()
					.file( new File( "main.xsd"))
				.close()
				.modelName("my-group:my-artifact#1.0")
				.packageName("com.braintribe.xsd")
				.substitutionModel()
					.modelName("my-substitution:model")
					.substitution()
						.replacementSignature("my.injected.package.one.SubstitutionOne")					
						.schemaAddress()
							.type( "first-xsd-type")							
						.close()
					.close()
					.substitution()
						.replacementSignature("my.injected.package.one.SubstitutionTwo")						
						.schemaAddress()
							.type( "second-xsd-type")
							.property( "second-xsd-collection")
						.close()
					.close()
				.close()				
			.build();
		
		Validator.validate( request);
	}

}
