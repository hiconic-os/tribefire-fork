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

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessA.CompositeIkpaEntityA;

/**
 * Mapped to {@link CompositeIkpaEntityA}:
 * 
 * Property {@link SmartPersonA#getCompositeIkpaEntity()} is mapped via:
 * <ul>
 * <li>delegate*.personId = PersonA.id</li>
 * <li>delegate*.personName = PersonA.name</li>
 * </ul>
 * 
 * Property {@link SmartPersonA#getCompositeIkpaEntity()} is mapped via:
 * <ul>
 * <li>delegate*.personId_Set = PersonA.id</li>
 * <li>delegate*.personName_Set = PersonA.name</li>
 * </ul>
 * 
 * (*) - delegate means {@link CompositeIkpaEntityA}
 */
public interface CompositeIkpaEntity extends StandardSmartIdentifiable {
	
	EntityType<CompositeIkpaEntity> T = EntityTypes.T(CompositeIkpaEntity.class);

	String getDescription();
	void setDescription(String description);

}
