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
package com.braintribe.devrock.zarathud.model.module;

import java.util.List;

import com.braintribe.devrock.zarathud.model.common.Node;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ImportedModuleNode extends Node {
		
	EntityType<ImportedModuleNode> T = EntityTypes.T(ImportedModuleNode.class);
	
	String artifactName = "artifactName";
	String moduleName = "moduleName";
	String exportedPackages = "exportedPackages";
			
	String getArtifactName();
	void setArtifactName(String value);

	String getModuleName();
	void setModuleName(String value);
	
	List<PackageNode> getExportedPackages();
	void setExportedPackages(List<PackageNode> value);
	
}
