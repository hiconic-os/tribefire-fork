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
package com.braintribe.model.generic.reflection;

import java.util.Date;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;

import jsinterop.annotations.JsType;

@JsType(namespace = GmCoreApiInteropNamespaces.reflection)
public interface ScalarType extends GenericModelType {

	/**
	 * Converts a given instance to a String. Unlike the {@link #instanceToGmString(Object)} method, this String doesn't
	 * <p>
	 * Assumes a compatible value is being passed to the actual {@link ScalarType} (so for example {@link DateType}
	 * would receive an instance of {@link Date}). If this is not the case, the behavior is undefined.
	 */
	String instanceToString(Object value);

	/**
	 * Inverse to {@link #instanceToString(Object)}.
	 * <p>
	 * Assumes the type and encoded value match and thus the value can be parsed. Otherwise, the behavior is undefined.
	 */
	<T> T instanceFromString(String encodedValue);

	/**
	 * Converts given scalar value to a string which also carries the type information. To parse the value back, when
	 * the type is not known, use GmValueCodec from gm-core. Otherwise use the {@link #instanceFromGmString(String)} for
	 * faster decoding.
	 * <p>
	 * Assumes a compatible value is being passed to the actual {@link ScalarType} (so for example {@link DateType}
	 * would receive an instance of {@link Date}). If this is not the case, the behavior is undefined.
	 * <p>
	 * NOTE that this value should (for now) be used as transient only - the actual format of certain types might
	 * change, so it is persisted, you might not be able to parse it later.
	 */
	String instanceToGmString(Object value);

	/**
	 * Inverse to {@link #instanceToGmString(Object)}.
	 * <p>
	 * Assumes the type and encoded value match and thus the value can be parsed. Otherwise, the behavior is undefined.
	 * <p>
	 * NOTE that this value should (for now) be used as transient only - the actual format of certain types might
	 * change, so it is persisted, you might not be able to parse it later.
	 */
	Object instanceFromGmString(String encodedValue);
}
