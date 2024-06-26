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

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.utils.xml.XmlTools;

public class XmlDocumentCodec<T> implements Codec<T, String> {
	private Codec<T, Document> domCodec;
	
	public XmlDocumentCodec(Codec<T, Document> domCodec) {
		super();
		setDomCodec(domCodec);
	}
	
	public XmlDocumentCodec() {
	}
	
	public void setDomCodec(Codec<T, Document> domCodec) {
		this.domCodec = domCodec;
	}
	
	public Codec<T, Document> getDomCodec() {
		return domCodec;
	}

	@Override
	public String encode(T value) throws CodecException {
		Document document = domCodec.encode(value);
		try {
			//Let the GC remove the possibly large object
			value = null;
			String xml = XmlTools.toString(document);
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
			//Let the GC remove the possibly large string
			encodedValue = null;
			return domCodec.decode(document);
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
