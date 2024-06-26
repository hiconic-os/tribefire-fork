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
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.gwt.logging.ui.gxt.client.GxtReasonErrorDialog;
import com.braintribe.gwt.logging.ui.gxt.client.experts.ServerNotRunningAction;
import com.braintribe.gwt.logging.ui.gxt.client.experts.ServerNotRunningExceptionFilter;
import com.braintribe.gwt.security.client.AuthorizationExceptionFilter;
import com.braintribe.gwt.security.client.SecurityService;
import com.braintribe.gwt.security.client.SessionNotFoundExceptionFilter;
import com.braintribe.gwt.security.client.SessionNotFoundReasonFilter;
import com.braintribe.gwt.security.tfh.client.TfhSecurityService;
import com.braintribe.provider.SingletonBeanProvider;

/**
 * This is the IoC configuration for the Services.
 *
 */
class Services {	
	
	protected static Supplier<SecurityService> securityService = new SingletonBeanProvider<SecurityService>() {
		{
			attach(Controllers.sessionController);
			//ErrorDialog.addExceptionFilterAction(new AuthorizationExceptionFilter(), new ShowAuthorizationExceptionMessageAction()::handleError);
			ErrorDialog.addExceptionFilterAction(new ServerNotRunningExceptionFilter(), new ServerNotRunningAction()::handleError);
			ErrorDialog.addExceptionFilterAction(new AuthorizationExceptionFilter(), Actions.sessionNotFoundExceptionMessageAction.get()::handleError);
			ErrorDialog.addExceptionFilterAction(new SessionNotFoundExceptionFilter(), Actions.sessionNotFoundExceptionMessageAction.get()::handleError);
			ErrorDialog.addExceptionFilterAction(new SessionNotFoundReasonFilter(), Actions.sessionNotFoundExceptionMessageAction.get()::handleError);
			GxtReasonErrorDialog.setGmContentViewSupplier(Constellations.errorMasterViewConstellationSupplier);
			GxtReasonErrorDialog.setIconProvider(Providers.typeIconProvider);
		}
		@Override
		public TfhSecurityService create() throws Exception {
			TfhSecurityService bean = (TfhSecurityService) publish(new TfhSecurityService());
			bean.setLocaleProvider(() -> UrlParameters.getInstance().getParameter("locale"));
			bean.setLocaleProvider(Startup.localeProvider.get());
			//bean.setPreparingLoaderProvider(preparingLoaderProvider);
			bean.setEvaluator(GmRpc.serviceRequestEvaluator.get());
			bean.setLogoutServletUrl(Runtime.logoutServletUrlProvider.get());
			return bean;
		}
	};
	
}
