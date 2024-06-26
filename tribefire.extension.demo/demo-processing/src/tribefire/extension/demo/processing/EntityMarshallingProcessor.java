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
package tribefire.extension.demo.processing;

import java.io.ByteArrayOutputStream;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.marshaller.api.CharacterMarshaller;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.codec.marshaller.api.OutputPrettiness;
import com.braintribe.model.processing.service.api.ServiceRequestContext;
import com.braintribe.model.processing.service.impl.AbstractDispatchingServiceProcessor;
import com.braintribe.model.processing.service.impl.DispatchConfiguration;

import tribefire.extension.demo.model.api.EntityMarshallingRequest;
import tribefire.extension.demo.model.api.EntityMarshallingResponse;
import tribefire.extension.demo.model.api.MarshallEntityToJson;
import tribefire.extension.demo.model.api.MarshallEntityToXml;
import tribefire.extension.demo.model.api.MarshallEntityToYaml;

public class EntityMarshallingProcessor extends AbstractDispatchingServiceProcessor<EntityMarshallingRequest, EntityMarshallingResponse>  {
	
	private CharacterMarshaller jsonMarshaller;
	private CharacterMarshaller xmlMarshaller;
	private CharacterMarshaller yamlMarshaller;
	
	@Configurable
	@Required
	public void setJsonMarshaller(CharacterMarshaller jsonMarshaller) {
		this.jsonMarshaller = jsonMarshaller;
	}

	@Configurable
	@Required
	public void setXmlMarshaller(CharacterMarshaller xmlMarshaller) {
		this.xmlMarshaller = xmlMarshaller;
	}

	@Configurable
	@Required
	public void setYamlMarshaller(CharacterMarshaller yamlMarshaller) {
		this.yamlMarshaller = yamlMarshaller;
	}

	@Override
	protected void configureDispatching(DispatchConfiguration<EntityMarshallingRequest, EntityMarshallingResponse> dispatching) {
		dispatching.register(MarshallEntityToJson.T, this::marshallEntityToJson);
		dispatching.register(MarshallEntityToXml.T, this::marshallEntityToXml);
		dispatching.register(MarshallEntityToYaml.T, this::marshallEntityToYaml);
		
	}
	
	private EntityMarshallingResponse marshallEntityToJson(@SuppressWarnings("unused") ServiceRequestContext context, MarshallEntityToJson request) {
		return marshall(request, jsonMarshaller);
	}

	private EntityMarshallingResponse marshallEntityToXml(@SuppressWarnings("unused") ServiceRequestContext context, MarshallEntityToXml request) {
		return marshall(request, xmlMarshaller);
	}
	
	private EntityMarshallingResponse marshallEntityToYaml(@SuppressWarnings("unused") ServiceRequestContext context, MarshallEntityToYaml request) {
		return marshall(request, yamlMarshaller);
	}
	
	private EntityMarshallingResponse marshall(EntityMarshallingRequest request, CharacterMarshaller marshaller) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		marshaller.marshall(stream, request.getEntity(), GmSerializationOptions.deriveDefaults().outputPrettiness(OutputPrettiness.high).build());
		
		EntityMarshallingResponse response = EntityMarshallingResponse.T.create();
		response.setMarshalledEntity(stream.toString());
		
		return response;
	}

}
