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
package com.braintribe.devrock.model.repository;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.meta.Confidential;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * represents the credentials - only user & password 
 * @author pit
 *
 */
public interface HasCredentials extends GenericEntity {
	
	EntityType<HasCredentials> T = EntityTypes.T(HasCredentials.class);
	
	String user = "user";
	String password = "password";

	/**
	 * @return - the name of the user as {@link String}. May contain variables like ${env.*}
	 */
	String getUser();
	void setUser( String user);
	
	/**
	 * @return - the password of the user as {@link String}. May contain variables like ${env.*}
	 */
	@Confidential
	String getPassword();
	void setPassword( String password);
	
}
