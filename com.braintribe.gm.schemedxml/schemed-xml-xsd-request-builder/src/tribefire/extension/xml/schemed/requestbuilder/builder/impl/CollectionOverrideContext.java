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

import com.braintribe.model.meta.GmListType;
import com.braintribe.model.meta.GmSetType;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.CollectionOverride;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;

/**
 * a context to specify what collection type should be used for a certain property. 
 * Default is to use {@link GmListType} type, but you can override it to used {@link GmSetType}
 *  
 * @author pit
 *
 * @param <T>
 */
public class CollectionOverrideContext<T extends CollectionOverrideConsumer> implements SchemaAddressConsumer{

	private CollectionOverride override = CollectionOverride.T.create();
	private T consumer;
	
	public CollectionOverrideContext(T consumer) {
		this.consumer = consumer;		
	}
		
	/**
	 * access the {@link SchemaAddressContext} to specify what type/property combination you want to 
	 * influence
	 * @return - a {@link SchemaAddressContext}, specialized of internal addresses in XSD
	 */
	public SchemaAddressContext<CollectionOverrideContext<T>> schemaAddress() {
		return new SchemaAddressContext<CollectionOverrideContext<T>>(this);
	}

	@Override
	public void accept(SchemaAddress address) {
		override.setSchemaAddress(address);		
	}
	
	/**
	 * specify that a {@link GmSetType} should be used
	 * @return - the {@link CollectionOverrideContext}
	 */
	public CollectionOverrideContext<T> asSet() {
		override.setCollectionAsSet(true);
		return this;
	}
	
	/**
	 * specify that a {@link GmListType} should be used (this is default)
	 * @return - the {@link CollectionOverrideContext}
	 */
	public CollectionOverrideContext<T> asList() {
		override.setCollectionAsSet(true);
		return this;
	}
	
	/**
	 * finish and return to parent context
	 * @return - the parent context
	 */
	public T close() {
		consumer.accept(override);
		return consumer;
	}
	
}
