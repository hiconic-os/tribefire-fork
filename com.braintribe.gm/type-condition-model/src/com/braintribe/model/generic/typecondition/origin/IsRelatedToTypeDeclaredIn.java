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
package com.braintribe.model.generic.typecondition.origin;

import com.braintribe.model.generic.reflection.CustomType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;
import com.braintribe.model.generic.reflection.LinearCollectionType;
import com.braintribe.model.generic.reflection.MapType;
import com.braintribe.model.generic.reflection.Model;
import com.braintribe.model.generic.reflection.TypeCode;
import com.braintribe.model.generic.typecondition.TypeCondition;
import com.braintribe.model.generic.typecondition.TypeConditionType;
import com.braintribe.model.meta.GmCollectionType;
import com.braintribe.model.meta.GmCustomType;
import com.braintribe.model.meta.GmLinearCollectionType;
import com.braintribe.model.meta.GmMapType;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.meta.GmType;

/**
 * Experimental. Not fully supported yet, use at your own risk!!!
 * 
 * Matches a {@link GmCustomType} if it is declared in the model of given name, or a {@link GmCollectionType} if the
 * type parameter of that collection (or one of the parameters two in case of a map) is a {@link GmCustomType} declared
 * in such a model.
 */
public interface IsRelatedToTypeDeclaredIn extends TypeCondition {

	EntityType<IsRelatedToTypeDeclaredIn> T = EntityTypes.T(IsRelatedToTypeDeclaredIn.class);

	String getModelName();
	void setModelName(String modelName);

	@Override
	default boolean matches(GenericModelType type) {
		String modelName = getModelName();
		if (modelName == null)
			return false;

		if (type.isBase() || type.isSimple())
			return GenericModelTypeReflection.rootModelName.equals(modelName);

		if (type.isCollection()) {
			if (type.getTypeCode() == TypeCode.mapType) {
				MapType mapType = (MapType) type;

				return matches(mapType.getKeyType()) || //
						matches(mapType.getValueType());

			} else {
				return matches(((LinearCollectionType) type).getCollectionElementType());
			}
		}

		CustomType ct = (CustomType) type;
		Model model = ct.getModel();
		if (model == null)
			throw new IllegalArgumentException("Cannot evaluate 'IsDeclaredIn' on a type that doesn't have a model: " + type.getTypeSignature());

		return modelName.equals(model.name());
	}

	@Override
	default boolean matches(GmType type) {
		String modelName = getModelName();
		if (modelName == null)
			return false;

		if (!type.isGmCollection()) {
			GmMetaModel m = type.getDeclaringModel();
			return m != null && modelName.equals(m.getName());
		}

		if (type instanceof GmLinearCollectionType)
			return matches(((GmLinearCollectionType) type).getElementType());

		if (type instanceof GmMapType) {
			GmMapType mapType = (GmMapType) type;

			return matches(mapType.getKeyType()) || //
					matches(mapType.getValueType());
		}

		return false;
	}

	@Override
	default TypeConditionType tcType() {
		return TypeConditionType.isRelatedToTypeDeclaredIn;
	}

}
