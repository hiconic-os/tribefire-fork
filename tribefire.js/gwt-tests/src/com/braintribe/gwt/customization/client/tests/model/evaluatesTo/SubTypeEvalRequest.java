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
package com.braintribe.gwt.customization.client.tests.model.evaluatesTo;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.eval.EvalContext;
import com.braintribe.model.generic.eval.Evaluator;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * In runtime GWT ITW we had an issue this leads to sub-type being woven before super-type is ready, and leading to an
 * NPE when accessing sub-type's super properties.
 * 
 * @author peter.gazdik
 */
public interface SubTypeEvalRequest extends GenericEntity {

	final EntityType<SubTypeEvalRequest> T = EntityTypes.T(SubTypeEvalRequest.class);

	EvalContext<SubTypeEvalResult> eval(Evaluator<SubTypeEvalRequest> evaluator);

}
