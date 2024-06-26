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
/**
 * This artifact provides two different types of Accesses that are able to connect to an LDAP-compatible
 * directory service (e.g., Active Directory).
 * <br><br>
 * <ul>
 *  <li><code>{@link com.braintribe.model.access.impl.LdapAccess}</code>: A general purpose Access that allows to access arbitrary objects in a directory service. 
 *     It requires a model and corresponding meta-data that allows to map entity types to LDAP classes.</li>
 *  <li><code>{@link com.braintribe.model.access.impl.LdapUserAccess}</code>: A specialized LDAP Access that can be used in conjunction with the <code>UserModel</code>. 
 *     It allows to query users and groups and also supports user authentication.</li>
 * </ul>
 */
package com.braintribe.model.access.impl;
