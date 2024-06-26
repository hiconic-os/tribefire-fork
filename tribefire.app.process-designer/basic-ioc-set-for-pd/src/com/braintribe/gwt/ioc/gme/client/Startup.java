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

import com.braintribe.gwt.customizationui.client.Customization;
import com.braintribe.gwt.customizationui.client.GwtLocaleProvider;
import com.braintribe.gwt.customizationui.client.security.LoginCredentials;
import com.braintribe.gwt.gmview.util.client.LocaleUtil;
import com.braintribe.provider.SingletonBeanProvider;
import com.braintribe.utils.i18n.I18nTools;
import com.google.gwt.core.client.GWT;

public class Startup {
	
	protected static Supplier<GwtLocaleProvider> localeProvider = new SingletonBeanProvider<GwtLocaleProvider>() {
		@Override
		public GwtLocaleProvider create() throws Exception {
			GwtLocaleProvider bean = publish(new GwtLocaleProvider());
			bean.setGmSession(Session.persistenceSession);
			
			LocaleUtil.setLocaleProvider(bean);
			I18nTools.localeProvider = bean;
			return bean;
		}
	};
	
	public static Supplier<Customization> customization = new SingletonBeanProvider<Customization>() {
		{
			attach(Services.securityService);
		}
		@Override
		public Customization create() throws Exception {
			Customization bean = publish(new Customization());
			bean.setSecurityServiceProvider(Services.securityService);
			bean.setHandleInitializationUI(Runtime.handleInitializationUI);
			
			if (!GWT.isProdMode())
				bean.setFixLogin(new LoginCredentials("cortex", "cortex"));
			
			return bean;
		}
	};
	
}
