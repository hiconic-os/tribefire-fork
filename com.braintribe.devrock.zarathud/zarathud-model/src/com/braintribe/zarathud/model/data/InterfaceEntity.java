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
package com.braintribe.zarathud.model.data;

import java.util.Set;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.data.natures.HasGenericNature;


/**
 * represents an interface 
 * @author pit
 *
 */
public interface InterfaceEntity extends ClassOrInterfaceEntity, HasGenericNature {
	
	final EntityType<InterfaceEntity> T = EntityTypes.T(InterfaceEntity.class);
	String superInterfaces = "superInterfaces";
	String subInterfaces = "subInterfaces";
	String implementingClasses = "implementingClasses";

	/**
	 * @return - a {@link Set} of {@link InterfaceEntity} it extends 
	 */
	Set<TypeReferenceEntity> getSuperInterfaces();
	void setSuperInterfaces( Set<TypeReferenceEntity> entries);
	
	/**
	 * @return - a {@link Set} of {@link InterfaceEntity} that derive from it
	 */
	Set<InterfaceEntity> getSubInterfaces();
	void setSubInterfaces( Set<InterfaceEntity> subInterfaces);
	
	/**
	 * @return - a {@link Set} of {@link ClassEntity} that implement it
	 */
	Set<ClassEntity> getImplementingClasses();
	void setImplementingClasses( Set<ClassEntity> implementingClasses);
}
