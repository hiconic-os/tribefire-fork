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
package tribefire.extension.ldap.initializer.wire.contract;

import com.braintribe.wire.api.annotation.Decrypt;
import com.braintribe.wire.api.annotation.Default;

import tribefire.cortex.initializer.support.wire.contract.PropertyLookupContract;

public interface RuntimePropertiesContract extends PropertyLookupContract {

	@Default("false")
	boolean LDAP_INITIALIZE_DEFAULTS();

	String LDAP_CONN_URL(String defaultValue);
	String LDAP_CONN_USERNAME();
	@Decrypt
	String LDAP_CONN_PASSWORD_ENCRYPTED();

	@Default("OU=Groups,OU=<Organization>,DC=<Company>")
	String LDAP_BASE_GROUPS();
	@Default("OU=Accounts,OU=<Organization>,DC=<Company>")
	String LDAP_BASE_USERS();

	@Default("distinguishedName")
	String LDAP_GROUP_ID();
	@Default("member")
	String LDAP_GROUP_MEMBER();
	@Default("name")
	String LDAP_GROUP_NAME();
	@Default("true")
	boolean LDAP_GROUPS_ARE_ROLES();
	@Default("memberOf")
	String LDAP_MEMBER_ATTRIBUTE();
	@Default("group")
	String LDAP_GROUP_OBJECT_CLASSES();

	@Default("distinguishedName")
	String LDAP_ROLE_ID();
	@Default("name")
	String LDAP_ROLE_NAME();

	@Default("distinguishedName")
	String LDAP_USER_ID();
	@Default("givenName")
	String LDAP_USER_FIRSTNAME();
	@Default("sn")
	String LDAP_USER_LASTNAME();
	@Default("sAMAccountName")
	String LDAP_USER_NAME();
	@Default("displayName")
	String LDAP_USER_DESCRIPTION();
	@Default("mail")
	String LDAP_USER_MAIL();
	@Default("(sAMAccountName=%s)")
	String LDAP_USER_FILTER();
	@Default("lastLogon")
	String LDAP_USER_LASTLOGON();
	@Default("memberOf")
	String LDAP_USER_MEMBER_OF();
	@Default("user")
	String LDAP_USER_OBJECT_CLASSES();

	@Default("20")
	int LDAP_SEARCH_PAGESIZE();

	@Default("false")
	boolean LDAP_REFERRAL_FOLLOW();
	@Default("30000")
	long LDAP_CONNECT_TIMEOUT();
	@Default("10000")
	long LDAP_DNS_TIMEOUT_INITIAL();
	@Default("3")
	int LDAP_DNS_TIMEOUT_RETRIES();

	@Default("OU=<Organization>,DC=<Company>")
	String LDAP_BASE();

	@Default("false")
	boolean LDAP_USE_EMPTY_ASPECTS();

	@Default("false")
	boolean LDAP_ATTACH_TO_CORTEXCONFIGURATION();

}
