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
package com.braintribe.model.access.smart.manipulation.tools;

import java.util.Collection;
import java.util.Map;

import com.braintribe.model.access.smart.manipulation.SmartManipulationContextVariables;
import com.braintribe.model.access.smart.manipulation.SmartManipulationProcessor;
import com.braintribe.model.accessdeployment.smart.meta.DirectPropertyAssignment;
import com.braintribe.model.accessdeployment.smart.meta.PropertyAssignment;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.manipulation.AddManipulation;
import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.ManipulationType;
import com.braintribe.model.generic.manipulation.PropertyManipulation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.processing.smart.SmartAccessException;
import com.braintribe.model.processing.smart.query.planner.tools.SmartMappingTools;

/**
 * @author peter.gazdik
 */
public class SmartManipulationValidator {

	private final SmartManipulationContextVariables $;

	private EntityType<?> currentAllowedReferencedType;

	public SmartManipulationValidator(SmartManipulationProcessor smp) {
		this.$ = smp.context();
	}

	public void validate(PropertyManipulation smartManipulation, PropertyAssignment pa) {
		if (!needsValidation(smartManipulation, pa)) {
			return;
		}

		currentAllowedReferencedType = resolveEntityTypeFromPropertyAssignmentIfPossible(pa);

		switch (smartManipulation.manipulationType()) {
			case ADD:
				validateManipulation((AddManipulation) smartManipulation);
				return;

			case CHANGE_VALUE:
				validateManipulation((ChangeValueManipulation) smartManipulation);
				return;
				
			default:
				return;
		}
	}

	// #############################################################################
	// ## . . . . . . . . . . . . . Actual validation . . . . . . . . . . . . . . ##
	// #############################################################################

	private void validateManipulation(AddManipulation smartManipulation) {
		validateMap(smartManipulation.getItemsToAdd());
	}

	private void validateManipulation(ChangeValueManipulation cvm) {
		validateValue(cvm.getNewValue());
	}

	private void validateValue(Object value) {
		if (value == null) {
			return;
		}

		if (value instanceof Collection) {
			validateCollection((Collection<?>) value);
			return;
		}

		if (value instanceof Map) {
			validateMap((Map<?, ?>) value);
			return;
		}

		EntityReference castedValue = (EntityReference) value;
		String valueSignature = castedValue.getTypeSignature();
		EntityType<?> valueEntityType = GMF.getTypeReflection().getEntityType(valueSignature);

		if (!currentAllowedReferencedType.isAssignableFrom(valueEntityType)) {
			throw new SmartAccessException("Invalid value");
		}
	}

	private void validateCollection(Collection<?> collection) {
		for (Object value: collection) {
			validateValue(value);
		}
	}

	private void validateMap(Map<?, ?> map) {
		validateCollection(map.values());
	}

	// #############################################################################
	// ## . . . . . . Verifying whether manipulation needs validation . . . . . . ##
	// #############################################################################

	private boolean needsValidation(PropertyManipulation smartManipulation, PropertyAssignment pa) {
		if (!$.currentSmartPropertyReferencesUnmappedType) {
			return false;
		}

		if (pa instanceof DirectPropertyAssignment) {
			return false;
		}

		if (!isReferenceAddingManipulation(smartManipulation)) {
			return false;
		}

		return true;
	}

	private boolean isReferenceAddingManipulation(PropertyManipulation smartManipulation) {
		ManipulationType type = smartManipulation.manipulationType();
		return type == ManipulationType.ADD || type == ManipulationType.CHANGE_VALUE;
	}

	/* I want to see the warning - at the end we should check if we want to decide this here, or if we replace
	 * currentSmartReferencedEntityTypeMappings with boolean for this value */
	@SuppressWarnings("unused")
	private boolean isCurrentPropertyForUnmappedType() {
		return $.currentSmartPropertyReferencesUnmappedType;
	}

	private EntityType<?> resolveEntityTypeFromPropertyAssignmentIfPossible(PropertyAssignment pa) {
		// this doesn't throw NPE, as pa isn't DirectPropertyAssignment, thus getJoinedProperty() method doesn't return null
		GmEntityType gmEntityType = SmartMappingTools.getJoinedProperty(pa).getEntityType();
		return GMF.getTypeReflection().getEntityType(gmEntityType.getTypeSignature());
	}

}
