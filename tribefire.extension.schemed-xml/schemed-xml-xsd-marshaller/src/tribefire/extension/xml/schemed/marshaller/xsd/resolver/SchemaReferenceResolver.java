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
package tribefire.extension.xml.schemed.marshaller.xsd.resolver;

import com.braintribe.model.resource.Resource;

import tribefire.extension.xml.schemed.model.xsd.Schema;

/**
 * the basic resolver to 
 * @author pit
 *
 */
public interface SchemaReferenceResolver {

	/**
	 * find the schema associated with this uri, this only works if the 
	 * {@link SchemaReferenceResolver} implementation knows where to look :
	 * see {@link ConfigurableSchemaReferenceResolver} for options.
	 * @param parent - the {@link Schema} that is the parent of the request
	 * @param uri  - the URI of the Schema
	 * @return - an instance of a {@link Schema}, will throw {@link IllegalStateException} if it can't be resolved
	 */	
	Schema resolve( Schema parent, String uri);
	/**
	 * simplest case: a single XSD 
	 * @param uri - the URI for the XSD (may be without consequence)
	 * @param resource - the {@link Resource} that contains the XSD
	 * return - an instance of a {@link Schema}, will throw {@link IllegalStateException} if it can't be resolved
	 */
	Schema resolve( String uri, Resource resource);	
	
	String getUriOfSchema( Schema schema);	
	Schema getSchemaOfUri( String uri);
		
}
