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
package com.braintribe.model.processing.query.smart.test.model.accessB;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * 
 */

public interface PersonB extends StandardIdentifiableB {

	final EntityType<PersonB> T = EntityTypes.T(PersonB.class);

	// @formatter:off
	/** such property is defined for both A and B */
	String getNameB();
	void setNameB(String nameB);

	int getAgeB();
	void setAgeB(int ageB);

	String getParentA();
	void setParentA(String parentA);

	String getCompanyNameB();
	void setCompanyNameB(String companyNameB);

	String getBirthDate();
	void setBirthDate(String birthDate);

	List<String> getDates();
	void setDates(List<String> dates);
	// @formatter:on

}
