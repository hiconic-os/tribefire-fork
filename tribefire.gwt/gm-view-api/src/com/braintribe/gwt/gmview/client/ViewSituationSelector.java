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
package com.braintribe.gwt.gmview.client;

import java.util.Set;

import com.braintribe.model.generic.path.ModelPathElementInstanceKind;
import com.braintribe.model.generic.path.ModelPathElementType;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.generic.typecondition.TypeCondition;
import com.braintribe.model.meta.selector.MetaDataSelector;

public interface ViewSituationSelector extends MetaDataSelector {
	
	final EntityType<ViewSituationSelector> T = EntityTypes.T(ViewSituationSelector.class);

	public TypeCondition getValueType();
	public void setValueType(TypeCondition valueType);
	
	public ModelPathElementType getPathElementType();	
	public void setPathElementType(ModelPathElementType pathElementType);
	
	public Double getConflictPriority(); 	
	public void setConflictPriority(Double conflictPriority);
	
	public String getMetadataTypeSignature();
	public void setMetadataTypeSignature(String metadataTypeSignature);
	
	public ModelPathElementInstanceKind getElementInstanceKind();
	public void setElementInstanceKind(ModelPathElementInstanceKind elementInstanceKind);
	
	public Set<String> getUseCases();
	public void setUseCases(Set<String> useCases);
	
}
