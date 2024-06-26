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
package com.braintribe.devrock.zed.api.core;

import com.braintribe.devrock.zed.api.context.CommonZedCoreContext;
import com.braintribe.zarathud.model.data.Artifact;

/**
 * a registry to contain information about all {@link Artifact}s
 * @author pit
 *
 */
public interface ArtifactRegistry {
	
	/**
	 * looks up an {@link Artifact} based on whether it contains a specified type (via its signature)
	 * @param context - the {@link CommonZedCoreContext}
	 * @param signature - the signature of a type within the {@link Artifact} 
	 * @return - the {@link Artifact} 
	 */
	Artifact artifact( CommonZedCoreContext context, String signature);
	
	/**
	 * returns the {@link Artifact} that was identified as runtime artifact (java JRE libs)
	 * @param context - the {@link CommonZedCoreContext}
	 * @return - the runtime {@link Artifact} 
	 */
	Artifact runtimeArtifact( CommonZedCoreContext context);
	
	/**
	 * returns the single {@link Artifact} that cannot be identified, and contains all such type references 
	 * @param context - the {@link CommonZedCoreContext}
	 * @return - the 'unknown' {@link Artifact}
	 */
	Artifact unknownArtifact( CommonZedCoreContext context);
	
	
	/**
	 * @param context
	 * @param name
	 * @return
	 */
	Artifact unknownArtifact( CommonZedCoreContext context, String name);
}
