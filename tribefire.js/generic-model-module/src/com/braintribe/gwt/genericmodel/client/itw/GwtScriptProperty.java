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
package com.braintribe.gwt.genericmodel.client.itw;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.AbstractProperty;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * @author peter.gazdik
 */
public abstract class GwtScriptProperty extends AbstractProperty {

	public GwtScriptProperty(String propertyName, boolean nullable, boolean confidential) {
		super(propertyName, nullable, confidential);
	}

	private PropertyBinding propertyBinding;

	public PropertyBinding getPropertyBinding() {
		return propertyBinding;
	}

	public void setPropertyBinding(PropertyBinding propertyBinding) {
		this.propertyBinding = propertyBinding;
	}

	public String getFieldName() {
		return ObfuscatedIdentifierSequence.specialChar + getName();
	}

	@SuppressWarnings("unusable-by-js")
	@Override
	public <T> T getDirectUnsafe(GenericEntity entity) {
		throw new UnsupportedOperationException("Seems method 'getDirectUnsafe' was not implemented in runtime! Property: "
				+ getDeclaringType().getTypeSignature() + "." + getName());
	}

	@SuppressWarnings("unusable-by-js")
	@Override
	public void setDirectUnsafe(GenericEntity entity, Object value) {
		throw new UnsupportedOperationException("Seems method 'setDirectUnsafe' was not implemented in runtime! Property: "
				+ getDeclaringType().getTypeSignature() + "." + getName());
	}

	public static native JavaScriptObject unboxJavaNumberToJsPrimitive(Number i)/*-{
		return i != null ? i.@Number::doubleValue()() : null;
	}-*/;

	public static Integer boxJsNumberToJavaInteger(Double n) {
		return n != null? n.intValue(): null;
	}
	
	public static Float boxJsNumberToJavaFloat(Double n) {
		return n != null? n.floatValue(): null;
	}

	public static native JavaScriptObject exceptionIfNumber(JavaScriptObject  o, String pName) /*-{
		if(typeof(o)=="number")
			throw Exception("Cannot assign native JS number '"+o+"' to property: " + pName);
		return o;
	}-*/;

}
