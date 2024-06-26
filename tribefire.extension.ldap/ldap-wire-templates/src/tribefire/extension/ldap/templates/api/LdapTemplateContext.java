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

import tribefire.extension.templates.api.TemplateContext;

public interface LdapTemplateContext extends TemplateContext {

	static LdapTemplateContextBuilder builder() {
		return new LdapTemplateContextImpl();
	}

	String getConnectionUrl();
	String getUsername();
	String getPassword();

	String getGroupBase();
	String getUserBase();

	String getGroupIdAttribute();
	String getGroupMemberAttribute();
	String getGroupNameAttribute();
	boolean getGroupsAreRoles();

	String getMemberAttribute();
	Set<String> getGroupObjectClasses();

	String getRoleIdAttribute();
	String getRoleNameAttribute();

	String getUserIdAttribute();
	String getUserFirstNameAttribute();
	String getUserLastNameAttribute();
	String getUserUsernameAttribute();
	String getUserDisplayNameAttribute();
	String getEmailAttribute();

	String getUserFilter();

	String getLastLogonAttribute();
	String getMemberOfAttribute();
	Set<String> getUserObjectClasses();

	int getSearchPageSize();

	boolean getReferralFollow();
	long getConnectTimeout();
	long getDnsTimeout();
	int getDnsRetries();

	String getBase();
	boolean getUseEmptyAspects();

	boolean getUseTlsExtension();

}