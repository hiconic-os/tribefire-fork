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
package com.braintribe.gwt.codec.dom.client;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.google.gwt.xml.client.Element;

public class DomMapCodec<K, V> implements Codec<Map<K, V>, Element> {
	private String parentTagName = "map";
	private Codec<K, Element> domKeyCodec;
	private Codec<V, Element> domValueCodec;
	private String keyTag = "key";
	private String valueTag = "value";
	private String entryTag = "entry";

	public DomMapCodec() {
	}
	
	public DomMapCodec(String parentTagName) {
		this.parentTagName = parentTagName;
	}
	
	/**
	 * Configures whether to use short notation when building XML. Useful for saving space.
	 * Defaults to false.
	 */
	@Configurable
	public void setUseShortNotation(boolean useShortNotation) {
		if (useShortNotation) {
			keyTag = "k";
			valueTag = "v";
			entryTag = "e";
		}
	}
	
	@Configurable @Required
	public void setDomKeyCodec(Codec<K, Element> domKeyCodec) {
		this.domKeyCodec = domKeyCodec;
	}
	
	@Configurable @Required
	public void setDomValueCodec(Codec<V, Element> domValueCodec) {
		this.domValueCodec = domValueCodec;
	}
	
	@Override
	public Map<K, V> decode(Element element) throws CodecException {
		String elementTagName = element.getTagName();
		if (parentTagName != null && !parentTagName.equals(elementTagName))
			throw new CodecException("tag name " + parentTagName + " expected and found " + elementTagName);
		
		Map<K, V> map = new HashMap<K, V>();

		Iterator<Element> it = new ElementIterator(element);
		
		while (it.hasNext()) {
			Element entryElement = it.next();
			Element keyElement = DomCodecUtil.getFirstChildElement(entryElement, keyTag);
			Element valueElement = DomCodecUtil.getFirstChildElement(entryElement, valueTag);

			Element keyValueElement = DomCodecUtil.getFirstChildElement(keyElement, null);
			Element valueValueElement = DomCodecUtil.getFirstChildElement(valueElement, null);
			
			K key = domKeyCodec.decode(keyValueElement);
			V value = domValueCodec.decode(valueValueElement);
			
			map.put(key, value);
		}
		
		return map;
	}
	
	@Override
	public Element encode(Map<K, V> map) throws CodecException {
		if (parentTagName == null)
			throw new CodecException("parentTagName cannot be null when encoding");
		
		Element element = DomCodecUtil.createElement(parentTagName);
		
		for (Map.Entry<K, V> entry: map.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();
			
			Element keyElement = DomCodecUtil.createElement(keyTag);
			Element valueElement = DomCodecUtil.createElement(valueTag);
			
			keyElement.appendChild(domKeyCodec.encode(key));
			valueElement.appendChild(domValueCodec.encode(value));

			Element entryElement = DomCodecUtil.createElement(entryTag);
			entryElement.appendChild(keyElement);
			entryElement.appendChild(valueElement);
			element.appendChild(entryElement);
		}
		
		return element;
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public Class<Map<K, V>> getValueClass() {
		return (Class) Map.class;
	}
}
