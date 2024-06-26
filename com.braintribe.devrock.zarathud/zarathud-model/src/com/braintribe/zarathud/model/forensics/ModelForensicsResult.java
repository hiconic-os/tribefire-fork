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
package com.braintribe.zarathud.model.forensics;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.forensics.data.ModelEntityReference;
import com.braintribe.zarathud.model.forensics.data.ModelEnumReference;

/**
 * the result on the forensics run on a model artifact 
 * @author pit
 *
 */
public interface ModelForensicsResult extends ForensicsResult {
		
	EntityType<ModelForensicsResult> T = EntityTypes.T(ModelForensicsResult.class);
		
	String modelEntityReferences = "modelEntityReferences";
	String modelEnumEntities = "modelEnumEntities";
	String declarationResult = "declarationResult";

	/**
	 * @return - a {@link List} of the {@link ModelEntityReference} - the 'model'-specific view on an artifact 
	 */
	List<ModelEntityReference> getModelEntityReferences();
	void setModelEntityReferences(List<ModelEntityReference> value);
	
	List<ModelEnumReference> getModelEnumEntities();
	void setModelEnumEntities(List<ModelEnumReference> value);

	
	/**
	 * @return - the result of the forensics run on the model-declaration.xml file 
	 */
	ModelDeclarationForensicsResult getDeclarationResult();
	void setDeclarationResult( ModelDeclarationForensicsResult result);
	 
	
}
