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

import java.util.Collection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.codec.context.CodingContext;
import com.braintribe.codec.dom.DomEncodingContext;

public abstract class DomCollectionCodec<T, C extends Collection<T>> implements Codec<C, Element> {
	private String parentTagName;
	private String tagName;
	private Codec<T, Element> domCodec;
	private boolean ignoreAlternativeTags = false;

	public DomCollectionCodec(String tagName) {
		this(null, tagName);
	}

	public DomCollectionCodec(String parentTagName, String tagName) {
		this.parentTagName = parentTagName;
		this.tagName = tagName;
	}

	public void setIgnoreAlternativeTags(boolean ignoreAlternativeTags) {
		this.ignoreAlternativeTags = ignoreAlternativeTags;
	}

	public void setDomCodec(Codec<T, Element> domCodec) {
		this.domCodec = domCodec;
	}

	protected abstract C createCollection();

	@Override
	public C decode(Element element) throws CodecException {
		String elementTagName = element.getTagName();
		if (parentTagName != null && !parentTagName.equals(elementTagName))
			throw new CodecException("tag name " + parentTagName + " expected and found " + elementTagName);

		C values = createCollection();
		NodeList nodes = element.getChildNodes();
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Element subElement = (Element)node;
				String subElementTagName = subElement.getTagName();
				if (tagName != null) {
					if (!tagName.equals(subElementTagName)) {
						if (ignoreAlternativeTags) continue;
						else
							throw new CodecException("tag name " + tagName + " expected and found " + subElementTagName);
					}
				}

				T value = domCodec.decode(subElement);
				values.add(value);
			}
		}

		return values;
	}

	@Override
	public Element encode(C collectionOfValues) throws CodecException {
		if (parentTagName == null)
			throw new CodecException("parentTagName cannot be null when encoding");

		DomEncodingContext ctx = CodingContext.get();
		Document document = ctx.getDocument();

		Element element = document.createElement(parentTagName);

		for (T value: collectionOfValues) {
			Element subElement = domCodec.encode(value);
			element.appendChild(subElement);
		}

		return element;
	}
}
