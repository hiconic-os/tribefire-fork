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

import javax.xml.transform.TransformerException;

import org.w3c.dom.Document;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.codec.marshaller.api.GmCodec;
import com.braintribe.codec.marshaller.api.GmDeserializationOptions;
import com.braintribe.codec.marshaller.api.GmSerializationOptions;
import com.braintribe.utils.xml.XmlTools;

public class XmlDocumentGmCodec<T> implements GmCodec<T, String> {
	private GmCodec<T, Document> domCodec;
	
	public XmlDocumentGmCodec(GmCodec<T, Document> domCodec) {
		super();
		setDomCodec(domCodec);
	}
	
	public XmlDocumentGmCodec() {
	}
	
	public void setDomCodec(GmCodec<T, Document> domCodec) {
		this.domCodec = domCodec;
	}
	
	public Codec<T, Document> getDomCodec() {
		return domCodec;
	}

	@Override
	public String encode(T value) throws CodecException {
		Document document = domCodec.encode(value);
		try {
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
			return domCodec.decode(document);
		}
		catch (Exception e) {
			throw new CodecException("error while parsing xml", e);
		}
	}
	
	@Override
	public T decode(String encodedValue, GmDeserializationOptions options) throws CodecException {
		if (encodedValue == null || encodedValue.length() == 0)
			return null;
		
		try {
			Document document = XmlTools.parseXML(encodedValue);
			return domCodec.decode(document, options);
		}
		catch (Exception e) {
			throw new CodecException("error while parsing xml", e);
		}
	}
	
	@Override
	public String encode(T value, GmSerializationOptions options) throws CodecException {
		Document document = domCodec.encode(value, options);
		try {
			String xml = XmlTools.toString(document);
			return xml;
		} catch (TransformerException e) {
			throw new CodecException("error while parsing xml", e);
		}
	}
	
	@Override
	public Class<T> getValueClass() {
		return domCodec.getValueClass();
	}
}
