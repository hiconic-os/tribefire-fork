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
import com.braintribe.model.generic.reflection.TypeCode;
import com.braintribe.model.generic.typecondition.TypeCondition;
import com.braintribe.model.generic.typecondition.TypeConditionType;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.meta.GmTypeKind;

public interface IsTypeKind extends TypeCondition {

	EntityType<IsTypeKind> T = EntityTypes.T(IsTypeKind.class);

	TypeKind getKind();
	void setKind(TypeKind typeKind);

	@Override
	default boolean matches(GenericModelType type) {
		TypeKind kind = getKind();
		
		if (kind == null)
			return false;

		switch (kind) {
		case scalarType:
			return type.isScalar();
		case numberType:
			return type.isNumber();
		case booleanType:
			return type.getTypeCode() == TypeCode.booleanType;
		case collectionType:
			return type.isCollection();
		case dateType:
			return type.getTypeCode() == TypeCode.dateType;
		case decimalType:
			return type.getTypeCode() == TypeCode.decimalType;
		case doubleType:
			return type.getTypeCode() == TypeCode.doubleType;
		case entityType:
			return type.isEntity();
		case enumType:
			return type.isEnum();
		case floatType:
			return type.getTypeCode() == TypeCode.floatType;
		case integerType:
			return type.getTypeCode() == TypeCode.integerType;
		case linearCollectionType:
			return type.getTypeCode() == TypeCode.setType || type.getTypeCode() == TypeCode.listType;
		case listType:
			return type.getTypeCode() == TypeCode.listType;
		case longType:
			return type.getTypeCode() == TypeCode.longType;
		case mapType:
			return type.getTypeCode() == TypeCode.mapType;
		case setType:
			return type.getTypeCode() == TypeCode.setType;
		case simpleType:
			return type.isSimple();
		case stringType:
			return type.getTypeCode() == TypeCode.stringType;
		default:
			throw new IllegalArgumentException("Unsupported TypeKind constant " + kind);
		}
	}
	
	@Override
	default boolean matches(GmType type) {
		TypeKind kind = getKind();
		
		if (kind == null)
			return false;
		
		switch (kind) {
		case scalarType:
			return type.isGmScalar();
		case numberType:
			return type.isGmNumber();
		case booleanType:
			return type.typeKind() == GmTypeKind.BOOLEAN;
		case collectionType:
			return type.isGmCollection();
		case dateType:
			return type.typeKind() == GmTypeKind.DATE;
		case decimalType:
			return type.typeKind() == GmTypeKind.DECIMAL;
		case doubleType:
			return type.typeKind() == GmTypeKind.DOUBLE;
		case entityType:
			return type.isGmEntity();
		case enumType:
			return type.isGmEnum();
		case floatType:
			return type.typeKind() == GmTypeKind.FLOAT;
		case integerType:
			return type.typeKind() == GmTypeKind.INTEGER;
		case linearCollectionType:
			return type.typeKind() == GmTypeKind.SET || type.typeKind() == GmTypeKind.LIST;
		case listType:
			return type.typeKind() == GmTypeKind.LIST;
		case longType:
			return type.typeKind() == GmTypeKind.LONG;
		case mapType:
			return type.typeKind() == GmTypeKind.MAP;
		case setType:
			return type.typeKind() == GmTypeKind.SET;
		case simpleType:
			return type.isGmSimple();
		case stringType:
			return type.typeKind() == GmTypeKind.STRING;
		default:
			throw new IllegalArgumentException("Unsupported TypeKind constant " + kind);
		}
	}

	@Override
	default TypeConditionType tcType() {
		return TypeConditionType.isTypeKind;
	}

}
