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

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.fileapi.client.Blob;
import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.resource.Resource;
import com.braintribe.processing.async.api.JsPromise;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

/**
 * Builder for updating the binary data associated with {@link Resource} as well as the resource itself if needed.
 * 
 * @author Neidhart.Orlich
 */
@JsType(namespace = GmCoreApiInteropNamespaces.resources)
@SuppressWarnings("unusable-by-js")
public interface ResourceUpdateBuilder extends AbstractResourceBuilder<ResourceUpdateBuilder> {

	ResourceUpdateBuilder deleteOldResourceSource(boolean keep);

	@JsIgnore
	Future<Resource> withBlob(Blob blob);

	@JsIgnore
	Future<Resource> withText(String text);

	@JsMethod(name="withBlob")
	JsPromise<Resource> withBlobJs(Blob blob);
	
	@JsMethod(name="withText")
	JsPromise<Resource> withTextJs(String text);
	
}