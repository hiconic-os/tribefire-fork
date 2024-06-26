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
package com.braintribe.common.attribute.common.impl;

import java.util.Set;

import com.braintribe.common.attribute.common.UserInfo;

public class BasicUserInfo implements UserInfo {
	private String userName;
	private Set<String> roles;
	
	public BasicUserInfo(String userName, Set<String> roles) {
		super();
		this.userName = userName;
		this.roles = roles;
	}
	
	@Override
	public Set<String> roles() {
		return roles;
	}
	
	@Override
	public String userName() {
		return userName;
	}
}
