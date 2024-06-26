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
package com.braintribe.gwt.customizationui.client.startup;

import com.braintribe.gwt.gxt.gxtresources.whitemask.client.MaskController;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.Scheduler;

/**
 * Default implementation for loading the logo and title for the client loading message.
 * @author michel.docouto
 *
 */
public abstract class GmeEntryPoint implements EntryPoint {
	
	protected boolean logoSet = false;
	protected boolean titleSet = false;
	private static final long TIMEOUT = 5000;
	
	protected void waitForLogoAndText(String servicesUrl, String accessId, String applicationId) {
		long start = System.currentTimeMillis();
		Scheduler.get().scheduleFixedDelay(() -> {
			if (!titleSet) {
				String title = TribefireRuntime.getProperty("TRIBEFIRE_LOADING_TITLE", null, true);
				if (title != null) {
					titleSet = true;
					MaskController.setProgressBarTitle(title);
				}
			}
			
			if (!logoSet) {
				String logoUrl = TribefireRuntime.getProperty("TRIBEFIRE_LOGO_URL", null, true);
				if (logoUrl != null) {
					logoSet = true;
					if (!logoUrl.equals(getDefaultLogoUrl(servicesUrl, accessId, applicationId)))
						MaskController.setProgressBarImage(logoUrl);
				}
			}
			
			if (logoSet && titleSet)
				return false;
			
			return System.currentTimeMillis() - start <= TIMEOUT;
		}, 100);
	}
	
	protected String getDefaultLogoUrl(String servicesUrl, String accessId, String applicationId) {
		String imageSource = servicesUrl + "publicResource/dynamic/gme-logo?";
		if (accessId != null)
			imageSource = imageSource + "accessId=" + accessId + "&";
			
		imageSource = imageSource + "applicationId=" + applicationId;
		
		return imageSource;
	}
	
}
