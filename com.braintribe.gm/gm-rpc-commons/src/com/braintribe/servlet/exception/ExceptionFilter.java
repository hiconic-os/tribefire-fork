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
package com.braintribe.servlet.exception;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.braintribe.cfg.Configurable;
import com.braintribe.logging.Logger;

public class ExceptionFilter implements Filter {

	private static final Logger logger = Logger.getLogger(ExceptionFilter.class);
	
	private Set<ExceptionHandler> exceptionHandlers;
	
	@Override
	public void destroy() {
		/* Intentionally left empty */
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		
		try {
			filterChain.doFilter(request, response);
		}
		catch(Throwable t) {

			String tracebackId = UUID.randomUUID().toString();

			ExceptionHandlingContext context = new ExceptionHandlingContext(tracebackId, request, response, t);
			if (response.isCommitted()) {
				logger.debug("Response has been committed already. Dumping exception in log.");
				context.setOutputCommitted(true);
			}
			
			boolean handled = false;
			for (ExceptionHandler handler : this.exceptionHandlers) {
				Boolean result = handler.apply(context);
				if (result != null && result.booleanValue()) {
					handled = true;
				}
			}
			
			if (!handled) {
				throw t;
			}
			
		}
	}
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		if (exceptionHandlers == null || exceptionHandlers.isEmpty()) {
			exceptionHandlers = new LinkedHashSet<>();
			exceptionHandlers.add(new StandardExceptionHandler());
		}
	}

	@Configurable
	public void setExceptionHandlers(Set<ExceptionHandler> exceptionHandlers) {
		this.exceptionHandlers = exceptionHandlers;
	}

}
