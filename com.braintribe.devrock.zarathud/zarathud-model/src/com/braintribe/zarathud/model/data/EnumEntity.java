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
import com.braintribe.zarathud.model.data.natures.HasAbstractNature;
import com.braintribe.zarathud.model.data.natures.HasAccessModifierNature;
import com.braintribe.zarathud.model.data.natures.HasStaticNature;
import com.braintribe.zarathud.model.data.natures.HasSynchronizedNature;


/**
 * represents an enum.. 
 * TODO: check what an enum can have.. derivations? members? methods? 
 * @author pit
 *
 */
public interface EnumEntity extends ClassOrInterfaceEntity, HasAccessModifierNature, HasStaticNature, HasSynchronizedNature, HasAbstractNature  {
	
	final EntityType<EnumEntity> T = EntityTypes.T(EnumEntity.class);
	
	String values = "values";
		
	Set<String> getValues();
	void setValues(Set<String> values);

	/**
	 * @return - single super type, a {@link ClassEntity} of course
	 */
	TypeReferenceEntity getSuperType();
	void setSuperType( TypeReferenceEntity supertype);
	
	/**
	 * @return - all deriving types as a {@link Set} of {@link ClassEntity}
	 */
	Set<ClassEntity> getSubTypes();
	void setSubTypes( Set<ClassEntity> subTypes);
	
	/**
	 * @return - a {@link Set} all {@link InterfaceEntity} the class implements
	 */
	Set<TypeReferenceEntity> getImplementedInterfaces();
	void setImplementedInterfaces( Set<TypeReferenceEntity> entries);

}
