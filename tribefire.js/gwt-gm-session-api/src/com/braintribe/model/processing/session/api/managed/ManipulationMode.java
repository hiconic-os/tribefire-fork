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
package com.braintribe.model.processing.session.api.managed;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.manipulation.EntityProperty;
import com.braintribe.model.generic.manipulation.LocalEntityProperty;
import com.braintribe.model.generic.value.EntityReference;

import jsinterop.annotations.JsType;

/**
 * Represents type of a manipulations in the manipulation stack.
 * 
 * @see ManipulationApplicationContext#getMode()
 */
@JsType(namespace=GmCoreApiInteropNamespaces.session)
@SuppressWarnings("unusable-by-js")
public enum ManipulationMode {

	/**
	 * Manipulations that use {@link LocalEntityProperty} as it's owner and values for properties are directly GM entities.
	 */
	LOCAL,

	/**
	 * Manipulations that use {@link EntityProperty} as owner and values for properties are {@link EntityReference}s.
	 */
	REMOTE
}
