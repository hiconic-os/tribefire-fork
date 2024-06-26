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

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.BidirectionalLink;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;

/**
 * a context for the bidirectional link declarations
 * 
 * @author pit
 *
 * @param <T>
 */
public class BidirectionalLinkContext<T extends BidirectionalLinkReceiver> implements SchemaAddressConsumer {

	private T consumer;
	private BidirectionalLink link = BidirectionalLink.T.create();

	public BidirectionalLinkContext(T consumer) {
		this.consumer = consumer;
	}
	
	/**
	 * specify the address within the schema
	 * @return - a {@link SchemaAddressContext}
	 */
	public SchemaAddressContext<BidirectionalLinkContext<T>> schemaAddress() {
		return new SchemaAddressContext<BidirectionalLinkContext<T>>(this);
	}
	
	/**
	 * specify the target property (to point to the parent)
	 * @param property
	 * @return
	 */
	public BidirectionalLinkContext<T> property(String property) {
		link.setBacklinkProperty(property);
		return this;
	}

	@Override
	public void accept(SchemaAddress address) {
		link.setSchemaAddress(address);		
	}
	
	/**
	 * close the context and return to parent context
	 * @return - the parent context
	 */
	public T close() {
		consumer.accept(link);
		return consumer;
	}
}
