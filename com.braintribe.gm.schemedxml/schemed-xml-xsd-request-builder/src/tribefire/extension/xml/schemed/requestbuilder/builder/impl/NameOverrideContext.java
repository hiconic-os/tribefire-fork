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

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.MappingOverride;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemaAddress;

/**
 * 
 * a context to generate {@link MappingOverride}s to influence the naming 
 * 
 * @author pit
 * 
 *
 * @param <T>
 */
public class NameOverrideContext<T extends MappingOverrideConsumer> implements SchemaAddressConsumer{
	
	private MappingOverride override = MappingOverride.T.create();
	private T consumer;
	
	public NameOverrideContext( T consumer) {
		this.consumer = consumer;
	}

	/**
	 * set the new name for the property or type 
	 * @param name
	 * @return
	 */
	public NameOverrideContext<T> overridingName( String name) {
		override.setNameOverride(name);
		return this;
	}
	
	/**
	 * define the address within the XSD schema
	 * @return
	 */
	public SchemaAddressContext<NameOverrideContext<T>> schemaAddress() {
		return new SchemaAddressContext<NameOverrideContext<T>>(this);
	}

	@Override
	public void accept(SchemaAddress address) {
		override.setSchemaAddress(address);		
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
