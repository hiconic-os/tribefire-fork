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
package com.braintribe.codec.dom.genericmodel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.codec.dom.DomEncodingContext;
import com.braintribe.utils.xml.XmlTools;

public class ValueDomCodec<T> implements Codec<T, Element> {
	private Codec<T, String> delegate;
	private String elementName = "value";
	
	@Configurable 
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	
	@Configurable @Required
	public void setDelegate(Codec<T, String> delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public T decode(Element element) throws CodecException {
		String encodedValue = XmlTools.getFirstTextAsString(element);
		return delegate.decode(encodedValue);
	}
	
	@Override
	public Element encode(T value) throws CodecException {
		DomEncodingContext ctx = EncodingContext.get();
		Document document = ctx.getDocument();
		Element element = document.createElement(elementName);
		Text text = document.createTextNode(delegate.encode(value));
		element.appendChild(text);
		return element;
	}
	
	@Override
	public Class<T> getValueClass() {
		return delegate.getValueClass();
	}

}
