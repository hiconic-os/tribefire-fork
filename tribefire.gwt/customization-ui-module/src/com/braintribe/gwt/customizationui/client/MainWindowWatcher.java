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
package com.braintribe.gwt.customizationui.client;

import java.util.function.Supplier;

import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.logging.client.Logger;
import com.braintribe.gwt.security.client.SecurityServiceException;
import com.braintribe.gwt.security.client.Session;
import com.braintribe.model.processing.securityservice.api.SecurityService;
import com.google.gwt.user.client.Window;

/**
 * This is going to make sure the user is logged out when closing the window or leaving to another site.
 * Also, when trying to leave to another site, the user will be prompted if he is sure of that.
 * @author michel.docouto
 *
 */
public class MainWindowWatcher {
	
	private static Logger logger = new Logger(MainWindowWatcher.class);
	
	private boolean requireCloseAffirmation = true;
	private Supplier<String> messageProvider;
	private final String messageToDisplay = LocalizedText.INSTANCE.dataWillBeLost();
	private boolean performLogoutOnClose = true;
	
	/**
	 * Configures whether to show the message to the user, asking for confirmation prior to leaving the application.
	 * Defaults to true.
	 */
	@Configurable
	public void setRequireCloseAffirmation(boolean requireCloseAffirmation) {
		this.requireCloseAffirmation = requireCloseAffirmation;
	}
	
	/**
	 * Configures a provider that provides the message to be displayed when leaving the application.
	 * If this is not set, then the default message is used, which is the LocalizedText entry "dataWillBeLost".
	 */
	@Configurable
	public void setMessageProvider(Supplier<String> messageProvider) {
		this.messageProvider = messageProvider;
	}
	
	/**
	 * Configures whether we should perform a logout when closing the window.
	 * Defaults to true.
	 */
	@Configurable
	public void setPerformLogoutOnClose(boolean performLogoutOnClose) {
		this.performLogoutOnClose = performLogoutOnClose;
	}
	
	public MainWindowWatcher(final SecurityService rpcSecurityService, final com.braintribe.gwt.security.client.SecurityService securityService) {
		Window.addCloseHandler(event -> {
			if (!performLogoutOnClose)
				return;
			
			try {
				Session session = securityService.getSession();
				if (session != null) {
					rpcSecurityService.logout(session.getId());
				}
			} catch (SecurityServiceException e1) {
				logger.error("Exception while getting session onClose.", e1);
				e1.printStackTrace();
			} catch (com.braintribe.model.processing.securityservice.api.exceptions.SecurityServiceException e2) {
				logger.error("Exception while logging out onClose.", e2);
				e2.printStackTrace();
			}
		});
		
		Window.addWindowClosingHandler(event -> {
			String message = null;
			
			try {
				if (requireCloseAffirmation && securityService.getSession() != null) {
					if (messageProvider != null) {
						message = messageProvider.get();
					} else {
						message = messageToDisplay;
					}
				}
			} catch (SecurityServiceException e1) {
				logger.error("Exception while getting the session onWindowClosing.", e1);
				e1.printStackTrace();
			} catch (RuntimeException e2) {
				logger.error("Exception while providing the messageProvider.", e2);
				e2.printStackTrace();
			}
			
			event.setMessage(message);
		});
	}

}
