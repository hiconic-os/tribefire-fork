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
package com.braintribe.model.queryplan.value;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;



/**
 * A value specified directly in the query, something like the string "John" in: <code>select * from Person p where p.name = "John"</code>
 */

public interface StaticValue extends ConstantValue {

	EntityType<StaticValue> T = EntityTypes.T(StaticValue.class);

	Object getValue();

	void setValue(Object value);

	@Override
	default ValueType valueType() {
		return ValueType.staticValue;
	}

}
