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
package com.braintribe.model.processing.traversing.engine.api.customize;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.AbsenceInformation;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.processing.traversing.api.GmTraversingContext;
import com.braintribe.model.processing.traversing.api.path.TraversingModelPathElement;
import com.braintribe.model.processing.traversing.engine.impl.walk.ModelWalker;

/**
 * Represents possible customizations for a {@link ModelWalker}
 *
 */
public interface ModelWalkerCustomization {

	/**
	 * Verify if absence Information can be resolved
	 * 
	 * @param context
	 *            The current {@link GmTraversingContext}
	 * @param property
	 *            {@link Property} that is absent
	 * @param entity
	 *            {@link GenericEntity} that holds the property
	 * @param absenceInformation
	 *            {@link AbsenceInformation} that represents the absent property
	 *            in the entity.
	 * @return boolean indicating if {@link AbsenceInformation} can be resolved
	 */
	boolean isAbsenceResolvable(GmTraversingContext context, Property property, GenericEntity entity, AbsenceInformation absenceInformation);

	/**
	 * Replace the {@link TraversingModelPathElement} prior to adjust the
	 * traversing events
	 * 
	 * @param context
	 *            The current {@link GmTraversingContext}
	 * @param pathElement
	 *            The current {@link TraversingModelPathElement} that is being
	 *            visited.
	 * @return The new {@link TraversingModelPathElement} that will be used for
	 *         the enter and exit events
	 */
	TraversingModelPathElement substitute(GmTraversingContext context, TraversingModelPathElement pathElement);

	/**
	 * Verify if absent property can be traversed
	 * 
	 * @return boolean indicating if an absent property could be traversed.
	 */
	boolean traverseAbsentProperty(GenericEntity entity, Property property, GmTraversingContext context, TraversingModelPathElement pathElement);

}
