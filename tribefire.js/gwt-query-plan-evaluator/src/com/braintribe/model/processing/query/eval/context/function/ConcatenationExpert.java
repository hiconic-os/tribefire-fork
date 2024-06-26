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
package com.braintribe.model.processing.query.eval.context.function;

import static com.braintribe.model.processing.query.eval.context.function.QueryFunctionEvalTools.resolveOperandValue;

import java.util.Map;

import com.braintribe.model.processing.query.eval.api.QueryEvaluationContext;
import com.braintribe.model.processing.query.eval.api.Tuple;
import com.braintribe.model.processing.query.eval.api.function.QueryFunctionExpert;
import com.braintribe.model.query.functions.value.Concatenation;
import com.braintribe.model.queryplan.value.Value;

/**
 * 
 */
public class ConcatenationExpert implements QueryFunctionExpert<Concatenation> {

	public static final ConcatenationExpert INSTANCE = new ConcatenationExpert();

	private ConcatenationExpert() {
	}

	@Override
	public Object evaluate(Tuple tuple, Concatenation concatenation, Map<Object, Value> operandMappings, QueryEvaluationContext context) {
		StringBuilder sb = new StringBuilder();

		for (Object o: concatenation.getOperands()) {
			Object s = resolveOperandValue(context, tuple, operandMappings, o);
			sb.append(s);
		}

		return sb.toString();
	}

}