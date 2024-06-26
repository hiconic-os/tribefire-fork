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
package com.braintribe.devrock.zarathud.runner.api.cfg;

import java.util.function.Predicate;

import com.braintribe.zarathud.model.data.Artifact;

public class ValidImplicitArtifactReferenceFilter implements Predicate<Artifact> {
	
	private static String [] validArtifacts = new String [] {
			"com.braintribe.gm:gm-core-api",
			"com.braintribe.commons:platform-api",
	}; 
	
	public boolean test( Artifact artifact) {
		String key = artifact.getGroupId() + ":" + artifact.getArtifactId();
		for (String valid : validArtifacts) {
			if (key.equalsIgnoreCase( valid))
				return true;
		}
		return false;
	}
}