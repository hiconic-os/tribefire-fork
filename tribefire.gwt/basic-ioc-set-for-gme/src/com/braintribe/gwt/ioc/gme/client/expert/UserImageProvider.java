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

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.Image;

public class UserImageProvider implements Supplier<Future<Image>> {
	
	private Future<Image> future;
	private String servicesUrl;
	private String userImageUrl = "user-image";
	
	/**
	 * Configures the required url for the tribefire services.
	 */
	@Required
	public void setServicesUrl(String servicesUrl) {
		this.servicesUrl = servicesUrl;
	}
	
	/**
	 * Configures the url for the user. Defaults to "user-image".
	 */
	@Configurable
	public void setUserImageUrl(String userImageUrl) {
		this.userImageUrl = userImageUrl;
	}
	
	@Override
	public Future<Image> get() throws RuntimeException {
		if (this.future == null) {
			this.future = new Future<>();
			
			Scheduler.get().scheduleDeferred(() -> {
				Image image = new Image();
				
				StringBuilder url = new StringBuilder();
				url.append(servicesUrl);
				if (!servicesUrl.endsWith("/"))
					url.append("/");
				
				url.append(userImageUrl);
				image.setUrl(url.toString());
				image.setStyleName("");
				image.setWidth("32px");
				image.setHeight("32px");
				image.getElement().getStyle().setProperty("borderRadius", "50%");
				future.onSuccess(image);
			});
		}
	
		return future;
	}

}
