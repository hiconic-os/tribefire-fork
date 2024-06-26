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
package com.braintribe.model.processing.smart.query.planner.structure.adapter;

import static com.braintribe.model.processing.smart.query.planner.core.builder.SmartValueBuilder.enumToSimpleValueConversion;
import static com.braintribe.model.processing.smart.query.planner.core.builder.SmartValueBuilder.identityEnumToStringConversion;
import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Map;
import java.util.Map.Entry;

import com.braintribe.model.accessdeployment.smart.meta.ConversionEnumConstantAssignment;
import com.braintribe.model.accessdeployment.smart.meta.EnumConstantAssignment;
import com.braintribe.model.accessdeployment.smart.meta.IdentityEnumConstantAssignment;
import com.braintribe.model.accessdeployment.smart.meta.QualifiedEnumConstantAssignment;
import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EnumType;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.meta.GmEnumType;
import com.braintribe.model.processing.smart.query.planner.SmartQueryPlannerException;
import com.braintribe.model.processing.smartquery.eval.api.RuntimeSmartQueryEvaluationException;

/**
 * 
 */
public class EnumMapping {

	private final GmEnumType smartEnumType;

	private final boolean isIdentity;
	private final Map<GmEnumConstant, Object> valueMappings;
	private final Map<String, EnumConstantAssignment> nameToAssignmentMappings;

	private SmartConversion enumConversion;
	private Map<String, Object> enumNameMappings;

	public EnumMapping(GmEnumType smartEnumType, boolean isIdentity, Map<GmEnumConstant, Object> valueMappings,
			Map<String, EnumConstantAssignment> nameToAssignmentMappings) {

		this.smartEnumType = smartEnumType;
		this.isIdentity = isIdentity;
		this.valueMappings = valueMappings;
		this.nameToAssignmentMappings = nameToAssignmentMappings;
	}

	public GmEnumType getSmartEnumType() {
		return smartEnumType;
	}

	public SmartConversion getConversion() {
		if (enumConversion == null) {
			enumConversion = isIdentity ? identityEnumToStringConversion(smartEnumType) : enumToSimpleValueConversion(valueMappings);
		}

		return enumConversion;
	}

	/**
	 * Converts given smart-level enum value to the corresponding delegate value.
	 */
	public Object convertToDelegateValaue(Enum<?> smartValue) {
		return isIdentity ? smartValue : convertToDelegateHelper(smartValue);
	}

	private Object convertToDelegateHelper(Enum<?> smartValue) {
		if (enumNameMappings == null) {
			enumNameMappings = buildEnumNameMappings();
		}

		Object result = enumNameMappings.get(smartValue.name());

		if (result == null) {
			throw new RuntimeSmartQueryEvaluationException("Error while converting smart enum value '" + smartValue +
					"' to delegate value. No mapping exists for this constant. Found mappings: " + enumNameMappings);
		}

		return result;
	}

	private Map<String, Object> buildEnumNameMappings() {
		Map<String, Object> result = newMap();

		for (Entry<String, EnumConstantAssignment> entry: nameToAssignmentMappings.entrySet()) {
			String smartEnumName = entry.getKey();
			EnumConstantAssignment assignment = entry.getValue();

			result.put(smartEnumName, getValue(smartEnumName, assignment));
		}

		return result;
	}

	private Object getValue(String name, EnumConstantAssignment assignment) {
		if (assignment instanceof IdentityEnumConstantAssignment) {
			return getConstant(smartEnumType, name);

		} else if (assignment instanceof QualifiedEnumConstantAssignment) {
			QualifiedEnumConstantAssignment qa = (QualifiedEnumConstantAssignment) assignment;
			GmEnumConstant delegateEnumConstant = qa.getDelegateEnumConstant();

			return getConstant(delegateEnumConstant.getDeclaringType(), delegateEnumConstant.getName());

		} else if (assignment instanceof ConversionEnumConstantAssignment) {
			ConversionEnumConstantAssignment ca = (ConversionEnumConstantAssignment) assignment;
			return ca.getDelegateValue();

		} else {
			throw new SmartQueryPlannerException("Unknown EnumConstantAssignment type '" + assignment.getClass().getName() +
					"', value: " + assignment);
		}
	}

	private Object getConstant(GmEnumType gmEnumType, String name) {
		EnumType enumType = GMF.getTypeReflection().getType(gmEnumType.getTypeSignature());

		for (Enum<?> value: enumType.getEnumValues()) {
			if (value.name().equals(name)) {
				return value;
			}
		}

		throw new SmartQueryPlannerException("Enum constant not found: '" + enumType.getTypeSignature() + "#" + name + "'");
	}
}
