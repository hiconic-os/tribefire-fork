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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import com.google.gwt.core.client.JavaScriptObject;

import jsinterop.context.JsKeywords;

public class GenericJavaScriptObject extends JavaScriptObject {
	protected GenericJavaScriptObject() {
		
	}
	
	public final void forEachPropertyName(Consumer<String> consumer) {
		ScriptOnlyItwTools.forEachPropertyName(this, consumer);
	}

	public final native boolean hasProperty(String propertyName) /*-{
		return propertyName in this;
	}-*/;
	
	public final native void setProperty(String propertyName, int value) /*-{
		this[propertyName] = value;
	}-*/;
	
	public final native void setProperty(String propertyName, JavaScriptObject value) /*-{
		this[propertyName] = value;
	}-*/;
	
	public final native void setProperty(String propertyName, Object value) /*-{
		this[propertyName] = value;
	}-*/;
	
	public final native int getIntProperty(String propertyName) /*-{
		return this[propertyName];
	}-*/;
	
	public final native <T> T getObjectProperty(String propertyName) /*-{
		return this[propertyName];
	}-*/;
	
	/**
	 * Tries to delete a property of this object.
	 * 
	 * @return <tt>true</tt> iff given property existed and was thus deleted.
	 */
	public final native boolean deleteProperty(String propertyName) /*-{
		return delete this[propertyName];
	}-*/;
	
	public final native boolean hasOwnProperty(String propertyName) /*-{
		return this.hasOwnProperty(propertyName);
	}-*/; 

	/**
	 * @return <tt>true</tt> iff there is no property defined for this object
	 */
	public final boolean isEmpty(){
		return size() == 0;
	}
	
	/**
	 * @return number of properties defined for this object
	 */
	public final native int size() /*-{
		return Object.keys(this).length;
	}-*/; 
	
	public final Set<String> keySet()  {
		return new JsPropertyNameSet(this);
	}

	public final void putAll(Map<String, Object> properties) {
		for (Entry<String, Object> entry: properties.entrySet()) {
			setProperty(entry.getKey(), entry.getValue());
		}
	}

	public final void defineVirtualProperty(String propertyName, JavaScriptObject getter, JavaScriptObject setter) {
		defineActualVirtualProperty(JsKeywords.javaIdentifierToJs(propertyName), getter, setter);
	}

	private final native void defineActualVirtualProperty(String propertyName, JavaScriptObject getter, JavaScriptObject setter) /*-{
		Object.defineProperty(this, propertyName, {
			get: getter,
			set: setter,
			configurable: true
		})
	}-*/;

}
