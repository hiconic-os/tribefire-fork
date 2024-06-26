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

import com.braintribe.model.accessdeployment.smart.meta.discriminator.CompositeDiscriminator;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * This is used when we have a hierarchy of smart types, which is mapped just a single delegate type, but we want to
 * distinguish the types based on one or more "discriminator" property values. It is the exact same principle as
 * hibernate does with TalbePerHierarchy strategy.
 * 
 * The base of the hierarchy is mapped with {@link PolymorphicBaseEntityAssignment}, the sub-type are then mapped with
 * {@link PolymorphicDerivateEntityAssignment}.
 * 
 * The base (and only the base) defines the discriminator property (or properties), the sub-types then only have
 * discriminator values and a link to the base.
 * 
 * In case of multiple discriminator properties, the value is actually a ListRecord containing the values, and the
 * correlation with {@link CompositeDiscriminator#getProperties()} is done via position in the list.
 * 
 * @see PolymorphicBaseEntityAssignment
 * @see PolymorphicDerivateEntityAssignment
 * 
 * @author peter.gazdik
 */
@Abstract
public interface PolymorphicEntityAssignment extends EntityAssignment {

	EntityType<PolymorphicEntityAssignment> T = EntityTypes.T(PolymorphicEntityAssignment.class);

	// @formatter:off
	/**
	 * The discriminator value is either a simple value, or a ListRecord of simple values (in case of {@link CompositeDiscriminator}).
	 */
	Object getDiscriminatorValue();
	void setDiscriminatorValue(Object value);
	// @formatter:on	

}
