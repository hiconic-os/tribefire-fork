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

import com.braintribe.model.generic.reflection.GmtsEnhancedEntityStub;
import com.braintribe.model.generic.reflection.Property;
import com.braintribe.model.generic.reflection.PropertyAccessInterceptor;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.UnsafeNativeLong;

/**
 * Documented to force an import, thus using short name in JS code 
 * 
 * @see GmtsEnhancedEntityStub
 * @see PropertyAccessInterceptor
 * 
 */
public class GenericAccessorMethods {
	
	public native static JavaScriptObject buildGenericGetter(Property property)/*-{
		return function(){
			return this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::getProperty(*)(property, this, false);
		}
	}-*/;
	
	public native static JavaScriptObject buildGenericSetter(Property property)/*-{
		return function(v){
			this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::setProperty(*)(property, this, v, false);
		}
	}-*/;
	
	public native static JavaScriptObject buildUnboxingGetter(Property property)/*-{
		return function(){
		 	var boxer = this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::getProperty(*)(property, this, false);
			if(boxer != null)
				return boxer.valueOf();
			else
				return null;
		}
	}-*/;
	
	public native static JavaScriptObject buildIntBoxingSetter(Property property)/*-{
		return function(v){
			v = @java.lang.Integer::valueOf(I)(v);
			this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::setProperty(*)(property, this, v, false);
		}
	}-*/;
	
	public native static JavaScriptObject buildNullableIntBoxingSetter(Property property)/*-{
		return function(v){
			v = v == null ? null : @java.lang.Integer::valueOf(I)(v);
			this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::setProperty(*)(property, this, v, false);
		}
	}-*/;

	public native static JavaScriptObject buildFloatBoxingSetter(Property property)/*-{
		return function(v){
			v = @java.lang.Float::valueOf(F)(v);
			this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::setProperty(*)(property, this, v, false);
		}
	}-*/;

	public native static JavaScriptObject buildNullableFloatBoxingSetter(Property property)/*-{
		return function(v){
			v = v == null ? null : @java.lang.Float::valueOf(F)(v);
			this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::setProperty(*)(property, this, v, false);
		}
	}-*/;
	
	@SuppressWarnings("unusable-by-js")
	@UnsafeNativeLong
	public native static JavaScriptObject buildLongUnboxingGetter(Property property)/*-{
		return function(){
		 	var boxer = this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::getProperty(*)(property, this, false);
			if(boxer != null)
				return boxer.@Long::longValue()();
			else
				return null;
		}
	}-*/;
	
	@SuppressWarnings("unusable-by-js")
	@UnsafeNativeLong
	public native static JavaScriptObject buildLongBoxingSetter(Property property)/*-{
		return function(v){
			v = @java.lang.Long::valueOf(J)(v);
			this.@GmtsEnhancedEntityStub::pai.@PropertyAccessInterceptor::setProperty(*)(property, this, v, false);
		}
	}-*/;
}
