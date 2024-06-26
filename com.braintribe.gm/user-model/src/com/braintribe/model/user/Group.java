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

import com.braintribe.model.generic.annotation.meta.Unique;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * @author gunther.schenk
 *
 */
public interface Group extends Identity {

	final EntityType<Group> T = EntityTypes.T(Group.class);
	
	public static final String localizedName = "localizedName";
	public static final String users = "users";
	public static final String conflictPriority = "conflictPriority";
	
	@Override
	@Unique
	String getName();

	/**
	 * @param localizedName the localizedName to set
	 */
	public void setLocalizedName(LocalizedString localizedName);
	/**
	 * @return the localizedName
	 */
	public LocalizedString getLocalizedName();
	
	/**
	 * @param users the users to set
	 */
	public void setUsers(Set<User> users);
	/**
	 * @return the users
	 */
	public Set<User> getUsers();
	
	/**
	 * @param conflictPriority the conflictPriority to set
	 */
	public void setConflictPriority(double conflictPriority);
	/**
	 * @return the conflictPriority
	 */
	public double getConflictPriority();
	
	@Override
	default String roleName() {
		return "$group-"+getName();
	}


}
