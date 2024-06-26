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
package com.braintribe.gwt.gmview.client;

import java.util.function.Function;

import com.braintribe.gwt.gmresourceapi.client.GmImageResource;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.model.resource.Resource;

/**
 * THis provider will prepare an {@link IconAndType} based on the received {@link Resource}.
 * @author michel.docouto
 *
 */
public class ResourceIconProvider extends IconProviderAdapter {
	
	private Function<? super Resource, String> resourceUrlProvider;
	
	/**
	 * Configures the URL provider for resources.
	 * It is used when the session is not capable of handling resources.
	 */
	@Configurable
	public void setResourceUrlProvider(Function<? super Resource, String> resourceUrlProvider) {
		this.resourceUrlProvider = resourceUrlProvider;
	}
	
	@Override
	public IconAndType apply(ModelPath modelPath) throws RuntimeException {
		if (modelPath == null || !(modelPath.last().getValue() instanceof Resource))
			return null;
		
		Resource resource = modelPath.last().getValue();
		return new IconAndType(prepareGmImageResource(resource), false);
	}
	
	private GmImageResource prepareGmImageResource(Resource resource) {
		if (resourceUrlProvider != null)
			return new GmImageResource(resource, resourceUrlProvider);
			//ManagedGmSession
		
		GmSession session = resource.session();
		if (!(session instanceof ManagedGmSession))
			return null;
		
		return new GmImageResource(resource, ((ManagedGmSession) session).resources().url(resource).asString());
	}

}
