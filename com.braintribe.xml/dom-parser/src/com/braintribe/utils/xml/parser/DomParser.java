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
package com.braintribe.utils.xml.parser;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.validation.SchemaFactory;

import com.braintribe.utils.xml.parser.builder.CreateDocumentContext;
import com.braintribe.utils.xml.parser.builder.LoadDocumentContext;
import com.braintribe.utils.xml.parser.builder.ValidateDocumentContext;
import com.braintribe.utils.xml.parser.builder.WriteDocumentContext;

/**
 * new DomParser interface: gives access to the following builders:<br/><br/>
 * {@link CreateDocumentContext} : creating and copying documents <br/>
 * {@link LoadDocumentContext} : loading documents <br/>
 * {@link WriteDocumentContext} : writing documents <br/>
 * {@link ValidateDocumentContext} : validating documents <br/>
 * 
 * <br/>
 * @author Pit
 *
 */
public class DomParser {

	/**
	 * gets a {@link LoadDocumentContext}
	 * @return - a newly created {@link LoadDocumentContext}
	 */
	public static LoadDocumentContext load() {
		return new LoadDocumentContext();
	}
	
	/**
	 * gets a {@link LoadDocumentContext} parametrized with the {@link DocumentBuilderFactory} passed
	 * @param factory - the {@link DocumentBuilderFactory} to use 
	 * @return - a newly created {@link LoadDocumentContext}
	 */
	public static LoadDocumentContext load( DocumentBuilderFactory factory) {
		return new LoadDocumentContext(factory);
	}
	
	/**
	 * gets a {@link CreateDocumentContext}
	 * @return - a newly created {@link CreateDocumentContext}
	 */
	public static CreateDocumentContext create() {
		return new CreateDocumentContext();
	}
	
	/**
	 * gets a {@link CreateDocumentContext} parametrized with the {@link DocumentBuilderFactory} passed
	 * @param factory - the {@link DocumentBuilderFactory} to use
	 * @return - a newly created {@link CreateDocumentContext}
	 */
	public static CreateDocumentContext create(DocumentBuilderFactory factory) {
		return new CreateDocumentContext( factory);
	}
	
	/**
	 * gets a {@link WriteDocumentContext} 
	 * @return - a newly created {@link WriteDocumentContext}
	 */
	public static WriteDocumentContext write() {
		return new WriteDocumentContext();
	}
	
	/**
	 * gets a {@link WriteDocumentContext} parametrized with the {@link TransformerFactory} passed
	 * @param factory - {@link TransformerFactory} to use
	 * @return - a newly created {@link WriteDocumentContext}
	 */
	public static WriteDocumentContext write( TransformerFactory factory) {
		return new WriteDocumentContext(factory);
	}
	
	/**
	 * gets a {@link ValidateDocumentContext}
	 * @return - a newly created {@link ValidateDocumentContext}
	 */
	public static ValidateDocumentContext validate(){
		return new ValidateDocumentContext();
	}
	
	/**
	 * gets a {@link ValidateDocumentContext}
	 * @param factory - the {@link SchemaFactory} to use
	 * @return - a newly created {@link ValidateDocumentContext}
	 */
	public static ValidateDocumentContext validate( SchemaFactory factory) {
		return new ValidateDocumentContext(factory);
	}
	
	
}
