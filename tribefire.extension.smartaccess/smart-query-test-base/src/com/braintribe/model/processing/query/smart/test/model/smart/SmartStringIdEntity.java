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
package com.braintribe.model.processing.query.smart.test.model.smart;

import java.util.Set;

import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessB.StandardIdEntity;

/**
 * Mapped to {@link StandardIdEntity}
 * 
 * @see StandardIdEntity
 */
public interface SmartStringIdEntity extends SmartGenericEntity, StandardStringIdentifiable {

	EntityType<SmartStringIdEntity> T = EntityTypes.T(SmartStringIdEntity.class);

	String getName();
	void setName(String name);

	SmartStringIdEntity getParent();
	void setParent(SmartStringIdEntity parent);

	Set<SmartStringIdEntity> getChildren();
	void setChildren(Set<SmartStringIdEntity> children);

	// mapped using kpaParentId
	SmartStringIdEntity getKpaParent();
	void setKpaParent(SmartStringIdEntity value);

}
