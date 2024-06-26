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

import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ReferencedSchema;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.ReferencedSchemata;
import tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model.SchemedXmlXsdAnalyzerRequest;

/**
 * the {@link SchemaReferenceResolver} needs to be configured if it needs to 
 * resolve URI without location.
 * @author pit
 *
 */
public interface ConfigurableSchemaReferenceResolver {

	/**
	 * direct from the request, a set of {@link ReferencedSchema}
	 * @param schemata - the {@link ReferencedSchemata} as from the {@link SchemedXmlXsdAnalyzerRequest}
	 */
	void setReferencedSchemata( ReferencedSchemata schemata);	
	/**
	 * a zip file that contains the XSDs to process
	 * @param resource
	 */
	void setContainerResource( Resource resource);	
}
