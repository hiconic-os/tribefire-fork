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

import java.util.function.Supplier;

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.processing.session.api.persistence.AccessDescriptor;
import com.braintribe.model.processing.session.api.resource.ResourceAccess;
import com.braintribe.model.processing.session.api.resource.ResourceAccessFactory;

public class GwtSessionResourceSupport implements ResourceAccessFactory<AccessDescriptor> {
	protected String streamBaseUrl;
	protected Supplier<String> sessionIdProvider;
	protected boolean accessoryAxis = false;

	@Configurable
	public void setAccessoryAxis(boolean accessoryAxis) {
		this.accessoryAxis = accessoryAxis;
	}

	@Required
	public void setStreamBaseUrl(String streamBaseUrl) {
		this.streamBaseUrl = streamBaseUrl;
	}

	@Required
	public void setSessionIdProvider(Supplier<String> sessionIdProvider) {
		this.sessionIdProvider = sessionIdProvider;
	}

	@Override
	public ResourceAccess newInstance(AccessDescriptor accessInfo) {
		Supplier<String> accessIdProvider = accessoryAxis ? () -> "cortex" : accessInfo::accessId;
		return new RestBasedResourceAccessBuilder(accessIdProvider, streamBaseUrl, sessionIdProvider);
	}

	public ResourceAccess newInstanceForDomainId(String domainId) {
		Supplier<String> domainIdProvider = accessoryAxis ? () -> "cortex" : () -> domainId;
		return new RestBasedResourceAccessBuilder(domainIdProvider, streamBaseUrl, sessionIdProvider);
	}

}
