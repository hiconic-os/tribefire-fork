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
package com.braintribe.codec.string;

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.utils.xml.XmlTools;

public class XmlCodec<T> implements Codec<T, String> {
	private Codec<T, Element> domCodec;
	
	public XmlCodec(Codec<T, Element> domCodec) {
		super();
		setDomCodec(domCodec);
	}
	
	public XmlCodec() {
	}
	
	public void setDomCodec(Codec<T, Element> domCodec) {
		this.domCodec = domCodec;
	}
	
	public Codec<T, Element> getDomCodec() {
		return domCodec;
	}

	@Override
	public String encode(T value) throws CodecException {
		Element element = domCodec.encode(value);
		try {
			String xml = XmlTools.toString(element);
			return xml;
		} catch (TransformerException e) {
			throw new CodecException("error while parsing xml", e);
		}
	}
	
	@Override
	public T decode(String encodedValue) throws CodecException {
		if (encodedValue == null || encodedValue.length() == 0)
			return null;
		
		try {
			Document document = XmlTools.parseXML(encodedValue);
			return domCodec.decode(document.getDocumentElement());
		}
		catch (Exception e) {
			throw new CodecException("error while parsing xml", e);
		}
	}
	
	@Override
	public Class<T> getValueClass() {
		return domCodec.getValueClass();
	}
}
