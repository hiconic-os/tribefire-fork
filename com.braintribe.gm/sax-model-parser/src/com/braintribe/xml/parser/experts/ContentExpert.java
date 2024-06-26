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
package com.braintribe.xml.parser.experts;

import java.lang.reflect.Array;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;

/**
 * a {@link ContentExpert} is used to handle a tag found in the XML 
 * @author Pit
 *
 */
public interface ContentExpert{
	/**
	 * called when SAX enters a new tag. Can be used to store the property name (if equaling qName)
	 * @param parent - the {@link ContentExpert} that handles the parent (if any)
	 * @param uri - the URI
	 * @param localName - the local name 
	 * @param qName - the qualified name 
	 * @param atts - any {@link Attributes}
	 * @throws SAXException - thrown if anything goes wrong 
	 */
	void startElement(ContentExpert parent, String uri, String localName, String qName, Attributes atts) throws SAXException;
	
	/**
	 * called when SAX is exiting the tag. Must be used the get read to be able to return the recored value 
	 * @param parent  - the {@link ContentExpert} that handles the parent.
	 * @param uri - the URI
	 * @param localName - the local name 
	 * @param qName - the fully qualified name 
	 * @throws SAXException 
	 */
	void endElement(ContentExpert parent, String uri, String localName, String qName) throws SAXException; 
	
	/**
	 * @param ch - an {@link Array} of char that contain PART of the data. May be called multiple times! 
	 * @param start - start of the sequence
	 * @param length - length of the sequence 
	 * @throws SAXException
	 */
	void characters(char[] ch, int start, int length) throws SAXException;
	
	/**
	 * set the XML tag in question 
	 * @param tag - the XML tag
	 */
	void setTag( String tag);
	/**
	 * gets the XML tag in question 
	 * @return - the XML tag 
	 */
	String getTag();
	
	/**
	 * get the property (in the model) that the expert is handling (either override via constructor or qName stored) 
	 * @return - the property name 
	 */
	String getProperty();

	/**
	 * if the {@link ContentExpert} has to handle children, this is used to attach the child's value 
	 * @param child - the {@link ContentExpert} that has processed the child tag 
	 */
	void attach( ContentExpert child);
	
	/**
	 * get a representation of the data that the {@link ContentExpert} has retrieved from the tag 
	 * @return - the data as an {@link Object}
	 */
	Object getPayload();
	
	/**
	 * if the {@link ContentExpert} backs  GenericEntity, it needs to return the instance here 
	 * @return - the {@link GenericEntity} instance the {@link ContentExpert} is handling 
	 */
	GenericEntity getInstance();
	/**
	 * if the {@link ContentExpert} backs a GenericEntity, it needs to return the type here 
	 * @return - the {@link EntityType} of the {@link GenericEntity} that the {@link ContentExpert} is handling
	 */
	EntityType<GenericEntity> getType();
	
}
