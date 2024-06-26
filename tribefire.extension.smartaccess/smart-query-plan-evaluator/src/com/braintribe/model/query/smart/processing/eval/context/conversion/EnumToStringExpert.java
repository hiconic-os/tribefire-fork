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

import com.braintribe.model.accessdeployment.smart.meta.conversion.EnumToString;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EnumType;
import com.braintribe.model.meta.GmEnumConstant;
import com.braintribe.model.processing.smartquery.eval.api.RuntimeSmartQueryEvaluationException;

/**
 * 
 */
public class EnumToStringExpert extends AbstractToStringExpert<Enum<?>, EnumToString> {

	public static final EnumToStringExpert INSTANCE = new EnumToStringExpert();

	private EnumToStringExpert() {
	}

	@Override
	protected Enum<?> parse(String value, EnumToString conversion) {
		Map<GmEnumConstant, String> valueMappings = conversion.getValueMappings();

		if (valueMappings == null || valueMappings.isEmpty()) {
			return parseEnum(value, conversion);
		}

		for (Entry<GmEnumConstant, String> entry: valueMappings.entrySet()) {
			if (entry.getValue().equals(value)) {
				return parseEnum(entry.getKey().getName(), conversion);
			}
		}

		throw new RuntimeSmartQueryEvaluationException("Cannot convert '" + value +
				"' to an enum. None of the configured GmEnumConstants matches this value. Enum: " +
				conversion.getEnumType().getTypeSignature());
	}

	private Enum<?> parseEnum(String value, EnumToString conversion) {
		EnumType enumType = GMF.getTypeReflection().getType(conversion.getEnumType().getTypeSignature());

		@SuppressWarnings("unchecked")
		Enum<?>[] enumConstants = ((Class<Enum<?>>) enumType.getJavaType()).getEnumConstants();

		for (Enum<?> e: enumConstants) {
			if (e.name().equals(value)) {
				return e;
			}
		}

		throw new RuntimeSmartQueryEvaluationException("Cannot convert '" + value + "' to an enum. No matching constant found for enum: " +
				enumType.getTypeSignature());
	}

	@Override
	protected String toString(Enum<?> value, EnumToString conversion) {
		Map<GmEnumConstant, String> valueMappings = conversion.getValueMappings();

		if (valueMappings == null || valueMappings.isEmpty()) {
			return value.name();
		}

		for (Entry<GmEnumConstant, String> entry: valueMappings.entrySet()) {
			if (entry.getKey().getName().equals(value.name())) {
				return entry.getValue();
			}
		}

		throw new RuntimeSmartQueryEvaluationException("Cannot convert '" + value +
				"' to a string. None of the configured GmEnumConstants matches this enum constant.");
	}

}
