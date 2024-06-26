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
package com.braintribe.devrock.zed.api;

import java.util.Map;

import com.braintribe.devrock.zed.api.context.ZedAnalyzerContext;
import com.braintribe.devrock.zed.commons.ZedException;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.ForensicsRating;

/**
 * {@link ZedCore} is the front-end for using zarathud's features
 * @author pit
 *
 */
public interface ZedCore {
	/**
	 * analyze a jar - i.e. extract all relevant information of the jar 
	 * @param terminalArtifact - the {@link Artifact} that this jar reflects 
	 * @return - the {@link Artifact} with all relevant information attached 
	 * @throws ZedException - thrown if anything goes wrong
	 */
	Artifact analyseJar(Artifact terminalArtifact) throws ZedException;
	
	/**
	 * @return - the {@link ZedAnalyzerContext} as built up by {@link ZedCore}, required for all Forensics
	 */
	ZedAnalyzerContext getAnalyzingContext();

	
	/**
	 * extract all suppressing annotations, turn them into 'real' fingerprints, and associate IGNORE with them
	 * @param terminalArtifact - a *already* parsed {@link Artifact}, i.e. the entries must be known
	 * @return - a {@link Map} of the {@link FingerPrint} as read from the pertinent annotations 
	 */
	Map<FingerPrint, ForensicsRating> collectSuppressAnnotations( Artifact terminalArtifact); 
}
