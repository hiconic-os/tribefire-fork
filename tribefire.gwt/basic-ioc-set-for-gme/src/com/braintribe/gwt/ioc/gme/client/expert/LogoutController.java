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
package com.braintribe.gwt.ioc.gme.client.expert;

import java.util.function.Supplier;

import com.braintribe.gwt.gme.constellation.client.SaveAction;
import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.braintribe.gwt.ioc.client.Required;


/**
 * This controller is responsible for warning the user that a manipulation was not already saved, if any, before
 * performing a logout.
 * 
 * @author michel.docouto
 * 
 */
public class LogoutController implements Supplier<String> {

	private Supplier<SaveAction> saveActionProvider;
	
	@Required
	public void setSaveActionProvider(Supplier<SaveAction> saveActionProvider) {
		this.saveActionProvider = saveActionProvider;
	}
	
	@Override
	public String get() throws RuntimeException {
		if(saveActionProvider != null) {
			SaveAction saveAction = saveActionProvider.get();
			if (saveAction.getEnabled())
				return LocalizedText.INSTANCE.signoutConfirmation();
		}		
		return null;
	}

}
