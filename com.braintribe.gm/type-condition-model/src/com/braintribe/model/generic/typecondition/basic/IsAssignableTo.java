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

import java.util.List;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.typecondition.TypeCondition;
import com.braintribe.model.generic.typecondition.TypeConditionType;
import com.braintribe.model.meta.GmEntityType;
import com.braintribe.model.meta.GmType;

public interface IsAssignableTo extends TypeCondition {

	EntityType<IsAssignableTo> T = EntityTypes.T(IsAssignableTo.class);

	String getTypeSignature();
	void setTypeSignature(String typeSignature);

	@Override
	default boolean matches(GenericModelType type) {
		String typeSig = getTypeSignature();
		if (typeSig == null)
			return false;

		GenericModelType thisType = GMF.getTypeReflection().findType(typeSig);
		if (thisType == null)
			return false;

		return thisType.isAssignableFrom(type);
	}

	@Override
	default boolean matches(GmType type) {
		String typeSignature = getTypeSignature();
		if (typeSignature == null)
			return false;

		return isAssignableTo(type, typeSignature);
	}

	static boolean isAssignableTo(GmType type, String superSignature) {
		if (type.getTypeSignature().equals(superSignature))
			return true;

		switch (type.typeKind()) {
			case ENTITY:
				List<GmEntityType> superTypes = ((GmEntityType) type).getSuperTypes();

				if (superTypes != null)
					for (GmEntityType superType : superTypes)
						if (isAssignableTo(superType, superSignature))
							return true;

				return false;

			case BASE:
				return false;

			default:
				return "object".equals(superSignature);
		}
	}

	@Override
	default TypeConditionType tcType() {
		return TypeConditionType.isAssignableTo;
	}

}
