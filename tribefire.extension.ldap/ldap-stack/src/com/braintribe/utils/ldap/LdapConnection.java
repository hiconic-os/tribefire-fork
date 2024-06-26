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
package com.braintribe.utils.ldap;

import javax.naming.ldap.LdapContext;

/**
 * <p>Interface describing the methods necessary for an LDAP connection stack.
 * Whenevery a piece of software needs an LDAP connection, the {@link #pop()} can
 * be used to get an {@link LdapContext} object. When the context is no longer needed,
 * it should be returned to the stack by calling the {@link #push(LdapContext)} method.</p>
 * <p>In case the code has to use different code when dealing with an Active Directory server 
 * (the most prominent use case when dealing with LDAP), it can detect the presence of an AD server
 * by calling the {@link #isActiveDirectory()} method.
 */
public interface LdapConnection {
	
	void push(LdapContext item);
	LdapContext pop() throws Exception;

	boolean isActiveDirectory();
}
