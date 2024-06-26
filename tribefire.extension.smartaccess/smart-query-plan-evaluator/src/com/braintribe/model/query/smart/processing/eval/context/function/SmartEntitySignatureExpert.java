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
package com.braintribe.model.query.smart.processing.eval.context.function;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

import com.braintribe.model.processing.query.eval.api.QueryEvaluationContext;
import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.processing.smartquery.eval.api.function.SignatureSelectionOperand;
import com.braintribe.model.processing.smartquery.eval.api.function.SmartQueryFunctionExpert;
import com.braintribe.model.query.PropertyOperand;
import com.braintribe.model.query.Source;
import com.braintribe.model.query.functions.EntitySignature;
import com.braintribe.model.query.smart.processing.SmartQueryEvaluatorRuntimeException;
import com.braintribe.model.queryplan.value.Value;

/**
 * 
 */
public class SmartEntitySignatureExpert implements SmartQueryFunctionExpert<EntitySignature> {

	public static final SmartEntitySignatureExpert INSTANCE = new SmartEntitySignatureExpert();

	private SmartEntitySignatureExpert() {
	}

	@Override
	public Object evaluate(Tuple tuple, EntitySignature signatureFunction, Map<Object, Value> operandMappings,
			QueryEvaluationContext context) {

		Value operandValue = operandMappings.get(getSignatureOperand(signatureFunction));
		return context.resolveValue(tuple, operandValue);
	}

	@Override
	public Collection<SignatureSelectionOperand> listOperandsToSelect(EntitySignature signatureFunction) {
		return Arrays.asList(getSignatureOperand(signatureFunction));
	}

	private SignatureSelectionOperand getSignatureOperand(EntitySignature signatureFunction) {
		SignatureSelectionOperand result = SignatureSelectionOperand.T.createPlain();
		result.setSource(extractSource(signatureFunction));

		return result;
	}

	private Source extractSource(EntitySignature signatureFunction) {
		Object operand = signatureFunction.getOperand();

		if (operand instanceof Source) {
			return (Source) operand;
		}

		if (operand instanceof PropertyOperand) {
			PropertyOperand po = (PropertyOperand) operand;

			if (po.getPropertyName() != null) {
				throw new SmartQueryEvaluatorRuntimeException(
						"Cannot resolve type signature. Source operand expected, not property one. Property: " + po.getPropertyName());
			}

			return po.getSource();
		}

		throw new RuntimeException("Cannot resolve type signature. Source operand expected, but found: ");
	}
}
