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
import com.braintribe.model.processing.query.smart.test.model.smart.Company;

/**
 * Mapped from {@link Company}
 */
public interface CompanyA extends StandardIdentifiableA {

	final EntityType<CompanyA> T = EntityTypes.T(CompanyA.class);

	String nameA = "nameA";

	String getNameA();
	void setNameA(String nameA);

	PersonA getOwnerA();
	void setOwnerA(PersonA ownerA);

	Long getOwnerIdA();
	void setOwnerIdA(Long ownerIdA);

}