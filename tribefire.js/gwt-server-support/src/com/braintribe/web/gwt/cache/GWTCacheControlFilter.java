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
package com.braintribe.web.gwt.cache;

import java.io.IOException;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * {@link Filter} to add cache control headers for GWT generated files to ensure that the correct files get cached.
 *
 */
public class GWTCacheControlFilter implements Filter {
	
	private static long ONE_DAY = 86400000L;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//NOP
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		String requestURI = httpRequest.getRequestURI();

		Date now = new Date();
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		httpResponse.setDateHeader("Date", now.getTime());
		if (requestURI.contains(".nocache.") || requestURI.contains("packaging.xml")) { //No cache
			// one day old
			httpResponse.setDateHeader("Expires", now.getTime() - ONE_DAY);
			httpResponse.setHeader("Pragma", "no-cache");
			httpResponse.setHeader("Cache-control", "no-cache, no-store, must-revalidate");
		} else if (requestURI.contains("bt-resources") && (requestURI.contains(".css") || requestURI.contains(".htm"))) { //1 day cache
			httpResponse.setDateHeader("Expires", now.getTime() + ONE_DAY);
		} else if (requestURI.contains(".cache.")) { //cache "forever" (one year)
			httpResponse.setDateHeader("Expires", now.getTime() + (365 * ONE_DAY));
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		//NOP
	}

}
