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
package tribefire.extension.xml.schemed.marshaller.xml;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import com.braintribe.codec.marshaller.api.DecodingLenience;
import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.meta.GmMetaModel;

import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerMarshallRequest;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerMarshallResponse;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerUnmarshallRequest;
import tribefire.extension.xml.schemed.model.api.xml.marshaller.api.model.SchemedXmlMarshallerUnmarshallResponse;

/**
 * a processor handle the two features, in & out
 * 
 * @author pit
 *
 */
public class SchemedXmlMarshallingRequestProcessor {

	private SchemedXmlMarshaller marshaller;
	private GmMetaModel mappingModel;
	private ReentrantLock lock = new ReentrantLock();

	private synchronized void initialize(GmMetaModel model) {
		if (mappingModel == null || mappingModel != model) {
			lock.lock();
			try {
				if (mappingModel == null) {
					mappingModel = model;
					marshaller = new SchemedXmlMarshaller();
					marshaller.setMappingMetaModel(mappingModel);
				} else if (mappingModel != model) {
					mappingModel = model;
					marshaller = new SchemedXmlMarshaller();
					marshaller.setMappingMetaModel(mappingModel);
				}
			} finally {
				lock.unlock();
			}
		}
	}

	/**
	 * processor for unmarshalling request
	 * 
	 * @param request
	 *            - {@link SchemedXmlMarshallerUnmarshallRequest}
	 * @return - {@link SchemedXmlMarshallerUnmarshallResponse}
	 */
	public SchemedXmlMarshallerUnmarshallResponse process(SchemedXmlMarshallerUnmarshallRequest request) {

		initialize(request.getMappingModel());

		DecodingLenience lenience = new DecodingLenience();
		lenience.setLenient(request.getIsLenient());
		GenericEntity result = (GenericEntity) marshaller.unmarshall(request.getXml().openStream(),
				GmDeserializationOptions.deriveDefaults().setDecodingLenience(lenience).build());

		SchemedXmlMarshallerUnmarshallResponse response = SchemedXmlMarshallerUnmarshallResponse.T.create();
		response.setAssembly(result);

		return response;
	}

	/**
	 * processor for the marshalling request
	 * 
	 * @param request
	 *            - the {@link SchemedXmlMarshallerMarshallRequest}
	 * @return - the {@link SchemedXmlMarshallerMarshallResponse}
	 */
	public SchemedXmlMarshallerMarshallResponse process(SchemedXmlMarshallerMarshallRequest request) {
		SchemedXmlMarshallerMarshallResponse response = SchemedXmlMarshallerMarshallResponse.T.create();
		initialize(request.getMappingModel());
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			marshaller.marshall(out, request.getAssembly());
			response.setExpression(new String(out.toByteArray()));
		} catch (IOException e) {
			throw new SchemedXmlMarshallingException(e);
		}

		return response;
	}
}
