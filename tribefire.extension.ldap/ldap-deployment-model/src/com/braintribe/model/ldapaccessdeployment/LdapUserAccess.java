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
package com.braintribe.model.ldapaccessdeployment;

import java.util.List;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.ldapconnectiondeployment.LdapConnection;


public interface LdapUserAccess extends IncrementalAccess {

	final EntityType<LdapUserAccess> T = EntityTypes.T(LdapUserAccess.class);
	
	void setGroupBase(String groupBase);
	@Initializer("'OU=Groups,OU=<Organization>,DC=<Company>'")
	String getGroupBase();
	
	void setGroupIdAttribute(String groupIdAttribute);
	@Initializer("'distinguishedName'")
	String getGroupIdAttribute();
	
	void setGroupMemberAttribute(String groupMemberAttribute);
	@Initializer("'member'")
	String getGroupMemberAttribute();
	
	void setGroupNameAttribute(String groupNameAttribute);
	@Initializer("'name'")
	String getGroupNameAttribute();
	
	void setGroupObjectClasses(List<String> groupObjectClasses);
	@Initializer("['group']")
	List<String> getGroupObjectClasses();
	
	void setGroupsAreRoles(boolean groupsAreRoles);
	@Initializer("true")
	boolean getGroupsAreRoles();
	
	void setLdapConnection(LdapConnection ldapConnection);
	LdapConnection getLdapConnection();
	
	void setMemberAttribute(String memberAttribute);
	@Initializer("'memberOf'")
	String getMemberAttribute();
	
	void setRoleIdAttribute(String roleIdAttribute);
	@Initializer("'distinguishedName'")
	String getRoleIdAttribute();
	
	void setRoleNameAttribute(String roleNameAttribute);
	@Initializer("'name'")
	String getRoleNameAttribute();
	
	void setUserBase(String userBase);
	@Initializer("'OU=Accounts,OU=<Organization>,DC=<Company>'")
	String getUserBase();
	
	void setUserDescriptionAttribute(String userDescriptionAttribute);
	@Initializer("'displayName'")
	String getUserDescriptionAttribute();
	
	void setUserEmailAttribute(String userEmailAttribute);
	@Initializer("'mail'")
	String getUserEmailAttribute();
	
	void setUserFilter(String userFilter);
	@Initializer("'(sAMAccountName=%s)'")
	String getUserFilter();
	
	void setUserFirstNameAttribute(String userFirstNameAttribute);
	@Initializer("'givenName'")
	String getUserFirstNameAttribute();
	
	void setUserIdAttribute(String userIdAttribute);
	@Initializer("'distinguishedName'")
	String getUserIdAttribute();
	
	void setUserLastLoginAttribute(String userLastLoginAttribute);
	@Initializer("'lastLogon'")
	String getUserLastLoginAttribute();
	
	void setUserLastNameAttribute(String userLastNameAttribute);
	@Initializer("'sn'")
	String getUserLastNameAttribute();
	
	void setUserMemberOfAttribute(String userMemberOfAttribute);
	@Initializer("'memberOf'")
	String getUserMemberOfAttribute();
	
	void setUserNameAttribute(String userNameAttribute);
	@Initializer("'sAMAccountName'")
	String getUserNameAttribute();
	
	void setUserObjectClasses(List<String> userObjectClasses);
	@Initializer("['user']")
	List<String> getUserObjectClasses();

	void setSearchPageSize(int searchPageSize);
	@Initializer("20")
	int getSearchPageSize();
}
