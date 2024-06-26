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
package tribefire.extension.elasticsearch.model.api.request.doc.conditions;

import com.braintribe.model.generic.annotation.meta.Priority;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import tribefire.extension.elasticsearch.model.api.request.doc.Comparison;
import tribefire.extension.elasticsearch.model.api.request.doc.Operator;

public interface ValueComparison extends Comparison {

	EntityType<ValueComparison> T = EntityTypes.T(ValueComparison.class);

	@Priority(0.82d)
	Operator getOperator();
	void setOperator(Operator operator);

	/**
	 * An operand is either a static value (String, Integer, Date or an instance of Condition.
	 */
	@Priority(0.81d)
	Object getLeftOperand();
	void setLeftOperand(Object leftOperand);

	@Priority(0.8d)
	Object getRightOperand();
	void setRightOperand(Object rightOperand);

	static ValueComparison compare(Object op1, Operator operator, Object op2) {
		ValueComparison comparision = ValueComparison.T.create();
		comparision.setLeftOperand(op1);
		comparision.setRightOperand(op2);
		comparision.setOperator(operator);
		return comparision;
	}

	static ValueComparison eq(Object op1, Object op2) {
		return compare(op1, Operator.equal, op2);
	}

	static ValueComparison ne(Object op1, Object op2) {
		return compare(op1, Operator.notEqual, op2);
	}

	static ValueComparison gt(Object op1, Object op2) {
		return compare(op1, Operator.greater, op2);
	}

	static ValueComparison ge(Object op1, Object op2) {
		return compare(op1, Operator.greaterOrEqual, op2);
	}

	static ValueComparison lt(Object op1, Object op2) {
		return compare(op1, Operator.less, op2);
	}

	static ValueComparison le(Object op1, Object op2) {
		return compare(op1, Operator.lessOrEqual, op2);
	}

}
