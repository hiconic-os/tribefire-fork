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
package com.braintribe.model.bvd.math;

import com.braintribe.model.generic.annotation.Abstract;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

import com.braintribe.model.generic.value.type.ImplicitlyTypedDescriptor;

/**
 * A {@link ImplicitlyTypedDescriptor} that performs approximate operation on a
 * value with respect to a precision. Allowed types are numeric ( int, long,
 * float, double, decimal) and date
 * 
 */
@Abstract
public interface ApproximateOperation extends ImplicitlyTypedDescriptor {

	final EntityType<ApproximateOperation> T = EntityTypes.T(ApproximateOperation.class);

	Object getValue(); // Date, int, long, float, double, decimal

	void setValue(Object value);

	Object getPrecision(); // DateOffset, int, long, float, double, decimal

	void setPrecision(Object precision);

}
