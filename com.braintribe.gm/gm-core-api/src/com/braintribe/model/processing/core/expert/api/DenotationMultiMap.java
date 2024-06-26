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

import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.processing.core.expert.impl.PolymorphicDenotationMap;
import com.braintribe.model.processing.core.expert.impl.PolymorphicDenotationMultiMap;

import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Multi-map which maps {@link EntityType}s to multiple values. The way how the value is resolved depends on the implementation.
 * <p>
 * The get/find methods inherited from {@link DenotationMap} return the first element of the list returned from {@link #findAll(EntityType)} (or
 * <tt>null</tt> / throw exception if the list is empty).
 * <p>
 * The basic use-case is a multi-map where values are inherited alongside the type hierarchy - i.e. when resolving values associated with given type,
 * the result also includes all values associated with all the super-types.
 * <p>
 * For use-cases where just the value associated with the most specific super-type is returned see {@link PolymorphicDenotationMap}.
 * 
 * @see DenotationMap
 * @see PolymorphicDenotationMultiMap
 * 
 * @author peter.gazdik
 */
@JsType(namespace = GmCoreApiInteropNamespaces.util)
@SuppressWarnings("unusable-by-js")
public interface DenotationMultiMap<B extends GenericEntity, V> extends DenotationMap<B, V> {

	@JsMethod(name = "findAllByType")
	<T extends V> List<T> findAll(EntityType<? extends B> denotationType);

	<T extends V> List<T> findAll(B denotation);
}
