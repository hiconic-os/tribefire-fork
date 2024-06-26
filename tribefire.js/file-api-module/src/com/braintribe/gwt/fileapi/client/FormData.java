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
package com.braintribe.gwt.fileapi.client;

import com.google.gwt.dom.client.FormElement;

import jsinterop.annotations.JsOverlay;
import jsinterop.annotations.JsPackage;
import jsinterop.annotations.JsType;

@JsType(isNative = true, namespace = JsPackage.GLOBAL)
public class FormData {
	protected FormData() {

	}

	@JsOverlay
	public static FormData create() {
		return FileApiNativeHelper.formDataCreate();
	}

	@JsOverlay
	public static FormData create(FormElement form) {
		return FileApiNativeHelper.formDataCreate(form);
	}

	public final native void append(String name, Object stringOrBlob, String filename);

	@JsOverlay
	public final void append(String name, Blob blob, String fileName) {
		append(name, (Object) blob, fileName);
	}

	@JsOverlay
	public final void append(String name, Blob blob) {
		String fileName = blob instanceof File ? ((File) blob).getName() : null;
		append(name, (Object) blob, fileName);
	}

	@JsOverlay
	public final void append(String name, String value) {
		append(name, value, null);
	}

}
