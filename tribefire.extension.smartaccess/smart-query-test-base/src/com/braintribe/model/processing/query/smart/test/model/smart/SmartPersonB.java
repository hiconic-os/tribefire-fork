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
package com.braintribe.model.processing.query.smart.test.model.smart;

import java.util.Date;
import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessB.PersonB;

/**
 * Mapped to {@link PersonB}
 */
public interface SmartPersonB extends StandardSmartIdentifiable, BasicSmartEntity {

	EntityType<SmartPersonB> T = EntityTypes.T(SmartPersonB.class);

	String getNameB();
	void setNameB(String nameB);

	int getAgeB();
	void setAgeB(int ageB);

	String getCompanyNameB();
	void setCompanyNameB(String companyNameB);

	Company getCompanyB();
	void setCompanyB(Company companyB);

	// based on keyProperty - PesonB.parentA = PersonA.nameA
	SmartPersonA getSmartParentA();
	void setSmartParentA(SmartPersonA smartParentA);

	// based on keyProperty - PesonB.parentA = PersonA.id
	SmartPersonA getConvertedSmartParentA();
	void setConvertedSmartParentA(SmartPersonA convertedSmartParentA);

	Date getConvertedBirthDate();
	void setConvertedBirthDate(Date convertedBirthDate);

	List<Date> getConvertedDates();
	void setConvertedDates(List<Date> convertedDates);

}
