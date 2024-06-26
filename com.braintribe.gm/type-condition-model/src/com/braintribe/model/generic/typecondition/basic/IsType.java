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
package com.braintribe.model.generic.typecondition.basic;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.typecondition.TypeCondition;
import com.braintribe.model.generic.typecondition.TypeConditionType;
import com.braintribe.model.meta.GmType;

public interface IsType extends TypeCondition {

	EntityType<IsType> T = EntityTypes.T(IsType.class);

	String getTypeSignature();
	void setTypeSignature(String typeSignature);


	@Override
	default boolean matches(GenericModelType type) {
		String typeSig = getTypeSignature();
		return typeSig != null && typeSig.equals(type.getTypeSignature());
	}
	
	@Override
	default boolean matches(GmType type) {
		String typeSig = getTypeSignature();
		return typeSig != null && typeSig.equals(type.getTypeSignature());
	}


	@Override
	default TypeConditionType tcType() {
		return TypeConditionType.isType;
	}

}
