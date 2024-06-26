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

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.annotation.Transient;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.data.natures.HasAccessModifierNature;
import com.braintribe.zarathud.model.data.natures.HasAnnotationsNature;
import com.braintribe.zarathud.model.data.natures.HasFieldsNature;
import com.braintribe.zarathud.model.data.natures.HasFinalNature;
import com.braintribe.zarathud.model.data.natures.HasScopeModifierNature;
import com.braintribe.zarathud.model.data.natures.HasStaticNature;


/**
 * represents a field (aka member) of a {@link ClassEntity}
 * @author pit
 *
 */
public interface FieldEntity extends GenericEntity, HasAccessModifierNature, HasAnnotationsNature, HasScopeModifierNature, HasFinalNature, HasStaticNature {
	
	final EntityType<FieldEntity> T = EntityTypes.T(FieldEntity.class);
	String name = "name";
	String signature = "signature";
	String desc = "desc";
	String type = "type";
	String initializer = "initializer";
	String owner = "owner";
	

	/**
	 * @return - name of the field
	 */
	String getName();
	void setName(String value);
	
	/**
	 * @return - signature of the field
	 */
	String getSignature();
	void setSignature( String value);
	
	/**
	 * @return - raw desc
	 */
	String getDesc();
	void setDesc( String value);

	/**
	 * @return - resolved {@link ZedEntity} which is the type of the field
	 */
	TypeReferenceEntity getType();
	void setType( TypeReferenceEntity type);
	
	/**
	 * TODO: triple check that .. doesn't detect assignments in initialing
	 * @return - the initial value  (or null)
	 */
	@Transient
	Object getInitializer();
	void setInitializer(Object value);
	
	/**
	 * @return - the owning {@link ClassEntity}
	 */
	HasFieldsNature getOwner();
	void setOwner( HasFieldsNature owner);
	
	/**
	 * @return - the type as passed to the initializer of a generic entities T field.
	 */
	TypeReferenceEntity getEntityTypesParameter();
	void setEntityTypesParameter( TypeReferenceEntity tre);
	
}
