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
package com.braintribe.gwt.gmview.client.js;

import java.util.List;

import com.braintribe.gwt.gmview.client.js.interop.InteropConstants;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

@JsType (namespace = InteropConstants.MODULE_NAMESPACE)
public class ComponentCreateContext {
	
	private String modulePath;
	private String accessId;
	private List<String> cssStyles;
	private JsPersistenceSessionFactory persistenceSessionFactory;
	private String rootUrl;
	private String servicesUrl;
	
	@JsConstructor
	public ComponentCreateContext() {
	}
	
	@JsProperty
	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}
	
	@JsProperty
	public String getModulePath() {
		return modulePath;
	}
	
	@JsProperty
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	
	@JsProperty
	public String getAccessId() {
		return accessId;
	}
	
	@JsProperty
	public void setCssStyles(List<String> cssStyles) {
		this.cssStyles = cssStyles;
	}
	
	@JsProperty
	public List<String> getCssStyles() {
		return cssStyles;
	}
	
	@JsProperty
	public void setPersistenceSessionFactory(JsPersistenceSessionFactory persistenceSessionFactory) {
		this.persistenceSessionFactory = persistenceSessionFactory;
	}
	
	@JsProperty
	public JsPersistenceSessionFactory getPersistenceSessionFactory() {
		return persistenceSessionFactory;
	}
	
	@JsProperty
	public void setRootUrl(String rootUrl) {
		this.rootUrl = rootUrl;
	}
	
	@JsProperty
	public String getRootUrl() {
		return rootUrl;
	}
	
	@JsProperty
	public void setServicesUrl(String servicesUrl) {
		this.servicesUrl = servicesUrl;
	}
	
	@JsProperty
	public String getServicesUrl() {
		return servicesUrl;
	}

}
