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
package com.braintribe.gwt.notification.client;

import java.util.Map;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.codec.string.client.StringCodec;
import com.braintribe.gwt.ioc.client.Configurable;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;

/**
 * This codec decodec a {@link JavaScriptObject} via {@link JSONObject} to
 * a {@link Notification} instance. It uses sub codecs configured by {@link #setTypeCodecs(Map)}
 * to decode the notification data depending on the type. 
 * @author Dirk
 *
 */
public abstract class AbstractNotificationCodec<E> implements Codec<Notification<?>, E> {
	private Map<String, Codec<?, String>> typeCodecs;
	private Codec<?, String> defaultTypeCodec = new StringCodec();
	private Codec<JSONObject, E> jsonCodec = null;

	protected void setJsonCodec(Codec<JSONObject, E> jsonCodec) {
		this.jsonCodec = jsonCodec;
	}
	
	/**
	 * Configures the codec that is used if no special association from type to codec
	 * can be found
	 * @see #setTypeCodecs(Map)
	 */
	@Configurable
	public void setDefaultTypeCodec(Codec<?, String> defaultTypeCodec) {
		this.defaultTypeCodec = defaultTypeCodec;
	}
	
	/**
	 * Configures the codecs that are used for special types. The keys of the given map
	 * representing the type and values the associated codecs. 
	 */
	@Configurable
	public void setTypeCodecs(Map<String, Codec<?, String>> typeCodecs) {
		this.typeCodecs = typeCodecs;
	}
	
	protected Codec<?, String> getCodec(String type) {
		Codec<?, String> codec = typeCodecs != null? typeCodecs.get(type): null;
		if (codec == null) codec = defaultTypeCodec;
		return codec;
	}
	
	@Override
	public E encode(Notification<?> value) throws CodecException {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public Notification<?> decode(E encodedValue) throws CodecException {
		try {
			JSONObject jsonObject = jsonCodec.decode(encodedValue);
			String type = jsonObject.get("type").isString().stringValue();
			String targetKey = jsonObject.get("targetKey").isString().stringValue();
			String encodedData = jsonObject.get("data").isString().stringValue();
			Codec<?, String> codec = getCodec(type);
			Object data = codec.decode(encodedData);
			return new Notification<Object>(targetKey, type, data);
		} catch (RuntimeException e) {
			throw new CodecException("could not decode notification", e);
		}
	}	
	
	@Override
	@SuppressWarnings({ "rawtypes" })
	public Class<Notification<?>> getValueClass() {
		return (Class)Notification.class;
	}
}
