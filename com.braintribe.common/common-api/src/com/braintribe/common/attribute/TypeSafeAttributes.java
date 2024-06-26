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
package com.braintribe.common.attribute;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.function.Supplier;

import jsinterop.annotations.JsType;
import jsinterop.context.JsInteropNamespaces;

/**
 * TypeSafeAttributes
 * 
 * @author Dirk Scheffler
 *
 */
@JsType(namespace = JsInteropNamespaces.attr)
public interface TypeSafeAttributes {

	default <A extends TypeSafeAttribute<V>, V> V getAttribute(Class<A> attribute) {
		return findAttribute(attribute).orElseThrow(() -> new NoSuchElementException("No value found for attribute: " + attribute.getName()));
	}

	default <A extends TypeSafeAttribute<V>, V> V findOrNull(Class<A> attribute) {
		return findOrDefault(attribute, null);
	}

	default <A extends TypeSafeAttribute<V>, V> V findOrDefault(Class<A> attribute, V defaultValue) {
		return findAttribute(attribute).orElse(defaultValue);
	}

	default <A extends TypeSafeAttribute<V>, V> V findOrSupply(Class<A> attribute, Supplier<V> defaultValueSupplier) {
		return findAttribute(attribute).orElseGet(defaultValueSupplier);
	}

	<A extends TypeSafeAttribute<V>, V> Optional<V> findAttribute(Class<A> attribute);
}
