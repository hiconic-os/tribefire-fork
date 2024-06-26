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

import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;


public class FilterServlet extends HttpServlet {
	
	private static final long serialVersionUID = -5843774757323766685L;
	
	private Filter filter;
	private HttpServlet delegate;

	public Filter getFilter() {
		return filter;
	}

	@Required
	@Configurable
	public void setFilter(Filter authFilter) {
		this.filter = authFilter;
	}
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		filter.doFilter(req, resp, delegate::service);
	}

	@Required
	@Configurable
	public void setDelegate(HttpServlet delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	@Override
	public void destroy() {
		delegate.destroy();
	}

	@Override
	public String getInitParameter(String name) {
		return delegate.getInitParameter(name);
	}

	@Override
	public Enumeration<String> getInitParameterNames() {
		return delegate.getInitParameterNames();
	}

	@Override
	public ServletConfig getServletConfig() {
		return delegate.getServletConfig();
	}

	@Override
	public ServletContext getServletContext() {
		return delegate.getServletContext();
	}

	@Override
	public String getServletInfo() {
		return delegate.getServletInfo();
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		delegate.init(config);
	}

	@Override
	public void init() throws ServletException {
		delegate.init();
	}

	@Override
	public void log(String msg) {
		delegate.log(msg);
	}

	@Override
	public void log(String message, Throwable t) {
		delegate.log(message, t);
	}

	@Override
	public String getServletName() {
		return delegate.getServletName();
	}	
}
