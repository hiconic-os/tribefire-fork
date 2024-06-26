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
import com.braintribe.model.processing.query.smart.test.model.smart.CompositeKpaEntity;

/**
 * @see CompositeKpaEntity
 */

public interface CompositeKpaEntityA extends StandardIdentifiableA {

	final EntityType<CompositeKpaEntityA> T = EntityTypes.T(CompositeKpaEntityA.class);

	// @formatter:off
	Long getPersonId();
	void setPersonId(Long personId);

	String getPersonName();
	void setPersonName(String personName);

	String getPersonCompanyName();
	void setPersonCompanyName(String personCompanyName);

	String getDescription();
	void setDescription(String description);
	// @formatter:on

}
