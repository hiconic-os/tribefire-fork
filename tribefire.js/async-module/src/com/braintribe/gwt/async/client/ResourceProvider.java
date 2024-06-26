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
package com.braintribe.gwt.async.client;

import java.util.function.Supplier;

import com.braintribe.codec.Codec;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;

import com.google.gwt.core.client.GWT;

public class ResourceProvider<T> implements Supplier<Future<T>> {
	private Codec<T, String> codec;
	private String pathToResource;
	private Future<T> future;
	
	@Configurable @Required
	public void setCodec(Codec<T, String> codec) {
		this.codec = codec;
	}
	
	@Configurable
	public void setPathToResource(String pathToResource) {
		this.pathToResource = pathToResource;
	}
	
	@Configurable
	public void setNameOfResource(String nameOfResource) {
		//extract context name for the module
		String baseUrl = GWT.getHostPageBaseURL();
		if (baseUrl.endsWith("/"))
			baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
		
		int index = baseUrl.lastIndexOf('/');
		String moduleContextName = baseUrl.substring(index + 1);
		String individualConfigContext = moduleContextName + "-config";
		
		pathToResource = "../../" + individualConfigContext + "/" + nameOfResource;
	}
	
	@Override
	public Future<T> get() throws RuntimeException {
		try {
			if (future == null) {
				future = LoaderChain
						.begin(AsyncUtils.loadStringResource(pathToResource))
						.decode(codec)
						.load();
			}
			return future;
		} catch (RuntimeException e) {
			throw new RuntimeException(e);
		}
	}
}
