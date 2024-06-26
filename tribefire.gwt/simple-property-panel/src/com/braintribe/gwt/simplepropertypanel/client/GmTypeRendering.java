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
package com.braintribe.gwt.simplepropertypanel.client;

import com.braintribe.model.meta.GmBaseType;
import com.braintribe.model.meta.GmCustomType;
import com.braintribe.model.meta.GmLinearCollectionType;
import com.braintribe.model.meta.GmMapType;
import com.braintribe.model.meta.GmSimpleType;
import com.braintribe.model.meta.GmType;
import com.braintribe.model.meta.GmTypeKind;

public class GmTypeRendering {
	
	public static String getPropertyType(GmType propertyType, boolean key){
		if (propertyType == null)
			return "???";
		
		if (propertyType instanceof GmMapType) {
			if (key)
				return getPropertyType(((GmMapType) propertyType).getKeyType(), false);
			
			return getPropertyType(((GmMapType) propertyType).getValueType(), false);
		}
		
		if (propertyType instanceof GmLinearCollectionType)
			return getPropertyType(((GmLinearCollectionType) propertyType).getElementType(), false);
		
		if (propertyType instanceof GmSimpleType)
			return propertyType.typeKind().name().toLowerCase();
		
		if (propertyType instanceof GmBaseType)
			return "object";
		
		if (propertyType instanceof GmCustomType) {
			GmCustomType type = (GmCustomType)propertyType;
			String typeSignature = type.getTypeSignature();
			return typeSignature.substring(typeSignature.lastIndexOf(".") + 1, typeSignature.length());
		}
		
		return "???";
	}
	
	public static String getTypeName(String typeSignature) {
		return typeSignature.substring(typeSignature.lastIndexOf(".")+1, typeSignature.length());
	}
	
	public static String getTypePackage(String typeSignature) {
		return typeSignature.substring(0, typeSignature.lastIndexOf("."));
	}
	
	public static String getTypeGlobalId(GmTypeKind collectionTypeKind, GmTypeKind keyTypeKind, GmTypeKind valueTypeKind) {
		String key = keyTypeKind.name();
		if (key.equalsIgnoreCase("base"))
			key = "object";
		
		String value = "";
		if (valueTypeKind != null) {
			value = keyTypeKind.name();
			if (value.equalsIgnoreCase("base"))
				value = "object";
		}
		
		if (collectionTypeKind == GmTypeKind.MAP)
			return "type:" + collectionTypeKind.name().toLowerCase() + "<" + key.toLowerCase() + "," + value.toLowerCase()	 + ">";
		
		if (collectionTypeKind == GmTypeKind.LIST || collectionTypeKind == GmTypeKind.SET)
			return "type:" + collectionTypeKind.name().toLowerCase() + "<" + key.toLowerCase() + ">";
		
		return "type:" + key.toLowerCase();
	}

	public static String getCardinality(GmType type) {
		/*if(type instanceof GmSimpleType || type instanceof GmBaseType)
			return "SINGLE";
		else */
		if (type instanceof GmLinearCollectionType || type instanceof GmMapType)
			return type.typeKind().name();
			
		return "SINGLE";
//		return null;
	}

}
