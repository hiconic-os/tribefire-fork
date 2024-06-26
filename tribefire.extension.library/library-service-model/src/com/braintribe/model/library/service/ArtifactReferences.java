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
package com.braintribe.model.library.service;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ArtifactReferences extends DependencyContext {

	final EntityType<ArtifactReferences> T = EntityTypes.T(ArtifactReferences.class);

	public final static String artifactIdList = "artifactIdList";
	public final static String resolveDependencies = "resolveDependencies";

	void setArtifactIdList(List<String> artifactIdList);
	List<String> getArtifactIdList();

	boolean getResolveDependencies();
	void setResolveDependencies(boolean resolveDependencies);

	boolean getIncludeTerminalArtifact();
	void setIncludeTerminalArtifact(boolean includeTerminalArtifact);

}
