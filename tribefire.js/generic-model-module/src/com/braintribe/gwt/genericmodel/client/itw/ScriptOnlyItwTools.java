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

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import com.google.gwt.core.client.GwtScriptOnly;
import com.google.gwt.core.client.JavaScriptObject;

@GwtScriptOnly
public class ScriptOnlyItwTools {

	/**
	 * This function resembles the hidden method available only in the GWT emulation JRE and addresses it via JSNI
	 * @param <T> type of newly create class
	 * @param packageName the packageName which must end with a '.'
	 * @param compoundClassName the simple Class name (without package)
	 * @param superClass the super class of the class
	 */
	public static native <T> Class<T> createForClass(String packageName, String compoundClassName, JavaScriptObject typeId,
			Class<?> superClass)
	/*-{
		return @java.lang.Class::createForClass(Ljava/lang/String;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/Class;)(packageName, compoundClassName, typeId, superClass);
	}-*/;
	
	/**
	 * This function resembles the hidden method available only in the GWT emulation JRE and addresses it via JSNI 
	 * @param <T> type of newly create enum
	 * @param packageName the packageName which must end with a '.'
	 * @param compoundClassName the simple Class name (without package)
	 * @param superClass the super class of the class
	 */
	public static native <T extends Enum<T>> Class<T> createForEnum(String packageName, String compoundClassName, JavaScriptObject typeId,
			Class<? super T> superClass, JavaScriptObject enumConstantsFunc, JavaScriptObject enumValueOfFunc) /*-{
		return @java.lang.Class::createForEnum(Ljava/lang/String;Ljava/lang/String;Lcom/google/gwt/core/client/JavaScriptObject;Ljava/lang/Class;Lcom/google/gwt/core/client/JavaScriptObject;Lcom/google/gwt/core/client/JavaScriptObject;)
			(packageName, compoundClassName, typeId, superClass, enumConstantsFunc, enumValueOfFunc);
	}-*/;

	
	public static native Enum<?> createEnumInstance(String name, int ordinal, String nameOfGetClass, String nameOfGetDeclaringClass,
			Class<? extends Enum<?>> enumClass)	/*-{
		var enumInstance = @java.lang.Enum::new(Ljava/lang/String;I)(name, ordinal);
		enumInstance[nameOfGetClass] = function() {
			return enumClass;
		}
		enumInstance[nameOfGetDeclaringClass] = function() {
			return enumClass;
		}
		enumInstance.@Object::___clazz = enumClass;
		return enumInstance;
	}-*/;
	
	
	/**
	 * This function resembles the hidden method available only in the GWT emulation JRE and addresses it via JSNI 
	 * @param <T> type of newly create interface
	 * @param packageName the packageName which must end with a '.'
	 * @param className the simple Class name of the interface (without package)
	 */
	public static native <T> Class<T> createForInterface(String packageName, String className) /*-{
		return @java.lang.Class::createForInterface(Ljava/lang/String;Ljava/lang/String;)(packageName, className);
	}-*/;

	public static native GenericJavaScriptObject portableObjectCreate(Object prototype) /*-{
		return @com.google.gwt.lang.Runtime::portableObjCreate(Lcom/google/gwt/core/client/JavaScriptObject;)(prototype);
	}-*/;
	
	public static native void setClass(Object object, Class<?> javaClass) /*-{
		object.@Object::___clazz = javaClass;
	}-*/;
	
	public static native void setCastableTypeMap(Object object, CastableTypeMap castableTypeMap) /*-{
		object.@java.lang.Object::castableTypeMap = castableTypeMap;
	}-*/;
	
	public static native CastableTypeMap getCastableTypeMap(Object object) /*-{
		return object.@java.lang.Object::castableTypeMap;
	}-*/;

	public static native void setPrototype(Object object, GenericJavaScriptObject prototype) /*-{
		object.prototype = prototype;
	}-*/;
	
	public static native GenericJavaScriptObject getPrototype(Object object) /*-{
		return object.prototype;
	}-*/;

	public static native Object eval(String source) /*-{
		return eval(source);
	}-*/;

	public static native JavaScriptObject createProvider(Object o)/*-{
		return function(){return o;};
	}-*/;
	
	public static native void setProperty(Object owner, String propertyName, Object value)/*-{
		owner[propertyName] = value;
	}-*/;

	
	public static native <T> T getObjectProperty(Object object, String propertyName) /*-{
		return object[propertyName];
	}-*/;

	public static native void forEachPropertyName(Object owner, Consumer<String> consumer)
	/*-{
		for (var propertyName in owner) {
			consumer.@Consumer::accept(Ljava/lang/Object;)(propertyName);
		}
	}-*/;

	public static native void forEachEntry(Object owner, BiConsumer<String, Object> consumer)
	/*-{
		for (var propertyName in owner) {
			consumer.@BiConsumer::accept(Ljava/lang/Object;Ljava/lang/Object;)(propertyName, owner[propertyName]);
		}
	}-*/;

}
