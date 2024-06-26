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
package com.braintribe.gwt.gme.notification.client;

import java.util.Arrays;

import com.braintribe.gwt.action.client.TriggerInfo;
import com.braintribe.gwt.gme.constellation.client.LocalizedText;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.gwt.gmview.client.ModelActionPosition;

/**
* This action provides clearing of Notification list
*
*/

public class ClearNotificationsAction extends ModelAction {	
	public ClearNotificationsAction() {
		setHidden(true);
		setName(LocalizedText.INSTANCE.clearNotifications());
		setIcon(ConstellationResources.INSTANCE.clearBig());
		setHoverIcon(ConstellationResources.INSTANCE.clearBig());
		put(ModelAction.PROPERTY_POSITION, Arrays.asList(ModelActionPosition.ActionBar));
	}	

	@Override
	protected void updateVisibility() {
		Boolean useHidden = true;
		
		if 	(gmContentView instanceof NotificationView) {
			if (((NotificationView) gmContentView).getStore().size() > 0)
				useHidden = false;
		}
			
		setHidden(useHidden);
	}

	@Override
	public void perform(TriggerInfo triggerInfo) {
		if 	(gmContentView instanceof NotificationView) {
		    ((NotificationView) gmContentView).clearNotifications();
		    updateVisibility();
		}		
	}

}
