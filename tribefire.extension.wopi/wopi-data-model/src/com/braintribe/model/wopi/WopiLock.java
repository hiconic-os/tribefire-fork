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
package com.braintribe.model.wopi;

import java.util.Date;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.annotation.meta.Unmodifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * Document lock information
 * 
 * @see <a href= "https://wopi.readthedocs.io/projects/wopirest/en/latest/files/Lock.html">WOPI Locking mechanism</a>
 * 
 *
 */
@SelectiveInformation("${lock}/${creationDate}")
public interface WopiLock extends StandardIdentifiable {

	EntityType<WopiLock> T = EntityTypes.T(WopiLock.class);

	String lock = "lock";
	String creationDate = "creationDate";

	@Name("Lock")
	@Description("WOPI Lock information.")
	@Mandatory
	@Unmodifiable
	String getLock();
	void setLock(String lock);

	@Name("Creation Date")
	@Description("Creation Date of the WOPI lock.")
	@Mandatory
	@Unmodifiable
	Date getCreationDate();
	void setCreationDate(Date creationDate);

}
