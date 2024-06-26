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
package com.braintribe.model.processing.smart.query.planner.core.combination;

import static com.braintribe.utils.lcd.CollectionTools2.newSet;

import java.util.Set;

import com.braintribe.model.processing.smart.query.planner.graph.EntitySourceNode;
import com.braintribe.model.query.conditions.Condition;

/**
 * A wrapper for {@link EntitySourceNode} related to given {@link Condition}, with an additional flag which might tell
 * this condition is not delegate-able no matter what.
 */
public class ConditionExaminationDescription {

	public Set<EntitySourceNode> affectedSourceNodes = newSet();

	/* This might be set to false, which means there is some special reason why we cannot delegate given condition.
	 * 
	 * Currently, such case happens if we have a condition on a converted property, and this condition is not
	 * equality-based (equal, notEqual, in, contains). So for example if we try to compare our converted property using
	 * greaterThan, we cannot delegate such condition, because the conversion may not be preserving the intended order. */
	public boolean delegateable = true;

}
