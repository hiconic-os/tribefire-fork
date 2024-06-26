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
package com.braintribe.model.query.smart.processing.eval.context.conversion;

import java.util.Map;
import java.util.Map.Entry;

import com.braintribe.model.accessdeployment.smart.meta.conversion.EnumToSimpleValue;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EnumType;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.processing.smartquery.eval.api.RuntimeSmartQueryEvaluationException;

/**
 * 
 */
public class EnumToSimpleValueExpert extends AbstractToSimpleTypeExpert<Enum<?>, EnumToSimpleValue, Object> {

	public static final EnumToSimpleValueExpert INSTANCE = new EnumToSimpleValueExpert();

	private EnumToSimpleValueExpert() {
	}

	@Override
	protected Enum<?> parse(Object value, EnumToSimpleValue conversion) {
		Map<GmEnumConstant, Object> valueMappings = conversion.getValueMappings();

		for (Entry<GmEnumConstant, Object> entry: valueMappings.entrySet()) {
			if (entry.getValue().equals(value)) {
				return parseEnum(entry.getKey());
			}
		}

		throw new RuntimeSmartQueryEvaluationException("Cannot convert '" + value +
				"' to an enum. None of the configured GmEnumConstants matches this value. Mappings: " + conversion.getValueMappings());
	}

	private Enum<?> parseEnum(GmEnumConstant constant) {
		EnumType enumType = GMF.getTypeReflection().getType(constant.getDeclaringType().getTypeSignature());

		@SuppressWarnings("unchecked")
		Enum<?>[] enumConstants = ((Class<Enum<?>>) enumType.getJavaType()).getEnumConstants();

		for (Enum<?> e: enumConstants) {
			if (e.name().equals(constant.getName())) {
				return e;
			}
		}

		throw new RuntimeSmartQueryEvaluationException("Cannot convert '" + constant +
				"' to an enum. No matching constant found for enum: " + enumType.getTypeSignature());
	}

	@Override
	protected Object toSimpleValue(Enum<?> value, EnumToSimpleValue conversion) {
		Map<GmEnumConstant, Object> valueMappings = conversion.getValueMappings();

		for (Entry<GmEnumConstant, Object> entry: valueMappings.entrySet()) {
			if (entry.getKey().getName().equals(value.name())) {
				return entry.getValue();
			}
		}

		throw new RuntimeSmartQueryEvaluationException("Cannot convert '" + value +
				"' to a string. None of the configured GmEnumConstants matches this enum constant.");
	}

}
