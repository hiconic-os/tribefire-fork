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
package com.braintribe.codec.dom;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.utils.xml.XmlTools;

public class XmlCodec<T> implements Codec<T, String> {

	protected DomCodec<T> xmlCodec;
	protected String path;
	
	public XmlCodec(DomCodec<T> xmlCodec) {
		this(xmlCodec, null);
	}
	
	public XmlCodec(DomCodec<T> xmlCodec, String path) {
		this.xmlCodec= xmlCodec;
		this.path = path;
	}
	
	@Override
	public T decode(String s) throws CodecException {
		if (s == null || s.trim().length() == 0)
			return null;
		
		Element e;
		try {
			Document doc = XmlTools.parseXML(s);
			
			e = doc.getDocumentElement();
			if (path!=null) e = XmlTools.getElement(e, path);
		} catch (Exception ex) {
			throw new CodecException("error while decoding xml", ex);
		}
		
		return xmlCodec.decode(e);
	}

	@Override
	public String encode(T obj) throws CodecException {
		if (obj == null) return ""; //TODO: really? or ask codec?
		
		try {
			Document doc = XmlTools.createDocument();
			
			Element rootElement = xmlCodec.encode(doc, obj);
			if (rootElement == null) return "";
			
			doc.appendChild(rootElement);
			
			return XmlTools.toString(doc);
		} catch (Exception e) {
			throw new CodecException("error while encoding dom", e);
		}
	}
	
	@Override
	public Class<T> getValueClass() {
	    return xmlCodec.getValueClass();
	}

}
