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
package com.braintribe.model.processing.vde.evaluator.impl.bvd.convert;

import java.util.Collection;
import java.util.Map;

import com.braintribe.model.bvd.convert.ToCollection;
import com.braintribe.model.processing.vde.evaluator.api.ValueDescriptorEvaluator;
import com.braintribe.model.processing.vde.evaluator.api.VdeContext;
import com.braintribe.model.processing.vde.evaluator.api.VdeResult;
import com.braintribe.model.processing.vde.evaluator.api.VdeRuntimeException;
import com.braintribe.model.processing.vde.evaluator.impl.VdeResultImpl;

public class ToCollectionVde<T extends ToCollection> implements ValueDescriptorEvaluator<T> {

	@Override
	public VdeResult evaluate(VdeContext context, T valueDescriptor) throws VdeRuntimeException {

		Object operand = context.evaluate(valueDescriptor.getOperand());
		Collection<Object> toCollection = valueDescriptor.newCollection();
		
		if (operand instanceof Collection<?>) {
			toCollection.addAll((Collection<?>)operand);
		} else if (operand instanceof Map<?, ?>) {
			toCollection.addAll(((Map<?,?>)operand).keySet());
		} else {
			toCollection.add(operand);
		}
		
		return new VdeResultImpl(toCollection, false);
	}

}
