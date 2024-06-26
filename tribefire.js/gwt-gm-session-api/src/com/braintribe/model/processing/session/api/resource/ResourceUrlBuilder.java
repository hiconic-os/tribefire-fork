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

import jsinterop.annotations.JsType;

@JsType(namespace=GmCoreApiInteropNamespaces.resources)
@SuppressWarnings("unusable-by-js")
public interface ResourceUrlBuilder {

	/**
	 * <p>
	 * Determines the {@code download} parameter value.
	 */
	ResourceUrlBuilder download(boolean download);

	/**
	 * <p>
	 * Determines the {@code fileName} parameter value which can be used in both download and upload URLs.
	 */
	ResourceUrlBuilder fileName(String fileName);

	/**
	 * <p>
	 * Determines the {@code accessId} parameter value.
	 */
	ResourceUrlBuilder accessId(String accessId);

	/**
	 * <p>
	 * Determines the {@code sessionId} parameter value.
	 */
	ResourceUrlBuilder sessionId(String sessionId);

	/**
	 * <p>
	 * Determines the {@code entityType} of the ResourceSource.
	 */
	ResourceUrlBuilder sourceType(String sourceTypeSignature);

	/**
	 * <p>
	 * Determines the {@code useCase} parameter value.
	 */
	ResourceUrlBuilder useCase(String useCase);

	/**
	 * <p>
	 * Determines the {@code mimeType} parameter value.
	 */
	ResourceUrlBuilder mimeType(String mimeType);

	/**
	 * <p>
	 * Determines the {@code md5} parameter value.
	 */
	ResourceUrlBuilder md5(String md5);

	/**
	 * <p>
	 * Determines the {@code tags} parameter value.
	 */
	ResourceUrlBuilder tags(String tags);
	
	/**
	 * <p>
	 * Determines the {@code specification} parameter value.
	 */
	ResourceUrlBuilder specification(String specification);

	/**
	 * <p>
	 * Determines the base URL.
	 */
	ResourceUrlBuilder base(String baseUrl);

	String asString();

}
