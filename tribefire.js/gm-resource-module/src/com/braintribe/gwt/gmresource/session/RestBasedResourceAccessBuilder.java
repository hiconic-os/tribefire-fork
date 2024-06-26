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
package com.braintribe.gwt.gmresource.session;

import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.fileapi.client.FileList;
import com.braintribe.model.processing.session.api.resource.ResourceAccess;
import com.braintribe.model.processing.session.api.resource.ResourceCreateBuilder;
import com.braintribe.model.processing.session.api.resource.ResourceDeleteBuilder;
import com.braintribe.model.processing.session.api.resource.ResourceRetrieveBuilder;
import com.braintribe.model.processing.session.api.resource.ResourceUpdateBuilder;
import com.braintribe.model.processing.session.api.resource.ResourceUrlBuilder;
import com.braintribe.model.resource.Resource;
import com.braintribe.processing.async.api.JsPromise;

@SuppressWarnings("unusable-by-js")
public class RestBasedResourceAccessBuilder implements ResourceAccess {
	protected Supplier<String> accessIdProvider;
	protected String streamBaseUrl;
	protected Supplier<String> sessionIdProvider;
	
	public RestBasedResourceAccessBuilder(Supplier<String> accessIdProvider, String streamBaseUrl, Supplier<String> sessionIdProvider) {
		super();
		this.accessIdProvider = accessIdProvider;
		this.streamBaseUrl = streamBaseUrl;
		this.sessionIdProvider = sessionIdProvider;
	}

	@Override
	public ResourceUrlBuilder url(Resource resource) {
		String accessId = getAccessId();
		return new RestBasedResourceUrlBuilder(accessId, resource, streamBaseUrl, sessionIdProvider);
		
	}	

	@Override
	public ResourceCreateBuilder create() {		
		return new RestBasedResourceCreateBuilder(getAccessId(), streamBaseUrl, sessionIdProvider);
	}

	@Override
	public ResourceRetrieveBuilder retrieve(Resource resource) {
		throw new UnsupportedOperationException(RestBasedResourceAccessBuilder.class.getName() + " does not support the retrieve(Resource) method.");
	}

	@Override
	public ResourceDeleteBuilder delete(Resource resource) {
		throw new UnsupportedOperationException(RestBasedResourceAccessBuilder.class.getName() + " does not support the delete(Resource) method.");
	}
	
	@Override
	public ResourceUpdateBuilder update(Resource resource) {
		return new RestBasedResourceUpdateBuilder(getAccessId(), streamBaseUrl, sessionIdProvider, resource);
	}

	@Override
	public String urlAsString(Resource resource) {
		return url(resource).asString();
	}

	@Override
	public JsPromise<List<Resource>> createFromFiles(FileList files) {
		return create().store(files);
	}

	private String getAccessId() {
		try {
			return accessIdProvider.get();
		} catch (RuntimeException e) {
			throw new RuntimeException("error when retrieving accessId", e);
		}
	}

}
