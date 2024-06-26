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

import java.util.List;

import com.braintribe.zarathud.model.data.Artifact;

/**
 * matches artifacts - ie. diverts to {@link Artifact} itself 
 * @author pit
 *
 */
public class ArtifactMatcher {
	
	/**
	 * matches two artifacts
	 * @param one - the first {@link Artifact}
	 * @param two - second {@link Artifact}
	 * @return - true if the first's compare run returns 0, false otherwise
	 */
	public static boolean matchArtifactTo( Artifact one, Artifact two) {
		if (one == two)
			return true;
		
		if (two == null) {
			// some java complex type leads to this
			return false;
		}
		
		return one.compareTo(two) == 0;
	}
	
	/**
	 * finds whether the first artifact is contained within the passed list 
	 * @param one - the first {@link Artifact}
	 * @param twos - the second {@link Artifact}
	 * @return - true if the first is contained in the second
	 */
	public static boolean matchArtifactTo( Artifact one, List<Artifact> twos) {
		for (Artifact two : twos) {
			if (matchArtifactTo(one, two)) 
				return true;
		}
		return false;
	}
}
