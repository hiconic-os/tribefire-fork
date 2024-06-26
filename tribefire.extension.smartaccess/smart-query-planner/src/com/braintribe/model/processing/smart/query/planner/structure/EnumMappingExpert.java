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
package com.braintribe.model.processing.smart.query.planner.structure;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Collections;
import java.util.Map;

import com.braintribe.model.accessdeployment.smart.meta.ConversionEnumConstantAssignment;
import com.braintribe.model.accessdeployment.smart.meta.EnumConstantAssignment;
import com.braintribe.model.accessdeployment.smart.meta.IdentityEnumConstantAssignment;
import com.braintribe.model.accessdeployment.smart.meta.QualifiedEnumConstantAssignment;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlannerException;
import com.braintribe.model.processing.smart.query.planner.structure.adapter.EnumMapping;

/**
 * Component of {@link ModelExpert} which is able to resolve the {@link EnumMapping} for given smart enum type.
 * 
 * @see #resolveEnumMapping(GmEnumType)
 */
class EnumMappingExpert {

	private static final EnumConstantAssignment IDENTITY_CONSTANT_ASSIGNMENT = IdentityEnumConstantAssignment.T.createPlain();

	private final ModelExpert modelExpert;
	private final Map<GmEnumType, EnumMapping> enumMappingCache;

	public EnumMappingExpert(ModelExpert modelExpert) {
		this.modelExpert = modelExpert;
		this.enumMappingCache = newMap();
	}

	public EnumMapping resolveEnumMapping(GmEnumType smartEnumType) {
		EnumMapping result = enumMappingCache.get(smartEnumType);

		if (result == null) {
			result = resolveEnumMappingHelper(smartEnumType);
			enumMappingCache.put(smartEnumType, result);
		}

		return result;
	}

	private EnumMapping resolveEnumMappingHelper(GmEnumType smartEnumType) {
		EnumConstantMappingInfo info = resolveConstantMappingInfo(smartEnumType);

		return new EnumMapping(smartEnumType, info.isIdentity, info.delegateToSmartConstantMapping(), info.nameToAssignmentMappings());
	}

	private EnumConstantMappingInfo resolveConstantMappingInfo(GmEnumType gmEnumType) {
		EnumConstantMappingInfo result = new EnumConstantMappingInfo();

		for (GmEnumConstant gmEnumConstant: gmEnumType.getConstants()) {
			EnumConstantAssignment assignment = resolveEnumConstatnAssignmentOrDefault(gmEnumConstant);

			result.nameToAssignmentMappings.put(gmEnumConstant.getName(), assignment);

			if (assignment instanceof IdentityEnumConstantAssignment) {
				result.valueMappings.put(gmEnumConstant, gmEnumConstant.getName());

			} else if (assignment instanceof QualifiedEnumConstantAssignment) {
				QualifiedEnumConstantAssignment qa = (QualifiedEnumConstantAssignment) assignment;
				addValueMapping(result, gmEnumConstant, qa.getDelegateEnumConstant().getName());

			} else if (assignment instanceof ConversionEnumConstantAssignment) {
				ConversionEnumConstantAssignment ca = (ConversionEnumConstantAssignment) assignment;
				addValueMapping(result, gmEnumConstant, ca.getDelegateValue());

			} else {
				throw new SmartQueryPlannerException("Unknown EnumConstantAssignment type '" + assignment.getClass().getName() +
						"', value: " + assignment);
			}
		}

		return result;
	}

	private void addValueMapping(EnumConstantMappingInfo info, GmEnumConstant smartConstant, Object delegateValue) {
		info.valueMappings.put(smartConstant, delegateValue);

		/* Theoretically we could have effectively an identity mapping, even though it is specified as qualified. So we
		 * check whether it really is not identity and only then mark it as such... */
		if (!smartConstant.getName().equals(delegateValue))
			info.isIdentity = false;
	}

	private EnumConstantAssignment resolveEnumConstatnAssignmentOrDefault(GmEnumConstant gmEnumConstant) {
		EnumConstantAssignment result = modelExpert.resolveEnumConstantAssignmentIfPossible(gmEnumConstant);
		return result != null ? result : IDENTITY_CONSTANT_ASSIGNMENT;
	}

	private static class EnumConstantMappingInfo {
		public boolean isIdentity = true;
		public Map<GmEnumConstant, Object> valueMappings = newMap();
		public Map<String, EnumConstantAssignment> nameToAssignmentMappings = newMap();

		public Map<GmEnumConstant, Object> delegateToSmartConstantMapping() {
			return mapOrEmptyIfIdentity(valueMappings);
		}

		public Map<String, EnumConstantAssignment> nameToAssignmentMappings() {
			return mapOrEmptyIfIdentity(nameToAssignmentMappings);
		}

		private <K, V> Map<K, V> mapOrEmptyIfIdentity(Map<K, V> map) {
			return isIdentity ? Collections.<K, V> emptyMap() : map;
		}
	}

}
