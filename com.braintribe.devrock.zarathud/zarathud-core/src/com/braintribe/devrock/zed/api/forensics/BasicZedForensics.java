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
package com.braintribe.devrock.zed.api.forensics;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.data.ZedEntity;

public interface BasicZedForensics {

	/**
	 * returns a {@link Map} of caller {@link ZedEntity} in the terminal to a {@link List} of all callee {@link ZedEntity}
	 * @param runtime - the runtime artifact 
	 * @param artifact - the artifact that contains the callee
	 * @return - a {@link Map} of caller to callee 
	 */
	Map<ZedEntity, List<ZedEntity>> getTerminalReferencesToArtifact(Artifact runtime, Artifact artifact);

	/**
	 * returns a {@link Set} of {@link Artifact}s that were not declared 
	 * @param declared - the {@link Collection} of {@link Artifact}s the were declared
	 * @param found - the {@link Collection} of {@link Artifact}s that were found 
	 * @return
	 */
	Set<Artifact> extractUndeclaredDependencies(Collection<Artifact> declared, Collection<Artifact> found);

	/**
	 * not used? 
	 */
	List<ZedEntity> getEntitiesWithMultipleSources(List<ZedEntity> population);

	/**
	 * not used?
	 */
	Map<String, List<ZedEntity>> collectEntitiesWithSameMultipleSources(List<ZedEntity> multiSourcePopulation);

}