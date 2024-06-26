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
package com.braintribe.model.processing.vde.evaluator.impl.approximate.floor;

import com.braintribe.model.bvd.math.Floor;
import com.braintribe.model.processing.vde.evaluator.api.VdeRuntimeException;
import com.braintribe.model.processing.vde.evaluator.api.approximate.ApproximateEvalExpert;
import com.braintribe.model.processing.vde.evaluator.impl.approximate.ApproximateVdeUtil;

/**
 * Expert for {@link Floor} that operates on value of type Float and precision of type Number 
 *
 */
public class FloatFloor implements ApproximateEvalExpert<Float, Number> {

	private static FloatFloor instance = null;

	protected FloatFloor() {
		// empty
	}

	public static FloatFloor getInstance() {
		if (instance == null) {
			instance = new FloatFloor();
		}
		return instance;
	}
	
	@Override
	public Object evaluate(Float firstOperand, Number secondOperand) throws VdeRuntimeException {
		Float result = null;
		if (firstOperand >= 0){
			result = ApproximateVdeUtil.getFloor(firstOperand, secondOperand).floatValue();
		}
		else{
			result = -1.0f * ApproximateVdeUtil.getCeil(-1*firstOperand, secondOperand).floatValue();
		}
		return result;
	}

}
