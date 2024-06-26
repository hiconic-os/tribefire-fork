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
package com.braintribe.gwt.browserfeatures.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.gwt.browserfeatures.client.interop.JsMap;
import com.braintribe.gwt.browserfeatures.client.interop.JsSet;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;

/**
 *
 * Uses {@link HashMap}
 * Uses {@link HashSet}
 */
public class JsCollections {

	public static <V> Map<String, V> stringMap() {
		return GWT.isScript() ? new JsStringMap<V>() : new HashMap<String, V>();
	}
	
	public static <V> Map<String, V> stringMap(JavaScriptObject backend) {
		if (GWT.isScript())
			return new JsStringMap<V>(backend);
		else {
			Map<String, V> map = new HashMap<String, V>();
			fill(backend, map);
			return map;
		}
	}
	
	private static native <V> void fill(JavaScriptObject backend, Map<String, V> map) /*-{
		var ks = Object.keys(backend);
		
		for (var i = 0; i < ks.length; i++) {
			var k = keys[i];
			var v = backend[k];
			map.@java.util.Map::put(Ljava/lang/Object;Ljava/lang/Object;)(k,v);
		}
	}-*/;

	public static <V> List<V> nativeList(JsArray<V> backend) {
		if (GWT.isScript()) {
			return new JsArrayList<V>(backend);
		}
		else {
			int length = backend.length();
			List<V> list = new ArrayList<V>(length);
			for (int i = 0; i < length; i++) {
				list.add(backend.get(i));
			}
			return list;
		}
	}

	public static native <K, V> Map<K, V> toJMap(JsMap<K, V> jsMap) /*-{
		var result = new @HashMap::new()();
		jsMap.forEach((v,k)=>result.@java.util.Map::put(Ljava/lang/Object;Ljava/lang/Object;)(k,v););
		return result;
	}-*/;  

	public static native <E> Set<E> toJSet(JsSet<E> jsSet) /*-{
		var result = new @HashSet::new()();
		jsSet.forEach((e)=>result.@java.util.Set::add(Ljava/lang/Object;)(e););
		return result;
	}-*/;  

}
