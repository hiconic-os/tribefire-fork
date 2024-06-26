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
package tribefire.extension.xml.schemed.xsd.api.analyzer.naming;

import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.SchemaEntity;
import tribefire.extension.xml.schemed.xsd.analyzer.registry.schema.QPath;

public interface QPathGenerator {
	/**
	 * create a fully qualified {@link QPath} for the passed {@link SchemaEntity}
	 * @param schemaEntity - the {@link SchemaEntity} to start traversing from 
	 * @return - the {@link QPath}
	 */
	QPath generateQPathForSchemaEntity( Schema schema);
}
