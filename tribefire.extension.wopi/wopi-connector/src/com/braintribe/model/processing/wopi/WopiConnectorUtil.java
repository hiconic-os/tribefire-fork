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
package com.braintribe.model.processing.wopi;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

import com.braintribe.model.processing.bootstrapping.TribefireRuntime;
import com.braintribe.util.network.NetworkTools;
import com.braintribe.utils.lcd.CommonTools;

/**
 * Utility class related to WOPI connector
 * 
 *
 */
public class WopiConnectorUtil {

	/**
	 * An extension of {@link TribefireRuntime#getPublicServicesUrl()} which returns a outside reachable IP address if
	 * 'localhost' or '127.0.0.1' is configured. Additionally in case {@link TribefireRuntime#getPublicServicesUrl()} is
	 * relative {@link TribefireRuntime#getServicesUrl()} will be used - this is for testing to have a reachable IP
	 * address.
	 * 
	 * @param customPublicServicesUrl
	 *            an optional custom PUBLIC_SERVICES_URL
	 * 
	 * @return Adapted version of {@link TribefireRuntime#ENVIRONMENT_PUBLIC_SERVICES_URL} if 'localhost' or '127.0.0.1'
	 */
	@SuppressWarnings("unused")
	public static String getPublicServicesUrl(String customPublicServicesUrl) {
		if (CommonTools.isEmpty(customPublicServicesUrl)) {
			String publicServicesUrl = TribefireRuntime.getPublicServicesUrl();

			try {
				new URL(publicServicesUrl);
			} catch (MalformedURLException e) {
				publicServicesUrl = TribefireRuntime.getServicesUrl();
			}

			if (StringUtils.containsIgnoreCase(publicServicesUrl, "localhost")) {
				publicServicesUrl = publicServicesUrl.replace("localhost", NetworkTools.getNetworkAddress().getHostAddress());
			}
			if (publicServicesUrl.contains("127.0.0.1")) {
				publicServicesUrl = publicServicesUrl.replace("127.0.0.1", NetworkTools.getNetworkAddress().getHostAddress());
			}

			return publicServicesUrl;
		} else {
			return customPublicServicesUrl;
		}

	}

}
