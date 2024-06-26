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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.GmCoreApiInteropNamespaces;

import jsinterop.annotations.JsType;

/**
 * @see Property
 * @see TransientProperty
 */
@JsType(namespace=GmCoreApiInteropNamespaces.reflection)
@SuppressWarnings("unusable-by-js")
public interface Attribute {

	String getName();

	Class<?> getJavaType();

	/**
	 * Returns the {@link EntityType} which declared this property. Note that this does not mean, that the property is not inherited by the returned
	 * type, it may have just re-declared it with a different initializer.
	 */
	EntityType<?> getDeclaringType();

	/**
	 * Returns the first type in the hierarchy (when examined with depth-first search) where this property was declared. This means this property is
	 * not inherited by the returned type.
	 */
	EntityType<?> getFirstDeclaringType();

	/** This is false only for properties defined as primitive java types. */
	boolean isNullable();

	/** Accesses the attribute in a way equivalent to invoking the corresponding getter or setter. */
	<T> T get(GenericEntity entity);
	/** @see #get */
	void set(GenericEntity entity, Object value);

	/**
	 * Accesses the property directly, bypassing any possible configured {@link PropertyAccessInterceptor}s as well as type checks. The type check is
	 * expected to happen when the result is returned, if at all.
	 */
	<T> T getDirectUnsafe(GenericEntity entity);
	/** Sets the property directly, bypassing any possible configured {@link PropertyAccessInterceptor}s as well as type checks. */
	void setDirectUnsafe(GenericEntity entity, Object value);

	/**
	 * Same as {@link #getDirectUnsafe(GenericEntity)}. See {@link #setDirect(GenericEntity, Object)}.
	 */
	<T> T getDirect(GenericEntity entity);
	/**
	 * Sets the property directly, bypassing any possible configured {@link PropertyAccessInterceptor}s, but does type checking of the value against
	 * the attribute's type.
	 */
	Object setDirect(GenericEntity entity, Object value);

}
