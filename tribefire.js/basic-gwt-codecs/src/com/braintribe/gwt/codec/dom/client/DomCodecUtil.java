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

import java.util.Arrays;

import com.braintribe.codec.CodecException;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;
import com.google.gwt.xml.client.Text;
import com.google.gwt.xml.client.XMLParser;

public class DomCodecUtil {
	private static Document document = XMLParser.createDocument();
	
	public static Element createElement(String tagName) {
		Element element = document.createElement(tagName);
		return element;
	}
	
	public static Text createTextElement(String data) {
		return document.createTextNode(data);
	}
	
	public static Element getElement(Element parent, String... tagNames) {
		Element element = parent;
		
		for (String tagName: tagNames) {
			element = getFirstChildElement(element, tagName);
			if (element == null) break;
		}
		
		return element;
	}
	
	public static Element requireElement(Element parent, String... tagNames) throws CodecException {
		Element element = getElement(parent, tagNames);
		
		if (element == null)
			throw new CodecException("element with path " + Arrays.asList(tagNames) + " not found");

		return element;
	}
	
	public static Element getFirstChildElement(Element parent, String tagName) {
		ElementIterator elementIterator = new ElementIterator(parent);
		
		for (Element element: elementIterator) {
			if (tagName == null) return element;
			else if (element.getTagName().equals(tagName)) return element; 
		}
		
		return null;
	}
	
	public static Element requireFirstChildElement(Element parent, String tagName) throws CodecException {
		Element element = getFirstChildElement(parent, tagName);
		if (element == null)
			throw new CodecException("tag with name " + tagName + " not found");
		return element;
	}
	
	public static Text getFirstText(Element parent) {
		TextIterator textIterator = new TextIterator(parent);
		if (textIterator.hasNext()) return textIterator.next();
		else return null;
	}
	
	public static String getFirstTextAsString(Element parent) {
		return getFirstTextAsString(parent, "");
	}
	
	public static String getFirstTextAsString(Element parent, String def) {
		Text text = getFirstText(parent);
		if (text == null) return def;
		else return text.getData();
	}
	
	public static String getFullElementText(Element element, boolean rekursive) {
		StringBuilder stringBuilder = new StringBuilder();
		getFullElementText(element, stringBuilder, rekursive);
		
		String text = stringBuilder.toString();
		return text;
	}
	
	public static void getFullElementText(Element element, StringBuilder builder, boolean rekursive) {
		Node childNode = element.getFirstChild();
		while (childNode != null) {
			if (childNode instanceof Text) {
				Text text = (Text)childNode;
				builder.append(text.getData());
			}
			else if (rekursive && childNode instanceof Element) {
				getFullElementText((Element)childNode, builder, true);
			}
			childNode = childNode.getNextSibling();
		}
	}
}
