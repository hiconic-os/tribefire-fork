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

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.processing.query.smart.test.model.accessB.PersonB;
import com.braintribe.model.processing.query.smart.test.model.smart.Car;
import com.braintribe.model.processing.query.smart.test.model.smart.SmartPersonA;

/**
 * @see PersonB
 * @see SmartPersonA
 */

public interface PersonA extends StandardIdentifiableA {

	EntityType<PersonA> T = EntityTypes.T(PersonA.class);

	String nameA = "nameA";
	String companyNameA = "companyNameA";
	String companyNameSetA = "companyNameSetA";
	
	// @formatter:off
	BigDecimal getDecimalIdEntityId();
	void setDecimalIdEntityId(BigDecimal decimalIdEntityId);

	/** The name is also unique, so can be used as a loose reference */
	String getNameA();
	void setNameA(String nameA);

	/**
	 * Used for IKPA, the point is that property with this name does not exist in {@link SmartPersonA} and the suffix means we do not
	 * resolve the default mapping (which is configured with selector that says everything with suffixA is mapped). 
	 */
	String getNickNameX();
	void setNickNameX(String nickNameX);
	
	String getParentB();
	void setParentB(String parentB);

	String getCompanyNameA();
	void setCompanyNameA(String companyNameA);

	CompanyA getCompanyA();
	void setCompanyA(CompanyA companyA);

	// ############################################
	// ## . . . . . Composite Properties . . . . ##
	// ############################################

	Long getCompositeId();
	void setCompositeId(Long compositeId);

	String getCompositeName();
	void setCompositeName(String compositeName);

	String getCompositeCompanyName();
	void setCompositeCompanyName(String compositeCompanyName);

	// ############################################
	// ## . . . . . Simple Collections . . . . . ##
	// ############################################

	Set<String> getNickNamesSetA();
	void setNickNamesSetA(Set<String> nickNamesSetA);

	List<String> getNickNamesListA();
	void setNickNamesListA(List<String> nickNamesListA);

	Map<Integer, String> getNickNamesMapA();
	void setNickNamesMapA(Map<Integer, String> nickNamesMapA);

	// ############################################
	// ## . . . . . Entity Collections . . . . . ##
	// ############################################

	Set<CompanyA> getCompanySetA();
	void setCompanySetA(Set<CompanyA> companySetA);

	List<CompanyA> getCompanyListA();
	void setCompanyListA(List<CompanyA> companyListA);

	Map<CompanyA, PersonA> getCompanyOwnerA();
	void setCompanyOwnerA(Map<CompanyA, PersonA> companyOwnerA);

	Map<CarA, String> getCarAliasA();
	void setCarAliasA(Map<CarA, String> carAliasA);

	Map<Car, PersonA> getCarLendToA();
	void setCarLendToA(Map<Car, PersonA> carLendToA);

	// ############################################
	// ## . . . . Key Entity Collections . . . . ##
	// ############################################

	Set<String> getCompanyNameSetA();
	void setCompanyNameSetA(Set<String> companyNameSetA);

	List<String> getCompanyNameListA();
	void setCompanyNameListA(List<String> companyNameListA);

	Map<String, String> getKeyFriendEmployerNameA();
	void setKeyFriendEmployerNameA(Map<String, String> keyFriendEmployerNameA);

	// ############################################
	// ## . . . . Id to Unique Mapping . . . . . ##
	// ############################################

	Id2UniqueEntityA getId2UniqueEntityA();
	void setId2UniqueEntityA(Id2UniqueEntityA id2UniqueEntityA);

	Set<Id2UniqueEntityA> getId2UniqueEntitySetA();
	void setId2UniqueEntitySetA(Set<Id2UniqueEntityA> id2UniqueEntitySetA);
	// @formatter:on

}
