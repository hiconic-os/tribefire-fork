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
package tribefire.extension.xml.schemed.model.api.xsd.analyzer.api.model;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.meta.Alias;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

/**
 * a {@link ReferencedSchema} is an external schema that needs to be incorporated in the translation 
 * @author pit
 *
 */
@Description("a ReferencedSchema is an external schema that needs to be incorporated in the translation")
public interface ReferencedSchema extends GenericEntity{
	
	final EntityType<ReferencedSchema> T = EntityTypes.T(ReferencedSchema.class);

	/** 
	 * @return - the URI of schema - as it is referenced by others
	 */
	@Description("the URI of schema - as it is referenced by others")
	@Alias("u")
	@Mandatory
	String getUri();
	void setUri(String key);
	
	/** 
	 * @return - {@link Resource} that contains the referenced schema
	 */
	@Description(" the Resource that contains the referenced schema")
	@Alias("s")
	@Mandatory
	Resource getSchema();
	void setSchema( Resource schema);
}
