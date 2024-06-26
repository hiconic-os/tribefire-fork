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

import java.util.Set;
import java.util.function.Predicate;

import com.braintribe.gwt.ioc.client.Configurable;
import com.braintribe.gwt.ioc.client.Required;

public class RoleSessionFilter implements Predicate<Session>{

	private Set<String> validRoles;
	
	@Configurable @Required
	public void setValidRoles(Set<String> validRoles){
		this.validRoles = validRoles;
	}
	
	public Set<String> getValidRoles(){
		return this.validRoles;
	}
	
	@Override
	public boolean test(Session obj) {

		if(obj.getRoles() != null && validRoles != null)
		{
			for(String role : validRoles)
			{
				for(String sessionRole : obj.getRoles())
				{
					if(sessionRole.equalsIgnoreCase(role))
						return true;
				}
			}
		}

		return false;
	}	
}
