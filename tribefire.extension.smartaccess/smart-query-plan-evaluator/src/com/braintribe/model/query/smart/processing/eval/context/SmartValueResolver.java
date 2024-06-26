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
package com.braintribe.model.query.smart.processing.eval.context;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Map;

import com.braintribe.model.accessdeployment.smart.meta.conversion.SmartConversion;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.query.eval.api.QueryEvaluationContext;
import com.braintribe.model.processing.query.eval.api.RuntimeQueryEvaluationException;
import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.processing.smartquery.eval.api.ConversionDirection;
import com.braintribe.model.processing.smartquery.eval.api.SmartConversionExpert;
import com.braintribe.model.query.smart.processing.SmartQueryEvaluatorRuntimeException;
import com.braintribe.model.smartqueryplan.value.CompositeDiscriminatorBasedSignature;
import com.braintribe.model.smartqueryplan.value.ConvertedValue;
import com.braintribe.model.smartqueryplan.value.SimpleDiscriminatorBasedSignature;
import com.braintribe.model.smartqueryplan.value.SmartEntitySignature;
import com.braintribe.model.smartqueryplan.value.SmartValue;

/**
 * 
 */
class SmartValueResolver {

	private final QueryEvaluationContext context;
	private final Map<EntityType<? extends SmartConversion>, SmartConversionExpert<?>> experts;
	private final Map<ConvertedValue, SmartConversionExpert<SmartConversion>> expertsForValues;

	public SmartValueResolver(QueryEvaluationContext context, Map<EntityType<? extends SmartConversion>, SmartConversionExpert<?>> experts) {
		this.context = context;
		this.experts = experts;
		this.expertsForValues = newMap();
	}

	public <T> T resolve(Tuple tuple, SmartValue value) {
		return (T) resolve(value, tuple);
	}

	private Object resolve(SmartValue value, Tuple tuple) {
		switch (value.smartValueType()) {
			case compositeDiscriminatorBasedSignature:
				return resolveValue((CompositeDiscriminatorBasedSignature) value, tuple);
			case convertedValue:
				return resolveValue((ConvertedValue) value, tuple);
			case simpleDiscriminatorBasedSignature:
				return resolveValue((SimpleDiscriminatorBasedSignature) value, tuple);
			case smartEntitySignature:
				return resolveValue((SmartEntitySignature) value, tuple);
		}

		throw new RuntimeQueryEvaluationException("Unsupported SmartValue: " + value + " of type: " + value.valueType());
	}

	private Object resolveValue(ConvertedValue value, Tuple tuple) {
		Object operandValue = context.resolveValue(tuple, value.getOperand());

		SmartConversionExpert<SmartConversion> expert = getExpertFor(value);
		ConversionDirection dir = value.getInverse() ? ConversionDirection.smart2Delegate : ConversionDirection.delegate2Smart;

		return expert.convertValue(value.getConversion(), operandValue, dir);
	}

	private SmartConversionExpert<SmartConversion> getExpertFor(ConvertedValue value) {
		SmartConversionExpert<SmartConversion> result = expertsForValues.get(value);

		if (result == null) {
			EntityType<?> conversionType = value.getConversion().entityType();
			result = (SmartConversionExpert<SmartConversion>) experts.get(conversionType);

			if (result == null) {
				throw new RuntimeQueryEvaluationException("No expert found for conversion:" + conversionType.getTypeSignature());
			}

			expertsForValues.put(value, result);
		}

		return result;
	}

	/** May return <tt>null</tt> iff the delegate property is null, which only happens in case of a left join. */
	private Object resolveValue(SmartEntitySignature value, Tuple tuple) {
		if (value.getSignature() != null) {
			return value.getSignature();
		}

		Object delegateSignature = tuple.getValue(value.getTuplePosition());
		if (delegateSignature == null) {
			// This can happen if we did a left-join
			return null;
		}

		String smartSignature = value.getSignatureMapping().get(delegateSignature);

		if (smartSignature == null) {
			throw new SmartQueryEvaluatorRuntimeException(
					"Cannot instantiate entity, unable to determine the actual type. Delegate type: " + delegateSignature +
							". Signature mapping: " + value.getSignatureMapping() + ". Tuple position: " + value.getTuplePosition() +
							". Tuple: " + tuple);
		}

		return smartSignature;
	}

	private Object resolveValue(SimpleDiscriminatorBasedSignature value, Tuple tuple) {
		Object delegateSignature = tuple.getValue(value.getTuplePosition());
		String smartSignature = value.getSignatureMapping().get(delegateSignature);

		if (smartSignature == null) {
			throw new SmartQueryEvaluatorRuntimeException(
					"Cannot instantiate entity, unable to determine the actual type. Delegate type: " + delegateSignature +
							". Signature mapping: " + value.getSignatureMapping());
		}

		return smartSignature;
	}

	@SuppressWarnings("unused")
	private Object resolveValue(CompositeDiscriminatorBasedSignature value, Tuple tuple) {
		throw new UnsupportedOperationException("Method 'SmartValueResolver.resolveValue' is not supported yet!");
	}

}
