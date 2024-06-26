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
package com.braintribe.model.accessdeployment.smart.meta;

import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Similar to {@link InverseKeyPropertyAssignment}, but when associating entities more properties are being compared.
 * This is only valid for 1:1 and 1:n relationships (i.e. the configured properties are something like a composite key
 * for the owner of this meta data, and there can be on or more "other" entities with given property values). So the
 * valid type of a property on which this is configured is an entity or a set of entities.
 */
public interface CompositeInverseKeyPropertyAssignment extends PropertyAssignment {

	EntityType<CompositeInverseKeyPropertyAssignment> T = EntityTypes.T(CompositeInverseKeyPropertyAssignment.class);

	// @formatter:off
	Set<InverseKeyPropertyAssignment> getInverseKeyPropertyAssignments();
	void setInverseKeyPropertyAssignments(Set<InverseKeyPropertyAssignment> inverseKeyPropertyAssignments);
	// @formatter:on

}
