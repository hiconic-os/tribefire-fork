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
package com.braintribe.utils.xml.parser.sax;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.ContentHandler;
import org.xml.sax.ErrorHandler;
import org.xml.sax.XMLReader;

import com.braintribe.utils.xml.parser.sax.builder.SaxContext;

/**
 * the new sax parser wrapper <br/>
 * parsing is done via the {@link XMLReader}!<br/>
 * <br/>
 * there are three ways to deal with validation
 * <ul>
 * <li>no validation should take place: neither set the {@link SaxContext#setValidating()} nor supply a schema in any way </li>
 * <li>the xml should by validated by the data contained in the xml (xsi:schemaLocation): set the {@link SaxContext#setValidating()}</li>
 * <li>the xml should validated by the schema you provide : use the appropriate functions on the {@link SaxContext} to specify the schema</li>
 * </ul>
 * <br/>
 * if you do not specify an {@link ErrorHandler}, a default one is injected, the {@link SaxParserErrorHandler}, hooked to System.err for warning messages.<br/>
 * <br/>
 * there is a standard implementation of the {@link ContentHandler} interface, the {@link SaxParserContentHandler} that can be used as a base for your specific implementation. By default, no ContentHandler is injected.<br/>
 * @author pit
 *
 */
public class SaxParser {

	/**
	 * returns a standard {@link SaxContext}, standard {@link SAXParserFactory} and {@link SAXParser} will be used 
	 * @return - a {@link SaxContext}
	 */
	public static SaxContext parse() {
		return new SaxContext();
	}
		
	/**
	 * returns a {@link SaxContext} with a primed {@link SAXParserFactory} which will be used to get the {@link SAXParser}
	 * @param factory - the {@link SAXParserFactory} to be used
	 * @return - a {@link SaxContext}
	 */
	public static SaxContext parse(SAXParserFactory factory) {		
		return new SaxContext( factory);
	}
	
	/**
	 * returns a {@link SaxContext} with a primed {@link SAXParser} (no {@link SAXParserFactory} will ever be created), 
	 * i.e. no features will be set on it, even if you specify any 
	 * @param parser - the {@link SAXParser} to us
	 * @return - a {@link SaxContext}
	 */
	public static SaxContext parse(SAXParser parser) {
		return new SaxContext( parser);
	}
		
	
}
