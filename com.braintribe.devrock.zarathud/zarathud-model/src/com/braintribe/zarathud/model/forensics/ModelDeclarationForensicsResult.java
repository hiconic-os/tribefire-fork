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

/**
 * the result of the forensics running on the model-declaration.xml file.
 * 
 * @author pit
 *
 */
public interface ModelDeclarationForensicsResult extends ForensicsResult {
	
	EntityType<ModelDeclarationForensicsResult> T = EntityTypes.T(ModelDeclarationForensicsResult.class);
	
	String modelDeclarationContents = "modelDeclarationContents";
	String missingDependencyDeclarations = "missingDependencyDeclarations";
	String excessDependencyDeclarations = "excessDependencyDeclarations";
	String missingTypeDeclarations = "missingTypeDeclarations";
	String excessTypeDeclarations = "excessTypeDeclarations";
	String fileHash = "fileHash";
	String computedHash = "computedHash";
	
	/**
	 * @return - a {@link String} representation of the declaration file 
	 */
	String getModelDeclarationContents();
	void setModelDeclarationContents(String value);
	
	
	/**
	 * @return - a {@link List} of {@link String} with the condensed names of the missing artifacts {@code (<groupId>:<artifactId>)}
	 */
	List<String> getMissingDependencyDeclarations();
	void setMissingDependencyDeclarations(List<String> value);
	
	/**
	 * @return - a {@link List} of {@link String} with the condensed names 
	 */
	List<String> getExcessDependencyDeclarations();
	void setExcessDependencyDeclarations(List<String> value);
	
	
	/**
	 * @return - a {@link List} of {@link String} of the missing types (just name)
	 */
	List<String> getMissingTypeDeclarations();
	void setMissingTypeDeclarations(List<String> value);
	
	/**
	 * @return - a {@link List} of {@link String} of the excess types (just name)
	 */
	List<String> getExcessTypeDeclarations();
	void setExcessTypeDeclarations(List<String> value);
	
	
	
	

}
