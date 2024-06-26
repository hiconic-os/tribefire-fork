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
package com.braintribe.gwt.ioc.gme.client;

import java.util.function.Supplier;

import com.braintribe.gwt.browserfeatures.client.UrlParameters;
import com.braintribe.gwt.logging.ui.gxt.client.GxtReasonErrorDialog;
import com.braintribe.gwt.security.client.SecurityService;
import com.braintribe.gwt.security.tfh.client.TfhSecurityService;
import com.braintribe.provider.SingletonBeanProvider;

public class Services {
	
	protected static Supplier<SecurityService> securityService = new SingletonBeanProvider<SecurityService>() {
		{
			GxtReasonErrorDialog.setGmContentViewSupplier(Constellations.errorMasterViewConstellationSupplier);
		}
		@Override
		public TfhSecurityService create() throws Exception {
			TfhSecurityService bean = (TfhSecurityService) publish(new TfhSecurityService());
			bean.setLocaleProvider(() -> UrlParameters.getInstance().getParameter("locale"));
			bean.setLocaleProvider(Startup.localeProvider.get());
			bean.setEvaluator(GmRpc.serviceRequestEvaluator.get());
			bean.setLogoutServletUrl(Runtime.logoutServletUrlProvider.get());
			return bean;
		}
	};

}
