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
package tribefire.extension.xml.schemed.xml.marshaller.test;

import java.io.File;

import org.junit.Assert;
import org.junit.experimental.categories.Category;

import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.testing.category.KnownIssue;
import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

import tribefire.extension.xml.schemed.marshaller.xml.SchemedXmlMarshallingRequestProcessor;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerMarshallRequest;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerMarshallResponse;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerUnmarshallRequest;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerUnmarshallResponse;
import tribefire.extension.xml.schemed.test.commons.commons.ArchiveValidator;
import tribefire.extension.xml.schemed.test.commons.commons.SchemedXmlXsdMarshallerRequestBuilder;
import tribefire.extension.xml.schemed.test.commons.xsd.test.resource.ResourceProvidingSession;

@Category(KnownIssue.class)
public abstract class AbstractXmlMarshallerLab {
	protected static File res = new File( "res");
	private ResourceProvidingSession session = new ResourceProvidingSession();
	private StaxMarshaller modelMarshaller = new StaxMarshaller();
	private SchemedXmlMarshallingRequestProcessor marshaller = new SchemedXmlMarshallingRequestProcessor();
	private SchemedXmlXsdMarshallerRequestBuilder requestBuilder;
	
	public SchemedXmlXsdMarshallerRequestBuilder getRequestBuilder() {
		if (requestBuilder == null) {
			requestBuilder = new SchemedXmlXsdMarshallerRequestBuilder();
			requestBuilder.setSession(session);
			requestBuilder.setModelMarshaller( modelMarshaller);
		}
		return requestBuilder;
	}

	protected SchemedXmlMarshallerUnmarshallRequest buildRequest(File input, String xsdName, String model) {		
		return getRequestBuilder().buildRequest(input, xsdName, model);				
	}
	
	protected SchemedXmlMarshallerUnmarshallResponse process( SchemedXmlMarshallerUnmarshallRequest request) {
		return marshaller.process(request);
	}
	
	protected SchemedXmlMarshallerMarshallRequest buildRequest(File input, GenericEntity assembly, String model) {
		return getRequestBuilder().buildRequest(input, assembly, model);		
	}
	protected SchemedXmlMarshallerMarshallResponse process( SchemedXmlMarshallerMarshallRequest request) {
		return marshaller.process(request);
	}
	
	
	protected boolean validate( File xsd, File xml) {
		if (!xsd.getName().endsWith( ".xsd")) 
			return ArchiveValidator.validate(xsd, xml);
		else {
			try {
				return DomParser.validate().from(xml).schema( xsd).makeItSo();
			} catch (DomParserException e) {
				return false;
			}
		}
			
	}
	
	protected void assertValid( File xsdFile, File xmlFile) {
		try {
			if (!DomParser.validate().from(xmlFile).schema(xsdFile).makeItSo()) {
				Assert.fail("validation if [" + xmlFile.getName() + "] vs [" + xsdFile.getName() + "] failed");
			}				
		} catch (DomParserException e) {
			Assert.fail("validation failed as [" + e.getLocalizedMessage() + "]");
		}
		
	}
}
