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
package com.braintribe.gwt.gme.constellation.client.expert;

import java.util.function.Supplier;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.async.client.LoaderChainImpl;
import com.braintribe.gwt.async.client.TextLoader;
import com.braintribe.gwt.genericmodel.client.codec.dom4.GmXmlCodec;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.model.packaging.Packaging;


/**
 * Provider responsible for providing the {@link Packaging} instance from the xml.
 * @author michel.docouto
 *
 */
public class PackagingProvider implements Supplier<Future<Packaging>> {
	
	private Future<Packaging> packagingFuture;
	private String packagingXmlUrl;
	private String defaultPackagingXmlUrl = "packaging.xml";
	private String clientUrl;
	
	/**
	 * Configures the URL of the packaging.xml.
	 * Defaults to "./packaging.xml".
	 */
	@Configurable
	public void setPackagingXmlUrl(String packagingXmlUrl) {
		this.packagingXmlUrl = packagingXmlUrl;
	}

	/**
	 * Configures the client Url to be used in the default Url formation, when no URL is set via {@link #setPackagingXmlUrl(String)}.
	 */
	@Configurable
	public void setClientUrl(String clientUrl) {
		if (!clientUrl.endsWith("/"))
			clientUrl += "/";
		this.clientUrl = clientUrl;
	}

	@Override
	public Future<Packaging> get() throws RuntimeException {
		if (packagingFuture == null) {
			if (packagingXmlUrl == null)
				packagingXmlUrl = clientUrl == null ? defaultPackagingXmlUrl : clientUrl + defaultPackagingXmlUrl;
			
			packagingFuture = LoaderChainImpl
					.begin(new TextLoader(packagingXmlUrl))
					.decode(new GmXmlCodec<Packaging>())
					.load();
		}
		
		return packagingFuture;
	}

}
