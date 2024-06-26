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
package com.braintribe.model.processing.test.itw.entity;

import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.SelectiveInformation;
import com.braintribe.model.generic.annotation.ToStringInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;


@SelectiveInformation("Hi ${#type} ${double} ${#id} ${#runtimeId} ${N/A} Low2")
@ToStringInformation("Hi ${#type_short} ${double}  ${N/A} Low2")
public interface TestEntity extends GenericEntity {

	EntityType<TestEntity> T = EntityTypes.T(TestEntity.class);

	int getAge();
	void setAge(int s);
	
	double getDouble();
	void setDouble(double d);
	
	Integer getAgeO();
	void setAgeO(Integer s);
	
	AnotherTestEntity getAnotherEntity();
	void setAnotherEntity(AnotherTestEntity ate);
	
	Set<String> getStrings();
	void setStrings(Set<String> s);

}
