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
package com.braintribe.model.processing.core.expert.api;

import java.util.Map;
import java.util.stream.Stream;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.core.expert.impl.PolymorphicDenotationMap;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Map which maps {@link EntityType}s to values. The way how the value is resolved depends on the implementation.
 * <p>
 * The basic use-case is a map where values are inherited alongside the type hierarchy - i.e. if no value is associated with given type, a value
 * associated with the most specific super-type is returned. See {@link PolymorphicDenotationMap}.
 * <p>
 * For use-cases where not just one value is returned, but all values associate with given type and its super-types, see {@link DenotationMultiMap}.
 * 
 * @see PolymorphicDenotationMap
 * @see DenotationMultiMap
 * 
 * @author peter.gazdik
 */
@JsType(namespace = GmCoreApiInteropNamespaces.util)
@SuppressWarnings("unusable-by-js")
public interface DenotationMap<B extends GenericEntity, V> {

	/** Returns the value associated with given type. or throws an exception if no such value is found. */
	@JsMethod(name = "getByType")
	<T extends V> T get(EntityType<? extends B> denotationType);

	/** Returns the value associated with given type. or <tt>null</tt> if no such value is found. */
	@JsMethod(name = "findByType")
	<T extends V> T find(EntityType<? extends B> denotationType);

	/**
	 * Returns the value associated with given entity's type. or throws an exception if no such value is found.
	 * <p>
	 * Equivalent to {@code get(denotation.entityType())}
	 */
	<T extends V> T get(B denotation);

	/**
	 * Returns the value associated with given entity's type. or <tt>null</tt> if no such value is found.
	 * <p>
	 * Equivalent to {@code find(denotation.entityType())}
	 */
	<T extends V> T find(B denotation);

	Stream<Map.Entry<EntityType<? extends B>, V>> entryStream();

	Stream<V> expertStream();

	default boolean isEmpty() {
		return configurationSize() == 0;
	}

	default int configurationSize() {
		throw new UnsupportedOperationException("Method 'DenotationMap.configurationSize' is not supported!");
	}

}
