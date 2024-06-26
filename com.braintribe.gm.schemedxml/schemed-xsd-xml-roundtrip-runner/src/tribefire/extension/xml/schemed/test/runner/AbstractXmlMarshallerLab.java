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
package tribefire.extension.xml.schemed.test.runner;

import java.io.File;

import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.utils.xml.parser.DomParser;
import com.braintribe.utils.xml.parser.DomParserException;

import tribefire.extension.xml.schemed.marshaller.xml.SchemedXmlMarshallingRequestProcessor;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerMarshallRequest;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerMarshallResponse;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerUnmarshallRequest;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerUnmarshallResponse;
import tribefire.extension.xml.schemed.test.commons.commons.SchemedXmlXsdMarshallerRequestBuilder;
import tribefire.extension.xml.schemed.test.commons.xsd.test.resource.ResourceProvidingSession;

public abstract class AbstractXmlMarshallerLab {
	protected static File res = new File( "res");
	private ResourceProvidingSession session = new ResourceProvidingSession();
	private StaxMarshaller modelMarshaller = new StaxMarshaller();
	private SchemedXmlXsdMarshallerRequestBuilder requestBuilder;
	private SchemedXmlMarshallingRequestProcessor requestProcessor = new SchemedXmlMarshallingRequestProcessor();
	
	public SchemedXmlXsdMarshallerRequestBuilder getRequestBuilder() {
		if (requestBuilder == null) {
			requestBuilder = new SchemedXmlXsdMarshallerRequestBuilder();
			requestBuilder.setSession(session);
			requestBuilder.setModelMarshaller( modelMarshaller);
		}
		return requestBuilder;
	}
	

	private void initialize() {
		
	}

	protected SchemedXmlMarshallerUnmarshallRequest buildRequest(File input, String xsdName, String model) {		
		return getRequestBuilder().buildRequest(input, xsdName, model);				
	}
	
	protected SchemedXmlMarshallerUnmarshallResponse process( SchemedXmlMarshallerUnmarshallRequest request) {
		initialize();
		return requestProcessor.process(request);
	}
	
	protected SchemedXmlMarshallerMarshallRequest buildRequest(File input, GenericEntity assembly, String model) {
		return getRequestBuilder().buildRequest(input, assembly, model);		
	}
	protected SchemedXmlMarshallerMarshallResponse process( SchemedXmlMarshallerMarshallRequest request) {
		initialize();
		return requestProcessor.process(request);
	}
	
	
	protected boolean validate( File xsdFile, File xmlFile) {
		try {
			return DomParser.validate().from(xmlFile).schema(xsdFile).makeItSo();
		} catch (DomParserException e) {
			throw new IllegalStateException(e);		
		}		
	}
}
