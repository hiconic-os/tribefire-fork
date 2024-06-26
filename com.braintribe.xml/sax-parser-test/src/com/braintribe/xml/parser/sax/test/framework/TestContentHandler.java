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
package com.braintribe.xml.parser.sax.test.framework;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.braintribe.utils.xml.parser.sax.SaxParserContentHandler;

public class TestContentHandler extends SaxParserContentHandler {

	Stack<String> stack = new Stack<String>();
	
	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		StringBuffer buffer = new StringBuffer();
		if (atts != null) {
			for (int i = 0; i < atts.getLength(); i++) {
				if (buffer.length() > 0)
					buffer.append(";");
				buffer.append( atts.getQName(i) + "=" + atts.getValue(i));
			}		
		}
		System.out.println("Element [" + qName + "] starts, attrs [" + buffer.toString() + "]");
		stack.push(qName);
		super.startElement(uri, localName, qName, atts);
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		System.out.println("Element [" + qName + "] ends");
		stack.pop();
		super.endElement(uri, localName, qName);
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		String str = new String( ch, start, length);		
		
		String qname = stack.peek();
		System.out.println( "value for [" + qname + "] is [" + str + "]");
		super.characters(ch, start, length);
	}

	
}
