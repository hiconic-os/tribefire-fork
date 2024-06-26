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

import com.braintribe.model.access.smart.manipulation.SmartManipulationProcessor;
import com.braintribe.model.access.smart.manipulation.conversion.ResolveDelegateValueConversion;
import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.generic.value.EntityReference;
import com.braintribe.model.processing.smart.SmartAccessException;
import com.braintribe.model.processing.smartquery.eval.api.ConversionDirection;
import com.braintribe.model.processing.smartquery.eval.api.SmartConversionExpert;

/**
 * 
 */
class ResolveDelegateValueConversionExpert implements SmartConversionExpert<ResolveDelegateValueConversion> {

	private final SmartManipulationProcessor smp;
	private final PropertyValueResolver propertyValueResolver;
	private final ValueConverter valueConverter;

	public ResolveDelegateValueConversionExpert(SmartManipulationProcessor smp, ValueConverter valueConverter) {
		this.smp = smp;
		this.propertyValueResolver = smp.propertyValueResolver();
		this.valueConverter = valueConverter;
	}

	@Override
	public Object convertValue(ResolveDelegateValueConversion conversion, Object value, ConversionDirection direction) {
		if (direction != ConversionDirection.smart2Delegate) {
			throw new SmartAccessException("This direction of conversion is not supported by this expert!");
		}

		EntityReference ref = (EntityReference) value;

		String propertyName = conversion.getPropertyName();
		propertyName = smp.findDelegatePropertyForKeyPropertyOfCurrentSmartType(propertyName, ref);

		SmartConversion nestedConversion = conversion.getNestedConversion();

		Object simpleValue = propertyValueResolver.acquireDelegatePropertyValue(ref, propertyName);

		if (nestedConversion != null) {
			simpleValue = convert(nestedConversion, simpleValue);
		}

		return simpleValue;
	}

	private Object convert(SmartConversion nestedConversion, Object simpleValue) {
		return valueConverter.convertToDelegate(simpleValue, nestedConversion, false);
	}
}
