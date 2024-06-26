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
package com.braintribe.codec.marshaller.stax.v4.decoder.envelope;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.xml.sax.Attributes;

import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.stax.TypeInfo;
import com.braintribe.codec.marshaller.stax.TypeInfo4Read;
import com.braintribe.codec.marshaller.stax.decoder.Decoder;
import com.braintribe.codec.marshaller.stax.factory.DecoderFactoryContext;
import com.braintribe.model.generic.reflection.GenericModelType;

public class RequiredTypesDecoder extends Decoder {
	private Map<String, TypeInfo> requiredTypes = null;
	
	@Override
	public Decoder newDecoder(DecoderFactoryContext context, String _elementName, Attributes attributes) throws MarshallException {
		return new TypeDecoder();
	}
	
	@Override
	public void begin(Attributes attributes) throws MarshallException {
		requiredTypes = new HashMap<String, TypeInfo>();
	}
	
	@Override
	public void end() throws MarshallException {
		Consumer<Set<String>> requiredTypesReceiver = decodingContext.getRequiredTypesReceiver();
		if (requiredTypesReceiver != null) {
			try  {
				requiredTypesReceiver.accept(requiredTypes.keySet());
			}
			catch (Exception e) {
				throw new MarshallException("error while propagating required types to configured receiver", e);
			}
		}
	}
	
	@Override
	public void notifyValue(Decoder origin, Object value) {
		TypeInfo4Read typeInfo = (TypeInfo4Read)value;
		GenericModelType type = typeInfo.type;
		if (type != null)
			requiredTypes.put(typeInfo.typeSignature, typeInfo);
			//requiredTypes.put(type.getTypeSignature(), typeInfo);
		decodingContext.registerTypeInfo(typeInfo);
	}
	
	@Override
	public void notifyForwardEntity(Decoder origin, String referenceId) {
		// suppress
	}
}
