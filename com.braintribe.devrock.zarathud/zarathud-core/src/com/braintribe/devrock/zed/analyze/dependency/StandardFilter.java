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
package com.braintribe.devrock.zed.analyze.dependency;

import java.util.function.Predicate;

import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.data.ParameterEntity;
import com.braintribe.zarathud.model.data.ZedEntity;

/**
 * a filter that only allows {@link Artifact}s to pass if they are matching the set one.
 * @author pit
 *
 */
public class StandardFilter implements Predicate<ZedEntity> {
	
	private Artifact artifact;
	
	public StandardFilter(Artifact owner) {
		artifact = owner;
	}

	@Override
	public boolean test(ZedEntity entity) {
		if (entity instanceof ParameterEntity) {			
			return false;
		}
		boolean matches = ArtifactMatcher.matchArtifactTo(artifact, entity.getArtifacts());
	
		/*
		String cases = entity.getArtifacts().stream().map( a -> a.toVersionedStringRepresentation()).collect(Collectors.joining(","));		
		if (matches) {
			System.out.println( "owner [" + artifact.toVersionedStringRepresentation() + "] matches " + cases);
		}
		else {
			System.out.println( "owner [" + artifact.toVersionedStringRepresentation() + "] doesn't match " + cases);
		}
		*/
	
		return matches;
	}

	
	
}
