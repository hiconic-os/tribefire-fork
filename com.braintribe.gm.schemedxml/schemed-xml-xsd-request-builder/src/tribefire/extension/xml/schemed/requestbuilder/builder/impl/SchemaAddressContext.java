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
package tribefire.extension.xml.schemed.requestbuilder.builder.impl;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;

/**
 * a specialized context for names within XSD.
 * It's not a XmlPath or so, just a simple type (ComplexType) and property (element) pairing 
 * 
 * @author pit
 *
 * @param <T>
 */
public class SchemaAddressContext<T extends SchemaAddressConsumer> {
	private T consumer;
	private SchemaAddress schemaAddress = SchemaAddress.T.create();
	
	public SchemaAddressContext(T consumer) {
		this.consumer = consumer;
	}
	
	/**
	 * specify the name of the ComplexType to match
	 * @param complexType - the name 
	 * @return - this context
	 */
	public SchemaAddressContext<T> type(String complexType) {
		schemaAddress.setParent(complexType);
		return this;
	}
	/**
	 * specify the name of the Element to match. If null, just the ComplexType matches,
	 * e.g. to rename the type rather than the property. 
	 * @param element - the name
	 * @return - this context
	 */
	public SchemaAddressContext<T> property(String element) {
		schemaAddress.setElement(element);
		return this;
	}
	
	/**
	 * finish and return to parent context 
	 * @return - the parent context
	 */
	public T close() {
		consumer.accept(schemaAddress);
		return consumer; 
	}
}
