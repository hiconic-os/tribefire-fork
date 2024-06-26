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
package com.braintribe.gwt.security.client;

import java.util.Map;
import java.util.function.Supplier;

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;


public class ProfileValueProvider implements Supplier<String> {
	private String profileName;
	private SecurityService securityService;
	
	@Configurable @Required
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@Configurable @Required
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	
	@Override
	public String get() throws RuntimeException {
		try {
			Session session = securityService.getSession();
			Map<String, String> profileData = session != null? session.getProfileData(): null;
			return profileData != null? profileData.get(profileName): null;
		} catch (SecurityServiceException e) {
			throw new RuntimeException("error while accessing session", e);
		}
	}
}
