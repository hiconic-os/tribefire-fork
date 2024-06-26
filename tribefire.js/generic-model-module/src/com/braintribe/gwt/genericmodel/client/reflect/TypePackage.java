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
package com.braintribe.gwt.genericmodel.client.reflect;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Map;

import com.braintribe.model.generic.reflection.GenericModelType;
import com.google.gwt.core.client.JavaScriptObject;

import jsinterop.context.JsKeywords;

@SuppressWarnings("unusable-by-js")
public class TypePackage{
	
	public static Map<String, JavaScriptObject> packagesByQualfiedName = newMap();
	
	// WHY IS THIS HERE???
	public static native TypePackage instance() /*-{
		return this;
	}-*/;
	
	public static native JavaScriptObject getRoot() /*-{
		var root = $wnd.$T;
		if(!root){
			root = {};
			$wnd.$T = root;
		}			
		return root;
	}-*/;

	public static final void register(GenericModelType type, Object jsObjectToRegister) {
		String typesig = type.getTypeSignature();
		
		int index = typesig.lastIndexOf('.');
		
		String simpleName = null;
		String packagePath = null;
		
		if (index != -1) {
			simpleName = typesig.substring(index + 1);
			packagePath = typesig.substring(0, index);
		}
		else {
			simpleName = typesig;
		}
		
		JavaScriptObject typePackage = acquireJsObjectForPackagePath(packagePath);
		
		TypePackage.set(typePackage, simpleName, jsObjectToRegister);
		
	}
	
	public static JavaScriptObject acquireJsObjectForPackagePath(String packagePath) {
		JavaScriptObject typePackage = packagePath == null ? getRoot() : packagesByQualfiedName.get(packagePath);

		if (typePackage == null) {
			int index = packagePath.lastIndexOf('.');
			String packageName = packagePath.substring(index + 1);

			String parentTypePackagePath = index != -1 ? packagePath.substring(0, index) : null;

			JavaScriptObject parentTypePackage = acquireJsObjectForPackagePath(parentTypePackagePath);

			typePackage = JavaScriptObject.createObject();
			TypePackage.set(parentTypePackage, JsKeywords.javaIdentifierToJs(packageName), typePackage);

			packagesByQualfiedName.put(packagePath, typePackage);
		}

		return typePackage;
	}

	public static final native JavaScriptObject getPackage(JavaScriptObject parent, String name) /*-{
		return parent[name];
	}-*/;
	
	public static final native void set(JavaScriptObject parent, String name, Object value) /*-{
		parent[name] = value;
	}-*/;  
}
