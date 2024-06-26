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
package com.braintribe.codec.marshaller.html.model;

import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.reflection.TypeCode;

/**
 * @author Christina Wilpernig
 * @author Neidhart.Orlich
 */

public interface HtmlValue extends GenericEntity {

	final EntityType<HtmlValue> T = EntityTypes.T(HtmlValue.class);

	public Object getValue();
	public void setValue(Object value);

	public String getTypeSignature();
	public void setTypeSignature(String typeSignature);
	
	public String getExpectedTypeSignature();
	public void setExpectedTypeSignature(String typeSignature);
	
	// TODO not needed yet
	default TypeCode getObjectTypeCode() {
		return GMF.getTypeReflection().getType(getValue()).getTypeCode();
	}
	
	default RenderType renderType() {
		return RenderType.simple;
	}
	
	default boolean linkable() {
		return false;
	}
	
	default String getRenderedString() {
		Object value = getValue();
		TypeCode objectTypeCode = getObjectTypeCode();
		
		switch(objectTypeCode) {
			case stringType:
				return value != null ? value.toString() : "";
			case objectType:
				return "";
			case booleanType:
			case dateType:
			case floatType:
			case integerType:
			case longType:
			case decimalType:
			case doubleType:
			case enumType:
				return value.toString();
			default:
				throw new IllegalStateException("No rendered string available for type " + objectTypeCode);
		}
	}
//	
//	default String simpleTypeName() {
//		int lastDotIndex = getTypeSignature().lastIndexOf('.');
//		return getTypeSignature().substring(lastDotIndex + 1);
//	}
	
	default String typePackageName() {
		int lastDotIndex = getTypeSignature().lastIndexOf('.');
		return getTypeSignature().substring(0, lastDotIndex);
	}

}
