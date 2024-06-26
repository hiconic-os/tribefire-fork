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
package com.braintribe.zarathud.model.storage;

import java.util.Map;

import com.braintribe.gm.model.reason.Reason;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.forensics.ClasspathForensicsResult;
import com.braintribe.zarathud.model.forensics.DependencyForensicsResult;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.ForensicsRating;
import com.braintribe.zarathud.model.forensics.ModelForensicsResult;
import com.braintribe.zarathud.model.forensics.ModuleForensicsResult;

public interface ViewingContextStorageContainer extends GenericEntity{
	
	EntityType<ViewingContextStorageContainer> T = EntityTypes.T(ViewingContextStorageContainer.class);
	
	
	Artifact getArtifact();
	void setArtifact(Artifact value);
	
	ModelForensicsResult getModelForensicsResult();
	void setModelForensicsResult(ModelForensicsResult value);

	DependencyForensicsResult getDependencyForensicsResult();
	void setDependencyForensicsResult(DependencyForensicsResult value);
	
	ClasspathForensicsResult getClasspathForensicsResult();
	void setClasspathForensicsResult(ClasspathForensicsResult value);
	
	ModuleForensicsResult getModuleForensicsResult();
	void setModuleForensicsResult(ModuleForensicsResult value);

	ForensicsRating getWorstRating();
	void setWorstRating(ForensicsRating value);

	Map<FingerPrint, ForensicsRating> getIssues();
	void setIssues(Map<FingerPrint, ForensicsRating> value);

	Reason getAnalyzerProcessingReturnReason();
	void setAnalyzerProcessingReturnReason(Reason value);
	
	Map<FingerPrint, ForensicsRating> getActiveRatings();
	void setActiveRatings(Map<FingerPrint, ForensicsRating> value);
	
	
}
	
