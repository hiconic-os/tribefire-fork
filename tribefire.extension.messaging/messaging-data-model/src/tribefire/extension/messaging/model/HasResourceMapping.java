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
package tribefire.extension.messaging.model;

import java.util.Map;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Resource;

/**
 * The {{@link #getResourceMapping()}} will be enriched automatically when producing the {@link Message} (writing to the
 * underlying messaging system) and read out when consuming the {@link Message} (from the underlying messaging system)
 */
@Abstract
public interface HasResourceMapping extends GenericEntity {

	EntityType<HasResourceMapping> T = EntityTypes.T(HasResourceMapping.class);

	String resourceMapping = "resourceMapping";

	@Name("Persisted Resource Pairing Map")
	@Description("Resource mapping storage, a pair of Original:Persisted resource versions. Must not be filled by the caller")
	Map<Object, Resource> getResourceMapping();
	void setResourceMapping(Map<Object, Resource> resourceMapping);
}
