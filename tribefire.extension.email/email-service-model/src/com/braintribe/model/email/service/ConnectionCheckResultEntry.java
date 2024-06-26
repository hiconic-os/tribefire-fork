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
package com.braintribe.model.email.service;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.meta.Name;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ConnectionCheckResultEntry extends GenericEntity {

	EntityType<ConnectionCheckResultEntry> T = EntityTypes.T(ConnectionCheckResultEntry.class);

	@Name("External ID")
	String getExternalId();
	void setExternalId(String externalId);

	@Name("Name")
	String getName();
	void setName(String name);

	@Name("Type")
	ConnectionType getType();
	void setType(ConnectionType type);

	@Name("Success")
	boolean getSuccess();
	void setSuccess(boolean success);

	@Name("Details")
	String getDetails();
	void setDetails(String details);

	@Name("Error Message")
	String getErrorMessage();
	void setErrorMessage(String errorMessage);
}
