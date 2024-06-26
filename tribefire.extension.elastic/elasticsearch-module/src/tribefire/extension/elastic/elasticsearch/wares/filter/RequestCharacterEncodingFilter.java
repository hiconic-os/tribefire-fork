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
package tribefire.extension.elastic.elasticsearch.wares.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;

/**
 * Request character encoding filter.
 *
 * <p>
 * This class is used for setting character encoding for ServletRequest object and content-type for ServletResponse
 * object for each request coming from the client. The encoding to be set for the request is by default set to UTF-8.
 * </p>
 *
 */
public class RequestCharacterEncodingFilter implements Filter {

	private String encoding = null;

	public RequestCharacterEncodingFilter() {
		this.encoding = null;
	}

	@Override
	public void init(FilterConfig config) {
		if (this.encoding == null) {
			this.encoding = config.getInitParameter("encoding");
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		request.setCharacterEncoding(encoding);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {
		// Intentionally left blank
	}

	@Configurable
	@Required
	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}

}
