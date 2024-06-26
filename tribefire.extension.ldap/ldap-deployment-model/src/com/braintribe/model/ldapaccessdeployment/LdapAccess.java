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

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.ldapconnectiondeployment.LdapConnection;


public interface LdapAccess extends IncrementalAccess {

	final EntityType<LdapAccess> T = EntityTypes.T(LdapAccess.class);
	
	void setBase(String base);
	@Initializer("'OU=<Base>,OU=<Organization>,DC=<Company>'")
	String getBase();
	
	void setLdapConnection(LdapConnection ldapConnection);
	LdapConnection getLdapConnection();
	
	void setSearchPageSize(int searchPageSize);
	@Initializer("20")
	int getSearchPageSize();

}
