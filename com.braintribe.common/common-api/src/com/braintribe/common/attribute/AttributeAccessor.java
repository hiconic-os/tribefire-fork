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

import java.util.Optional;
import java.util.function.Supplier;

import jsinterop.annotations.JsType;
import jsinterop.context.JsInteropNamespaces;

/**
 * A {@link TypeSafeAttribute} accessor that can be defined as a static field of an aspect and can be used instead of a class literal. This is
 * especially useful in tf.js.
 * 
 * Example:
 * 
 * <pre>
 * public interface MyAttribute extends TypeSafeAttribute<String> {
 * 	AttributeAccessor<MyAttribute> $ = AttributeAccessor.create(MyAttribute.class);
 * }
 * 
 * MyAttribute.$.get(typeSafeAttributes);
 * MyAttribute.$.set(evalContext, "MyValue");
 * </pre>
 * 
 * @author peter.gazdik
 */
@JsType(namespace = JsInteropNamespaces.attr)
public interface AttributeAccessor<T> {

	T get(TypeSafeAttributes attributes);

	T findOrNull(TypeSafeAttributes attributes);

	T findOrDefault(TypeSafeAttributes attributes, T defaultValue);

	T findOrSupply(TypeSafeAttributes attributes, Supplier<T> defaultValueSupplier);

	Optional<T> find(TypeSafeAttributes attributes);

	void set(TypeSafeAttribution attribution, T value);

	static <T, A extends TypeSafeAttribute<T>> AttributeAccessor<T> create(Class<A> key) {
		return new BasicAttributeAccessor<>(key);
	}

}

class BasicAttributeAccessor<T, A extends TypeSafeAttribute<T>> implements AttributeAccessor<T> {

	private final Class<A> key;

	public BasicAttributeAccessor(Class<A> key) {
		this.key = key;
	}

	@Override
	public T get(TypeSafeAttributes attributes) {
		return attributes.getAttribute(key);
	}

	@Override
	public T findOrNull(TypeSafeAttributes attributes) {
		return attributes.findOrNull(key);
	}

	@Override
	public T findOrDefault(TypeSafeAttributes attributes, T defaultValue) {
		return attributes.findOrDefault(key, defaultValue);
	}

	@Override
	public T findOrSupply(TypeSafeAttributes attributes, Supplier<T> defaultValueSupplier) {
		return attributes.findOrSupply(key, defaultValueSupplier);
	}

	@Override
	public Optional<T> find(TypeSafeAttributes attributes) {
		return attributes.findAttribute(key);
	}

	@Override
	public void set(TypeSafeAttribution attribution, T value) {
		attribution.setAttribute(key, value);
	}

}
