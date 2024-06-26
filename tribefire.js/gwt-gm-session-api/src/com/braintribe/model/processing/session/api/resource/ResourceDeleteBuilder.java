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
package com.braintribe.model.processing.session.api.resource;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.resource.Resource;

import jsinterop.annotations.JsType;

@JsType(namespace=GmCoreApiInteropNamespaces.resources)
@SuppressWarnings("unusable-by-js")
public interface ResourceDeleteBuilder {

	/**
	 * <p>
	 * Sets the use case under which the {@link Resource} binary data is to be deleted.
	 * 
	 * @param useCase
	 *            the use case under which the {@link Resource} binary data is to be deleted.
	 */
	ResourceDeleteBuilder useCase(String useCase);

	/**
	 * <p>
	 * Deletes the binary data backed by the provided {@link Resource}.
	 * 
	 * @throws java.io.UncheckedIOException
	 *             If the IO operation fails.
	 */
	void delete();

}
