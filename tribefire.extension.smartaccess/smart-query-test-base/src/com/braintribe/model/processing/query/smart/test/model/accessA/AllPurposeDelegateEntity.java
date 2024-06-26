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
package com.braintribe.model.processing.query.smart.test.model.accessA;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.smart.AllPurposeSmartEntity;

/**
 * This entity has no standard mapping, other than that it is mapped from {@link AllPurposeSmartEntity}. For special cases custom property
 * mappings are provided.
 * 
 * @author peter.gazdik
 */

public interface AllPurposeDelegateEntity extends StandardIdentifiableA {

	final EntityType<AllPurposeDelegateEntity> T = EntityTypes.T(AllPurposeDelegateEntity.class);

	// @formatter:off
	String getStringProperty();
	void setStringProperty(String value);
	
	Long getLongProperty();
	void setLongProperty(Long value);
	// @formatter:on

}
