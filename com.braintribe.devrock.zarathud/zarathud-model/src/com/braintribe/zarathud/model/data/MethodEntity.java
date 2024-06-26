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

import java.util.List;
import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.data.natures.HasAbstractNature;
import com.braintribe.zarathud.model.data.natures.HasAccessModifierNature;
import com.braintribe.zarathud.model.data.natures.HasAnnotationsNature;
import com.braintribe.zarathud.model.data.natures.HasMethodsNature;
import com.braintribe.zarathud.model.data.natures.HasStaticNature;
import com.braintribe.zarathud.model.data.natures.HasSynchronizedNature;


/**
 * represents a method 
 * @author pit
 *
 */
public interface MethodEntity extends GenericEntity, HasAccessModifierNature, HasAbstractNature, HasStaticNature, HasSynchronizedNature, HasAnnotationsNature {
	
	final EntityType<MethodEntity> T = EntityTypes.T(MethodEntity.class);
	String name = "name";
	String signature = "signature";
	String desc = "desc";
	String exceptions = "exceptions";
	String owner = "owner";
	String returnType = "returnType";
	String argumentTypes = "argumentTypes";
	String bodyTypes = "bodyTypes";

	/**
	 * @return - the name of the method
	 */
	String getName();
	void setName( String name);
	
	/**
	 * @return - the signature of the method
	 */
	String getSignature();
	void setSignature( String signature);
	
	/**
	 * @return - the raw desc of the method
	 */
	String getDesc();
	void setDesc( String desc);
	
	/**
	 * @return - a {@link Set} of all exceptions (as {@link ClassEntity}) thrown 
	 */
	Set<ClassEntity> getExceptions();
	void setExceptions( Set<ClassEntity> exceptions);
	
	/**
	 * @return - the owner of the method, a {@link ClassOrInterfaceEntity}
	 */
	HasMethodsNature getOwner();
	void setOwner( HasMethodsNature owner);
	
	/**
	 * @return - the return type as {@link ZedEntity}
	 */
	TypeReferenceEntity getReturnType();
	void setReturnType( TypeReferenceEntity desc);	
	
	/**
	 * @return - a {@link List} with the arguments as {@link ZedEntity}
	 */
	List<TypeReferenceEntity> getArgumentTypes();
	void setArgumentTypes( List<TypeReferenceEntity> types);
	
	/**
	 * @return - a {@link List} with the typereferences found in the body
	 */
	List<TypeReferenceEntity> getBodyTypes();
	void setBodyTypes(List<TypeReferenceEntity> types);
	
	/**
	 * @return - true if the methods is a default method (i.e. owner is interface, method had instructions)
	 */
	boolean getIsDefault();
	void setIsDefault( boolean isDefault);
			
}
