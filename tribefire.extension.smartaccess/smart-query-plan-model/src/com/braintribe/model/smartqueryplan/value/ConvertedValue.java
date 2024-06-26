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
package com.braintribe.model.smartqueryplan.value;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;

import com.braintribe.model.queryplan.value.Value;
import com.braintribe.model.queryplan.value.ValueType;

/**
 * 
 */
public interface ConvertedValue extends SmartValue {

	EntityType<ConvertedValue> T = EntityTypes.T(ConvertedValue.class);

	Value getOperand();
	void setOperand(Value operand);

	SmartConversion getConversion();
	void setConversion(SmartConversion conversion);

	boolean getInverse();
	void setInverse(boolean inverse);

	@Override
	default ValueType valueType() {
		return ValueType.extension;
	}

	@Override
	default SmartValueType smartValueType() {
		return SmartValueType.convertedValue;
	}

}
