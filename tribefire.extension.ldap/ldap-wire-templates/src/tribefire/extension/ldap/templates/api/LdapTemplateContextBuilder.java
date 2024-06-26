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
package tribefire.extension.ldap.templates.api;

import java.util.Set;

import tribefire.extension.templates.api.TemplateContextBuilder;

public interface LdapTemplateContextBuilder extends TemplateContextBuilder<LdapTemplateContext> {

	LdapTemplateContextBuilder setConnectionUrl(String connectionUrl);
	LdapTemplateContextBuilder setUsername(String username);
	LdapTemplateContextBuilder setPassword(String password);

	LdapTemplateContextBuilder setGroupBase(String groupBase);
	LdapTemplateContextBuilder setUserBase(String userBase);

	LdapTemplateContextBuilder setGroupIdAttribute(String groupIdAttribute);
	LdapTemplateContextBuilder setGroupMemberAttribute(String groupMemberAttribute);
	LdapTemplateContextBuilder setGroupNameAttribute(String groupNameAttribute);
	LdapTemplateContextBuilder setGroupsAreRoles(boolean groupsAreRoles);

	LdapTemplateContextBuilder setMemberAttribute(String memberAttribute);
	LdapTemplateContextBuilder setGroupObjectClasses(Set<String> groupObjectClasses);

	LdapTemplateContextBuilder setRoleIdAttribute(String roleIdAttribute);
	LdapTemplateContextBuilder setRoleNameAttribute(String roleNameAttribute);

	LdapTemplateContextBuilder setUserIdAttribute(String userIdAttribute);
	LdapTemplateContextBuilder setUserFirstNameAttribute(String userFirstNameAttribute);
	LdapTemplateContextBuilder setUserLastNameAttribute(String userLastNameAttribute);
	LdapTemplateContextBuilder setUserUsernameAttribute(String userUsernameAttribute);
	LdapTemplateContextBuilder setUserDisplayNameAttribute(String userDisplayNameAttribute);
	LdapTemplateContextBuilder setEmailAttribute(String emailAttribute);

	LdapTemplateContextBuilder setUserFilter(String userFilter);

	LdapTemplateContextBuilder setLastLogonAttribute(String lastLogonAttribute);
	LdapTemplateContextBuilder setMemberOfAttribute(String memberOfAttribute);
	LdapTemplateContextBuilder setUserObjectClasses(Set<String> userObjectClasses);

	LdapTemplateContextBuilder setSearchPageSize(int searchPageSize);

	LdapTemplateContextBuilder setReferralFollow(boolean referralFollow);
	LdapTemplateContextBuilder setConnectTimeout(long connectTimeout);
	LdapTemplateContextBuilder setDnsTimeout(long dnsTimeout);
	LdapTemplateContextBuilder setDnsRetries(int dnsRetries);

	LdapTemplateContextBuilder setBase(String base);
	LdapTemplateContextBuilder setUseEmptyAspects(boolean useEmptyAspects);

	LdapTemplateContextBuilder setUseTlsExtension(boolean useTlsExtension);

}