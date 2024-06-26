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
package com.braintribe.model.io.metamodel.render.context;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Map;

import com.braintribe.model.generic.reflection.SimpleType;
import com.braintribe.model.generic.reflection.SimpleTypes;
import com.braintribe.model.meta.GmBaseType;
import com.braintribe.model.meta.GmCollectionType;
import com.braintribe.model.meta.GmLinearCollectionType;
import com.braintribe.model.meta.GmListType;
import com.braintribe.model.meta.GmMapType;
import com.braintribe.model.meta.GmSimpleType;
import com.braintribe.model.meta.GmType;

/**
 * 
 */
public class TypeSignatureResolver {

	private static final Map<String, String> simpleNameToFullJavaName = newMap();
	static {
		for (SimpleType st : SimpleTypes.TYPES_SIMPLE) {
			simpleNameToFullJavaName.put(st.getTypeName(), st.getJavaType().getName());
		}
	}

	private final Map<GmType, JavaType> cache = newMap();

	public JavaType resolveJavaType(GmType gmType) {
		JavaType result = cache.get(gmType);
		if (result == null)
			cache.put(gmType, result = computeJavaTypes(gmType));

		return result;
	}

	private JavaType computeJavaTypes(GmType gmType) {
		if (gmType instanceof GmBaseType) {
			return new JavaType(Object.class.getName());

		} else if (gmType instanceof GmSimpleType) {
			return new JavaType(getSimpleTypeSignature((GmSimpleType) gmType));

		} else if (gmType instanceof GmCollectionType) {
			return getCollectionType((GmCollectionType) gmType);

		} else {
			return new JavaType(gmType.getTypeSignature());
		}
	}

	private String getSimpleTypeSignature(GmSimpleType gmSimpleType) {
		String typeSignature = gmSimpleType.getTypeSignature();
		if (simpleNameToFullJavaName.containsKey(typeSignature)) {
			return simpleNameToFullJavaName.get(typeSignature);

		} else {
			throw new IllegalArgumentException("Unsupported simple type: " + typeSignature);
		}
	}

	private JavaType getCollectionType(GmCollectionType gmType) {
		if (gmType instanceof GmLinearCollectionType) {
			String raw = gmType instanceof GmListType ? "List" : "Set";
			JavaType elementType = resolveJavaType(((GmLinearCollectionType) gmType).getElementType());

			return new JavaType("java.util." + raw, elementType.rawType);

		} else if (gmType instanceof GmMapType) {
			GmMapType mapType = (GmMapType) gmType;
			JavaType keyType = resolveJavaType(mapType.getKeyType());
			JavaType valueType = resolveJavaType(mapType.getKeyType());

			return new JavaType("java.util.Map", keyType.rawType, valueType.rawType);

		} else {
			throw new IllegalArgumentException("Unsupported collection type: " + gmType.getClass().getName());
		}
	}

}
