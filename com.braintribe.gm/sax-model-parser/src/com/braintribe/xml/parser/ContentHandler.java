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
package com.braintribe.xml.parser;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.logging.Logger;
import com.braintribe.utils.xml.parser.sax.SaxParserContentHandler;
import com.braintribe.xml.parser.experts.ContentExpert;
import com.braintribe.xml.parser.experts.SkippingExpert;
import com.braintribe.xml.parser.registry.ContentExpertRegistry;

public class ContentHandler<T> extends SaxParserContentHandler {
	private static Logger log = Logger.getLogger(ContentHandler.class);
	private ContentExpertRegistry registry;
	private Stack<ContentExpert> stack = new Stack<ContentExpert>();
	private Stack<String> pathStack = new Stack<String>();

	private Object result;
	
	@Configurable @Required
	public void setRegistry(ContentExpertRegistry registry) {
		this.registry = registry;
	}
	

	@Override
	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		// path 			
		String path = push(qName);
		
		// expert 
		ContentExpert parent = null;
		if (!stack.isEmpty() ) {
			parent = stack.peek();
		}		
		
		ContentExpert expert;
		if (parent instanceof SkippingExpert == false) { 
			expert = registry.getExpertForTag( path);		
			if (expert == null) {
				expert = new SkippingExpert();
				String msg = "no expert found for tag [" + qName + "], skipping";
				if (log.isDebugEnabled()) {
					log.debug( msg);				
				}			
			}
		}
		else {
			// if parent's a skipping expert, the child must be one too
			expert = new SkippingExpert();
		}
		expert.setTag(qName);						
		expert.startElement( parent, uri, localName, qName, atts);
		stack.push(expert);
	}




	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		pop( qName);
		ContentExpert expert = stack.pop();
		if (stack.isEmpty() == false) {
			expert.endElement(stack.peek(), uri, localName, qName);
		} else {
			expert.endElement(null, uri, localName, qName);
			result = expert.getPayload();
		}						
	}



	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		stack.peek().characters(ch, start, length);
	}
	
	@SuppressWarnings("unchecked")
	public T getResult() {
		return (T) result;
	}


	@Override
	public void processingInstruction(String target, String data) throws SAXException {
		// questionable whether it should be that long.. 
		String path = peek() + "?" + target;
		
		// expert 
		ContentExpert parent = null;
		if (!stack.isEmpty() ) {
			parent = stack.peek();
		}		
		
		ContentExpert expert;
		if (parent instanceof SkippingExpert == false) { 
			expert = registry.getExpertForTag( path);		
			if (expert == null) {
				expert = new SkippingExpert();
				String msg = "no expert found for tag [?" + target + "], skipping";
				if (log.isDebugEnabled()) {
					log.debug( msg);				
				}			
			}
		}
		else {
			// if parent's a skipping expert, the child must be one too
			expert = new SkippingExpert();
		}
		expert.setTag(target);						
		
		expert.startElement( parent, null, target, target, null);
		expert.characters(data.toCharArray(), 0, data.length());
		
		if (stack.isEmpty() == false) {
			expert.endElement(stack.peek(), null, target, target);
		} else {
			expert.endElement(null, null, target, target);
			result = expert.getPayload();
		}									
	}
	
	
	public String push(String qName) {
		String path = null;
		if (pathStack.empty()) {
			path = qName;
		} 
		else {
			path = pathStack.peek() + "/" + qName;
		}		
		pathStack.push(path);
		return path;
	}
	

	public void pop(String qname) {
		pathStack.pop();
	}

	public String peek() {
		if (pathStack.isEmpty()) 
			return "";
		else 
			return pathStack.peek() + "/";
	}
	
	
}
