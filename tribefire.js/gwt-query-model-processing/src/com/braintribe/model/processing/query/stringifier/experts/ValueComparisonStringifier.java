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
package com.braintribe.model.processing.query.stringifier.experts;

import com.braintribe.model.processing.query.api.stringifier.QueryStringifierRuntimeException;
import com.braintribe.model.processing.query.api.stringifier.experts.Stringifier;
import com.braintribe.model.processing.query.stringifier.BasicQueryStringifierContext;
import com.braintribe.model.query.Operator;
import com.braintribe.model.query.conditions.ValueComparison;

public class ValueComparisonStringifier implements Stringifier<ValueComparison, BasicQueryStringifierContext> {
	@Override
	public String stringify(ValueComparison valueComparison, BasicQueryStringifierContext context) throws QueryStringifierRuntimeException {
		StringBuilder queryString = new StringBuilder();

		// Get operator set equal as default operator
		Operator operator = valueComparison.getOperator();
		operator = (operator == null ? Operator.equal : operator);

		// Stringify and add left, right operand and operator
		context.stringifyAndAppend(valueComparison.getLeftOperand(), queryString);
		queryString.append(" ");
		queryString.append(Operator.getSignToOperator(operator));
		queryString.append(" ");
		context.stringifyAndAppend(valueComparison.getRightOperand(), queryString);

		return queryString.toString();
	}
}
