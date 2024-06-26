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
package com.braintribe.gwt.genericmodel.client.itw.gb;

import com.braintribe.common.lcd.Pair;
import com.braintribe.model.generic.reflection.GmtsEnhancedEntityStub;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.PropertyAccessInterceptor;
import com.google.gwt.core.client.JavaScriptObject;

/**
 * {@link GmtsEnhancedEntityStub}
 * {@link LongFacade}
 * {@link DecimalFacade}
 * {@link PropertyAccessInterceptor}
 * @author dirk.scheffler
 *
 */
public abstract class JsValueAdaption {

	public static Pair<JavaScriptObject,JavaScriptObject> getAdapterFunctions(Property property) {
		switch (property.getType().getTypeCode()) {
		// base type
		case objectType:
			break;

		// simple types
		case booleanType:
			return property.isNullable()?
					new Pair<>(createConversionGetter(property, getBooleanToPrimitive()), createConversionSetter(property, getPrimitiveToBooolean())):
					null;
		case floatType:
			return property.isNullable()?
					new Pair<>(createConversionGetter(property, getFloatToPrimitive()), createConversionSetter(property, getPrimitiveToFloat())):
					null;
		case doubleType:
			return property.isNullable()?
					new Pair<>(createConversionGetter(property, getDoubleToPrimitive()), createConversionSetter(property, getPrimitiveToDouble())):
					null;
		case integerType:
			return property.isNullable()?
					new Pair<>(createConversionGetter(property, getIntegerToPrimitive()), createConversionSetter(property, getPrimitiveToInteger())):
					null;
			
		case dateType:
			return new Pair<>(createConversionGetter(property, getToJsDate()), createConversionSetter(property, getToJavaDate()));
		case decimalType:
			return new Pair<>(createConversionGetter(property, getDecimalFacadeConstructor()), createUnboxingSetter(property));
		case longType:
			return new Pair<>(createConversionGetter(property, getLongFacadeConstructor()), createUnboxingSetter(property));
		
		// collection types
		case listType:
			return new Pair<>(createConversionGetter(property, getListFacadeConstructor()), createUnboxingSetter(property));
		case mapType:
			return new Pair<>(createConversionGetter(property, getMapFacadeConstructor()), createUnboxingSetter(property));
		case setType:
			return new Pair<>(createConversionGetter(property, getSetFacadeConstructor()), createUnboxingSetter(property));
			
		default:
			return null;
		
		}
		// TODO check switch block and return statement
		return null;
	}
	
	private static native JavaScriptObject getSetFacadeConstructor() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getMapFacadeConstructor() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getListFacadeConstructor() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getIntegerToPrimitive() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getDoubleToPrimitive() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getPrimitiveToDouble() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getPrimitiveToInteger() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getPrimitiveToFloat() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getFloatToPrimitive() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getBooleanToPrimitive() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getPrimitiveToBooolean() /*-{
		return null;
	}-*/;

	private static native JavaScriptObject getToJsDate() /*-{
		return function(date) {
			return new Date(date.@java.util.Date::getTime()());
		};
	}-*/;
	
	private static native JavaScriptObject getToJavaDate() /*-{
		return function(date) {
			return @Date::new(J)(date.getTime());
		};
	}-*/;
	
	
	private static native JavaScriptObject getLongFacadeConstructor() /*-{
		return @LongFacade::new(Ljava/lang/Long;);
	}-*/;
	
	private static native JavaScriptObject getDecimalFacadeConstructor() /*-{
		return @DecimalFacade::new(Ljava/lang/Long;);
	}-*/;

	private static native JavaScriptObject createConversionGetter(Property property, JavaScriptObject conversionFunction) /*-{
	    return function() {
			var v = this.@GmtsEnhancedEntityStub::pai.
				@PropertyAccessInterceptor::getProperty(Lcom/braintribe/model/generic/reflection/Property;Lcom/braintribe/model/generic/GenericEntity;)
					(property, this);
			return v == null? null: conversionFunction(v);
		};	
	}-*/;
	
	private static native JavaScriptObject createConversionSetter(Property property, JavaScriptObject conversionFunction) /*-{
	    return function(value) {
	    	value = conversionFunction(value);
			return this.@GmtsEnhancedEntityStub::pai.
				@PropertyAccessInterceptor::setProperty(Lcom/braintribe/model/generic/reflection/Property;Lcom/braintribe/model/generic/GenericEntity;Ljava/lang/Object;)
					(property, this, value);
		};	
	}-*/;
	
	/**
	 * {@link JsFacade}
	 */
	private static native JavaScriptObject createUnboxingSetter(Property property) /*-{
	    return function(value) {
	    	value = value.@JsBox::unbox()();
			return this.@GmtsEnhancedEntityStub::pai.
				@PropertyAccessInterceptor::setProperty(Lcom/braintribe/model/generic/reflection/Property;Lcom/braintribe/model/generic/GenericEntity;Ljava/lang/Object;)
					(property, this, value);
		};	
	}-*/;
	
}
