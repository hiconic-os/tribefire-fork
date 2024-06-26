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
package tribefire.extension.xml.schemed.test.commons.commons;

import java.io.File;
import java.io.FileInputStream;

import com.braintribe.cfg.Configurable;
import com.braintribe.codec.marshaller.stax.StaxMarshaller;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;

import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerMarshallRequest;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerUnmarshallRequest;
import tribefire.extension.xml.schemed.test.commons.xsd.test.resource.ResourceGenerator;
import tribefire.extension.xml.schemed.test.commons.xsd.test.resource.ResourceProvidingSession;

public class SchemedXmlXsdMarshallerRequestBuilder {
	protected static File res = new File( "res");
	private ResourceProvidingSession session = new ResourceProvidingSession();
	private StaxMarshaller modelMarshaller = new StaxMarshaller();
	
	@Configurable
	public void setSession(ResourceProvidingSession session) {
		this.session = session;
	}
	@Configurable
	public void setModelMarshaller(StaxMarshaller modelMarshaller) {
		this.modelMarshaller = modelMarshaller;
	}

	private ResourceProvidingSession getSession() {
		if (session == null) {
			session = new ResourceProvidingSession();
		}
		return session;
	}
	
	private StaxMarshaller getModelMarshaller() {
		if (modelMarshaller == null) {
			modelMarshaller = new StaxMarshaller();
		}
		return modelMarshaller;
	}
	
	
	public SchemedXmlMarshallerUnmarshallRequest buildRequest(File input, String xsdName, String model) {
		SchemedXmlMarshallerUnmarshallRequest request = SchemedXmlMarshallerUnmarshallRequest.T.create();
		
		request.setXml(  ResourceGenerator.filesystemResourceFromFile( getSession(), new File( input, xsdName)));
		
		try (FileInputStream stream = new FileInputStream( new File( input, model))) {
			request.setMappingModel((GmMetaModel) getModelMarshaller().unmarshall(stream));
		}
		catch (Exception e) {
			throw new IllegalStateException("cannot read mapping model", e);
		}		
		return request;		
	}
		
	public SchemedXmlMarshallerMarshallRequest buildRequest(File input, GenericEntity assembly, String model) {
		SchemedXmlMarshallerMarshallRequest request = SchemedXmlMarshallerMarshallRequest.T.create();
		request.setAssembly(assembly);
		try (FileInputStream stream = new FileInputStream( new File( input, model))) {
			request.setMappingModel((GmMetaModel) getModelMarshaller().unmarshall(stream));
		}
		catch (Exception e) {
			throw new IllegalStateException("cannot read mapping model", e);
		}
		
		return request;
	}
		
}
