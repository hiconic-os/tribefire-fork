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

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;


public class RolesProvider implements Supplier<Set<String>> {
	private SecurityService securityService;
	
	@Configurable @Required
	public void setSecurityService(SecurityService securityService) {
		this.securityService = securityService;
	}
	
	@Override
	public Set<String> get() throws RuntimeException {
		try {
			return securityService.getSession() != null ? securityService.getSession().getRoles() : new HashSet<String>();
		} catch (SecurityServiceException e) {
			throw new RuntimeException("error while retrieving roles from SecurityService", e);
		}
	}
}
