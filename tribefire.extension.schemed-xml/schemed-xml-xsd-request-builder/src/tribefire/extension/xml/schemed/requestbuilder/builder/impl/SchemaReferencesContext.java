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

import java.io.File;
import java.util.function.Supplier;

import com.braintribe.model.resource.Resource;

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ReferencedSchema;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ReferencedSchemata;
import tribefire.extension.xml.schemed.requestbuilder.resource.ResourceGenerator;
import tribefire.extension.xml.schemed.requestbuilder.resource.ResourceProvidingSession;

/**
 * a context to handle schema references, i.e when the main XSD includes other XSD
 * 
 * @author pit
 *
 * @param <T>
 */
public class SchemaReferencesContext<T extends SchemaReferencesConsumer> {
	private T consumer;
	private Supplier<ResourceProvidingSession> sessionSupplier;
	ReferencedSchemata referencedSchemata = ReferencedSchemata.T.create();

	
	public SchemaReferencesContext( T consumer, Supplier<ResourceProvidingSession> sessionSupplier) {
		this.consumer = consumer;
		this.sessionSupplier = sessionSupplier;
	}
	
	private ResourceProvidingSession getSession() {		
		return sessionSupplier.get();		
	}
	
	/**
	 * specify a referenced schema
	 * @param file - the file to load the schema from 
	 * @param uri - the URI of the schema, i.e. how it's addressed from the other schema
	 * @return
	 */
	public SchemaReferencesContext<T> file( File file, String uri) {
		ReferencedSchema referencedSchema = ReferencedSchema.T.create();
		Resource resource = ResourceGenerator.filesystemResourceFromFile( getSession(), file);
		referencedSchema.setSchema( resource);
		referencedSchema.setUri(uri);
		referencedSchemata.getReferencedSchemata().add(referencedSchema);
		return this;
	}
	
	/**
	 * finish and return to the parent context 
	 * @return - the parent context
	 */
	public T close() {
		consumer.accept(referencedSchemata);
		return consumer;
	}
	
}
