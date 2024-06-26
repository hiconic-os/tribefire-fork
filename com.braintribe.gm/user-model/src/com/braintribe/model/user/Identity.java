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
package com.braintribe.model.user;

import java.util.Set;

import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.annotation.Abstract;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.resource.Icon;

/**
 * @author gunther.schenk
 */
@SelectiveInformation("${name}")
@Abstract
public interface Identity extends StandardStringIdentifiable, AuthEntity {
	
	EntityType<Identity> T = EntityTypes.T(Identity.class);
	
	String description = "description";
	String email = "email";
	String roles = "roles";
	String picture = "picture";
	
	LocalizedString getDescription();
	void setDescription(LocalizedString description);

	String getEmail();
	void setEmail(String email);

	Set<Role> getRoles();
	void setRoles(Set<Role> roles);	

	Icon getPicture();
	void setPicture(Icon picture);
}
