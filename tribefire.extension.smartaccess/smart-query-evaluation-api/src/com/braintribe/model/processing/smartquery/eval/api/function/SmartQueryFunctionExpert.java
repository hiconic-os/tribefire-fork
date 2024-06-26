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
package com.braintribe.model.processing.smartquery.eval.api.function;

import java.util.Collection;

import com.braintribe.model.processing.query.eval.api.function.QueryFunctionExpert;
import com.braintribe.model.query.Operand;
import com.braintribe.model.query.Source;
import com.braintribe.model.query.functions.EntitySignature;
import com.braintribe.model.query.functions.QueryFunction;

/**
 * Customization of {@link QueryFunctionExpert} for SmartAccess. SmartAccess can also work with standard
 * {@link QueryFunctionExpert}s, but in some cases, it is not enough. The reason is, that the
 * {@link #listOperandsToSelect(QueryFunction)} function is used for two different things - when normalizing the query
 * (there it needs to provide all operands it contains, so that they themselves can be normalized), and when determining
 * which values need to be retrieved from the delegate (in the planner).
 * 
 * In this second case, we might want to work with different operands. For example, the {@link EntitySignature} function
 * can have an operand of type {@link Source}, but we do not need to retrieve the entire entity from the delegate, just
 * it's signature. In such case, if a {@link SmartQueryFunctionExpert} is configured for given function, it's
 * {@link #listOperandsToSelect(QueryFunction)} is used for this purpose.
 */
public interface SmartQueryFunctionExpert<F extends QueryFunction> extends QueryFunctionExpert<F> {

	Collection<? extends Operand> listOperandsToSelect(F queryFunction);

}
