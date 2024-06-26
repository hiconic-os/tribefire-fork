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
package com.braintribe.codec.marshaller.stax.decoder.collection;

import java.util.HashSet;
import java.util.Set;

import org.xml.sax.Attributes;

import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.stax.EntityRegistrationListener;
import com.braintribe.codec.marshaller.stax.decoder.Decoder;
import com.braintribe.codec.marshaller.stax.factory.DecoderFactory;
import com.braintribe.codec.marshaller.stax.factory.DecoderFactoryContext;
import com.braintribe.codec.marshaller.stax.factory.ObjectDecoderFactory;
import com.braintribe.model.generic.GenericEntity;

public class SetDecoder extends Decoder {
	private Set<Object> set;
	private DecoderFactory elementDecoderFactory;
	private int childCount;
	
	public SetDecoder(DecoderFactory elementDecoderFactory) {
		this.elementDecoderFactory = elementDecoderFactory;
	}
	
	public SetDecoder() {
		this.elementDecoderFactory = ObjectDecoderFactory.INSTANCE;
	}
	
	@Override
	public Decoder newDecoder(DecoderFactoryContext context, String elementName, Attributes attributes) throws MarshallException {
		childCount++;
		return elementDecoderFactory.newDecoder(context, elementName, attributes);
	}

	@Override
	public void end() throws MarshallException {
		if (childCount == 0 && elementName.equals("null"))
			parent.notifyValue(this, null);
		else
			parent.notifyValue(this, getSet());
	}
	
	@Override
	public void notifyValue(Decoder origin, Object value) {
		getSet().add(value);
	}
	
	private Set<Object> getSet() {
		if (set == null) {
			set = new HashSet<Object>();
		}

		return set;
	}
	
	@Override
	public void notifyForwardEntity(Decoder origin, String referenceId) {
		final Set<Object> localSet = getSet();
		
		decodingContext.addEntityRegistrationListener(referenceId, new EntityRegistrationListener() {
			
			@Override
			public void onEntityRegistered(GenericEntity entity) throws MarshallException {
				localSet.add(entity);
			}
		});
	}
	

}
