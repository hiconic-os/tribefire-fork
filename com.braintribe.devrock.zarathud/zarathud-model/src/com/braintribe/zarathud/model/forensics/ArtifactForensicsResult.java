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
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.forensics.data.ArtifactReference;
import com.braintribe.zarathud.model.forensics.data.ClasspathDuplicate;

/**
 * represents what Zed knows about an artifact - ! NOT A TERMINAL ! 
 * @author pit
 *
 */
public interface ArtifactForensicsResult extends ForensicsResult {
	
	EntityType<ArtifactForensicsResult> T = EntityTypes.T(ArtifactForensicsResult.class);
		
	String numberOfReferences = "numberOfReferences";
	String references = "references";
	String duplicates = "duplicates";
	

	/**
	 * @return - how many types of the terminal references this artifact
	 */
	int getNumberOfReferences();
	void setNumberOfReferences(int numberOfReferences);
	
	/**
	 * @return - the {@link ArtifactReference}s of the terminal into this {@link Artifact}
	 */
	List<ArtifactReference> getReferences();
	void setReferences(List<ArtifactReference>  references);
	
	List<ClasspathDuplicate> getDuplicates();
	void setDuplicates( List<ClasspathDuplicate> duplicates);
	
}
