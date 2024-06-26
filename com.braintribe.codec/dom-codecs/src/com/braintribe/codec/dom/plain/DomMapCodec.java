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
package com.braintribe.codec.dom.plain;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.codec.context.CodingContext;
import com.braintribe.codec.dom.DomEncodingContext;
import com.braintribe.utils.xml.XmlTools;

public class DomMapCodec<K, V> implements Codec<Map<K, V>, Element> {
	private String parentTagName = "map";
	private Codec<K, Element> domKeyCodec;
	private Codec<V, Element> domValueCodec;

	public DomMapCodec() {
	}

	public DomMapCodec(String parentTagName) {
		this.parentTagName = parentTagName;
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

		Iterator<Element> it = XmlTools.getElementIterator(element, "entry");

		while (it.hasNext()) {
			Element entryElement = it.next();
			Element keyElement = XmlTools.getFirstElement(entryElement, "key");
			Element valueElement = XmlTools.getFirstElement(entryElement, "value");

			Element keyValueElement = XmlTools.getFirstElement(keyElement, null);
			Element valueValueElement = XmlTools.getFirstElement(valueElement, null);

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

		DomEncodingContext ctx = CodingContext.get();
		Document document = ctx.getDocument();

		Element element = document.createElement(parentTagName);

		for (Map.Entry<K, V> entry: map.entrySet()) {
			K key = entry.getKey();
			V value = entry.getValue();

			Element keyElement = document.createElement("key");
			Element valueElement = document.createElement("value");

			keyElement.appendChild(domKeyCodec.encode(key));
			valueElement.appendChild(domValueCodec.encode(value));

			Element entryElement = document.createElement("entry");
			entryElement.appendChild(keyElement);
			entryElement.appendChild(valueElement);
			element.appendChild(entryElement);
		}

		return element;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Class<Map<K, V>> getValueClass() {
		return (Class)Map.class;
	}
}
