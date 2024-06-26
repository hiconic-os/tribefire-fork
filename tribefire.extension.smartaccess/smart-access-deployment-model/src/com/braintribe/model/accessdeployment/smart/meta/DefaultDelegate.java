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
package com.braintribe.model.accessdeployment.smart.meta;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.EntityTypeMetaData;

/**
 * Specifies the default access to which a newly instantiated entity should be persisted, in case there is no other way
 * to determine it (explicit assignment, relationships with entities whose access is uniquely determined).
 * 
 * For more information about this partition inference see SmartAccess javadoc (there is a class called AccessResolver).
 */
public interface DefaultDelegate extends EntityTypeMetaData {

	EntityType<DefaultDelegate> T = EntityTypes.T(DefaultDelegate.class);

	IncrementalAccess getAccess();
	void setAccess(IncrementalAccess access);

}
