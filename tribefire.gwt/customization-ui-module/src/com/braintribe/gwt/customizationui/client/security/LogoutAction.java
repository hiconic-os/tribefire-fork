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
package com.braintribe.gwt.customizationui.client.security;

import java.util.function.Supplier;

import com.braintribe.gwt.action.client.Action;
import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.async.client.AsyncCallbacks;
import com.braintribe.gwt.customizationui.client.resources.UiResources;
import com.braintribe.gwt.gmview.client.GlobalState;
import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.gwt.security.client.SecurityService;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.widget.core.client.Dialog.PredefinedButton;
import com.sencha.gxt.widget.core.client.box.ConfirmMessageBox;

/**
 * This action is responsible for logging the user out.
 * @author michel.docouto
 *
 */
public class LogoutAction extends Action {
	
	static {
		UiResources.INSTANCE.css().ensureInjected();
	}
	
	private SecurityService securityService;
	private Supplier<String> logoutController;
	
	public LogoutAction() {
		this.setName(LocalizedText.INSTANCE.signOut());
		this.setStyleName(UiResources.INSTANCE.css().bannerLinkButton());
	}
	
	private void logout() {
		GlobalState.mask(LocalizedText.INSTANCE.signingOut());
    	
		AsyncCallback<Boolean> callback = AsyncCallbacks //
				.of(result -> GlobalState.unmask(), //
						e -> {
							GlobalState.unmask();
							ErrorDialog.show(LocalizedText.INSTANCE.errorSigningOut(), e);
							e.printStackTrace();
						});
		securityService.logout(callback);
	}
	
	/**
	 * Configures the required SecurityService used for performing the logout.
	 */
	@Configurable @Required
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	/**
	 * Configures a provider that can cancel the logout if it returns a message.
	 * That message is then shown to the user, and should be a question.
	 * Only by replying yes, the logout is performed, otherwise it is cancelled.
	 */
	@Configurable
	public void setLogoutController(Supplier<String> logoutController) {
		this.logoutController = logoutController;
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		if (logoutController == null) {
			logout();
			return;
		}
		
		String message = logoutController.get();
		if (message == null) {
			logout();
			return;
		}
		
		ConfirmMessageBox messageBox = new ConfirmMessageBox(LocalizedText.INSTANCE.signOut(), message);
		messageBox.addDialogHideHandler(event -> {
			if (PredefinedButton.YES.equals(event.getHideButton()))
				logout();
		});
	}

}
