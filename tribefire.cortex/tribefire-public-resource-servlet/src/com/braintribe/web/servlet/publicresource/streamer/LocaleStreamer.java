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
package com.braintribe.web.servlet.publicresource.streamer;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.workbench.WorkbenchConfiguration;
import com.braintribe.utils.IOTools;
import com.braintribe.web.servlet.publicresource.PublicResourceStreamer;

public class LocaleStreamer implements PublicResourceStreamer {
	
	private WorkbenchConfigurationProvider configurationProvider;
	private String defaultLocale = "en";
	
	
	@Required
	@Configurable
	public void setConfigurationProvider(WorkbenchConfigurationProvider configurationProvider) {
		this.configurationProvider = configurationProvider;
	}
	
	@Configurable
	public void setDefaultLocale(String defaultLocale) {
		this.defaultLocale = defaultLocale;
	}
	
	@Override
	public boolean streamResource(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String locale = this.defaultLocale;
		String accessId = request.getParameter("accessId");
		if (accessId != null) {
			WorkbenchConfiguration configuration = this.configurationProvider.getConfiguration(accessId);
			if (configuration != null && configuration.getLocale() != null) {
				locale = configuration.getLocale();
			}
		}		
		
		response.setContentType("text/plain");
		IOTools.pump(new ByteArrayInputStream(locale.getBytes()), response.getOutputStream());
		return true;
	}
}
