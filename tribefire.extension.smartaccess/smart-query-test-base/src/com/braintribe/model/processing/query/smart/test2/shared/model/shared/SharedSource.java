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
package com.braintribe.model.processing.query.smart.test2.shared.model.shared;

import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartGenericEntity;

public interface SharedSource extends SharedEntity, SmartGenericEntity {

	EntityType<SharedSource> T = EntityTypes.T(SharedSource.class);

	String uuid = "uuid";
	String sourceLocation = "sourceLocation";
	
	String getUuid();
	void setUuid(String uuid);

	String getSourceLocation();
	void setSourceLocation(String sourceLocation);

	Set<String> getStringSet();
	void setStringSet(Set<String> stringSet);
	
}
