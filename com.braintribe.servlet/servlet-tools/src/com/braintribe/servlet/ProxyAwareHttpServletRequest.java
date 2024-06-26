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
package com.braintribe.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import com.braintribe.logging.Logger;
import com.braintribe.utils.lcd.StringTools;

public class ProxyAwareHttpServletRequest extends HttpServletRequestWrapper {

	private static Logger logger = Logger.getLogger(ProxyAwareHttpServletRequest.class);

	private String externalUrl = null;

	public ProxyAwareHttpServletRequest(String externalUrl, HttpServletRequest request) {
		super(request);
		if (StringTools.isBlank(externalUrl)) {
			throw new IllegalArgumentException("The external URL must not be blank.");
		}
		if (externalUrl.endsWith("/")) {
			externalUrl = StringTools.removeLastNCharacters(externalUrl, 1);
		}
		if (!externalUrl.startsWith("http")) {
			logger.debug(() -> "External URL is relative. This deactivates this wrapper.");
		} else {
			this.externalUrl = externalUrl;
		}
	}

	@Override
	public StringBuffer getRequestURL() {

		boolean debug = logger.isDebugEnabled();

		String originalRequestUrl = null;
		try {
			originalRequestUrl = super.getRequestURL().toString();
		} catch (Exception e) {
			logger.debug(() -> "Could not get the original request URL from the request: " + super.toString(), e);
		}

		if (this.externalUrl == null) {
			return new StringBuffer(originalRequestUrl);
		}

		String servletPath = super.getServletPath();
		String pathInfo = super.getPathInfo();

		if (debug) {
			logger.debug("RequestURI is null. Checking servletPath (" + servletPath + ") + pathInfo (" + pathInfo + ").");
		}

		String requestUri = null;
		if (servletPath != null && pathInfo != null) {
			requestUri = servletPath + pathInfo;
		}

		if (requestUri == null || requestUri.equals("/")) {
			if (debug) {
				logger.debug("RequestURI is " + requestUri + ". Overriding with ''.");
			}
			requestUri = "";
		}
		String realUrl = this.externalUrl + requestUri;

		if (debug) {
			String proto = getHeader("x-forwarded-proto");
			String host = getHeader("x-forwarded-host");
			String port = getHeader("x-forwarded-port");
			String path = getHeader("x-replaced-path");
			String forwardedFor = getHeader("x-forwarded-for");
			String server = getHeader("x-forwarded-server");
			String via = getHeader("via");

			StringBuilder sb = new StringBuilder();
			sb.append("x-forwarded-proto: " + proto);
			sb.append(", x-forwarded-host: " + host);
			sb.append(", x-forwarded-port: " + port);
			sb.append(", x-replaced-path: " + path);
			sb.append(", x-forwarded-for: " + forwardedFor);
			sb.append(", x-forwarded-server: " + server);
			sb.append(", via: " + via);

			logger.debug("Using RequestURL " + realUrl + " instead of " + originalRequestUrl + " (Request: " + sb.toString() + ")");
		}

		return new StringBuffer(realUrl);
	}

}
