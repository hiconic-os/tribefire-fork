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

import java.util.Set;

import com.braintribe.gwt.fileapi.client.ProgressHandler;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.resource.Resource;
import com.braintribe.model.resource.source.ResourceSource;
import com.braintribe.model.resource.specification.ResourceSpecification;

/**
 * @see ResourceCreateBuilder
 * @see ResourceUpdateBuilder
 */
@SuppressWarnings("unusable-by-js")
public interface AbstractResourceBuilder<B extends AbstractResourceBuilder<B>> {

	B mimeType(String mimeType);
	B md5(String md5);
	B useCase(String useCase);
	B tags(Set<String> tags);
	/** Sets the type of {@link ResourceSource} to be created for the {@link Resource}. */
	B sourceType(EntityType<? extends ResourceSource> sourceType);
	/** Sets the type of {@link ResourceSpecification} to be created for the {@link Resource}. */
	B specification(ResourceSpecification specification);
	B name(String resourceName);

	B withProgressHandler(ProgressHandler progressHandler);
}
