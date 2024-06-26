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

import java.util.List;

import com.braintribe.devrock.zed.api.context.ZedAnalyzerContext;
import com.braintribe.zarathud.model.data.Artifact;

/**
 * scans an artifact via the entry points (class names)
 * @author pit
 *
 */
public interface ZedArtifactAnalyzer {
	/**
	 * @param context - the {@link ZedAnalyzerContext}
	 * @param entryPoints - the names of the classes within the artifact's jar or folder
	 * @return - a fresh {@link Artifact} with all relevant data 
	 */
	Artifact analyzeArtifact( ZedAnalyzerContext context, List<String> entryPoints);
}
