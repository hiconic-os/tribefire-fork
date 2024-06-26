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

import java.util.AbstractSet;
import java.util.Iterator;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArray;

/**
 * 
 */
public class JsPropertyNameSet extends AbstractSet<String> {

	private final JavaScriptObject jso;

	public JsPropertyNameSet(JavaScriptObject jso) {
		this.jso = jso;
	}

	@Override
	public native boolean contains(Object o)
	/*-{
		//return this.@JsPropertyNameSet::jso.hasOwnProperty(o.@Object::toString());
		return this.@JsPropertyNameSet::jso.hasOwnProperty(o.toString());
	}-*/;

	@Override
	public Iterator<String> iterator() {

		return new Iterator<String>() {
			int nextIndex = 0;
			JsArray<JavaScriptObject> array = getObjectKeysArray();

			@Override
			public boolean hasNext() {
				return nextIndex < array.length();
			}

			@Override
			public String next() {
				return array.get(nextIndex++).toString();
			}

			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};

	}

	@Override
	public native int size()
	/*-{
		return Object.keys(this.@JsPropertyNameSet::jso).length;
	}-*/;

	private native JsArray<JavaScriptObject> getObjectKeysArray()
	/*-{
		return Object.keys(this.@JsPropertyNameSet::jso);
	}-*/;

}
