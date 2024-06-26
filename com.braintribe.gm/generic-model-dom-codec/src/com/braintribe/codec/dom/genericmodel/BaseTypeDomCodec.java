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

import com.braintribe.codec.Codec;
import com.braintribe.codec.CodecException;
import com.braintribe.codec.context.CodingContext;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.utils.xml.XmlTools;

public class BaseTypeDomCodec implements Codec<Object, Element> {
	private GenericModelDomCodecRegistry codecRegistry;
	private final GenericModelTypeReflection typeReflection = GMF.getTypeReflection();
	
	public void setCodecRegistry(GenericModelDomCodecRegistry codecRegistry) {
		this.codecRegistry = codecRegistry;
	}
	
	@Override
	public Element encode(Object value) throws CodecException {
		GenericModelType type = typeReflection.getType(value);
		if (type == null)
			throw new CodecException("GenericModelType not found for value: " + value);
	
		EncodingContext encodingContext = CodingContext.get();
		Document document = encodingContext.getDocument();
		
		Element anyTypeElement = document.createElement("any");
		anyTypeElement.setAttribute("type", type.getTypeSignature());
	
		Codec<Object, Element> codec = codecRegistry.getCodec(type);
		Element childElement = codec.encode(value);
		anyTypeElement.appendChild(childElement);
		
		return anyTypeElement;
	}
	
	@Override
	public Object decode(Element encodedValue) throws CodecException {
		String typeSignature = encodedValue.getAttribute("type");
		
		if (typeSignature == null || typeSignature.length() == 0)
			throw new CodecException("AnyType must have a type attribute to be decoded from a DOM Element");
		
		Element childElement = XmlTools.getFirstElement(encodedValue, null);
		GenericModelType type = typeReflection.getType(typeSignature);
		Codec<Object, Element> codec = codecRegistry.getCodec(type);
		
		Object actualValue = codec.decode(childElement);
		
//		GenericModelValue genericModelValue = new GenericModelValue();
//		genericModelValue.setTypeSignature(typeSignature);
//		genericModelValue.setValue(actualValue);
		
		return actualValue;
	}
	
	@Override
	public Class<Object> getValueClass() {
		return Object.class;
	}
}
