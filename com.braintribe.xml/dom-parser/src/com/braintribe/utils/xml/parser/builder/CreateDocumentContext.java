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
package com.braintribe.utils.xml.parser.builder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import com.braintribe.utils.xml.parser.DomParserException;

/**
 * a context to create documents with it <br/><br/>
 * usage : see test artifact..<br/> 
 * basic use : Document document = DomParser.create().setNamespaceAware().makeItSo();
 * @author Pit
 *
 */
public class CreateDocumentContext extends DocumentContext<CreateDocumentContext> {
	
	/**
	 * default constructor
	 */
	public CreateDocumentContext() {
		super();
		setSelf( this);
	}
	
	/**
	 * constructor with passed {@link DocumentBuilderFactory}
	 * @param factory - the {@link DocumentBuilderFactory} to use
	 */
	public CreateDocumentContext( DocumentBuilderFactory factory) {
		super( factory);
		setSelf(this);
	}
	
	/**
	 * creates a new {@link Document} as a copy of the one passed 
	 * @param in - the {@link Document} to copy 
	 * @return - the copy of the document passed 
	 * @throws DomParserException - if anything goes wrong
	 */
	public Document makeItSo( Document in) throws DomParserException {
		Document out = makeItSo();
		try {
			Node importedNode = out.importNode( in.getDocumentElement(), true);
			out.appendChild( importedNode);
		} 
		catch (DOMException e) {
			throw new DomParserException( "Can't create a copy of '" + in.getDocumentURI() + "'", e);
		}		
		return out;
	}
	
	/**
	 * creates a new {@link Document}
	 * @return - the new {@link Document}
	 * @throws DomParserException - if anything goes wrong
	 */
	public Document makeItSo() throws DomParserException{
		try {
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.newDocument();
			return document;
			
		} catch (Exception e) {
			throw new DomParserException( e);				
		}    	    
	}
}
