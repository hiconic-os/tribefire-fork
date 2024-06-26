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
package com.braintribe.devrock.zarathud.runner.api;

import java.util.Map;

import com.braintribe.common.lcd.Pair;
import com.braintribe.devrock.zed.api.context.ZedAnalyzerContext;
import com.braintribe.devrock.zed.forensics.fingerprint.register.RatingRegistry;
import com.braintribe.gm.model.reason.Maybe;
import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.forensics.ClasspathForensicsResult;
import com.braintribe.zarathud.model.forensics.DependencyForensicsResult;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.ForensicsRating;
import com.braintribe.zarathud.model.forensics.ModelForensicsResult;
import com.braintribe.zarathud.model.forensics.ModuleForensicsResult;

/**
 * wire based extraction/forensics/output-runner 
 * @author pit
 *
 */
public interface ZedWireRunner{
	/**
	 * runs the extraction, analysis, ratings etc, returns result. 
	 * If first value of the {@link Pair} is null, no run was done (probably no jar in terminal).  
	 * @return - a {@link Pair} of the worst {@link ForensicsRating} and the {@link FingerPrint} and their respective rating
	 */
	Maybe<Pair<ForensicsRating, Map<FingerPrint, ForensicsRating>>> run();
	
	
	/**
	 * simply returns the data that was collected during run, the same as via {@link #run()}
	 * @return - a {@link Pair} of the worst {@link ForensicsRating} and the {@link FingerPrint} and their respective rating
	 */
	Maybe<Pair<ForensicsRating, Map<FingerPrint, ForensicsRating>>> collectedForensics();
	
	
	/**
	 * @return - the {@link Artifact} representation of the the analyzed terminal
	 */
	Artifact analyzedArtifact();
	
	/** 
	 * @return - the {@link DependencyForensicsResult} as collected
	 */
	DependencyForensicsResult dependencyForensicsResult();
	
	/**
	 * @return - the {@link ClasspathForensicsResult} as collected
	 */
	ClasspathForensicsResult classpathForensicsResult();
	
	/**
	 * @return - the {@link ModelForensicsResult} as collected (if any)
	 */
	ModelForensicsResult modelForensicsResult();
	
	/**
	 * @return - the {@link ModuleForensicsResult} as collected
	 */
	ModuleForensicsResult moduleForensicsResult();
	
	/**
	 * @return - the built {@link RatingRegistry} (configured via the context)
	 */
	RatingRegistry ratingRegistry();
	
	
	/**
	 * @return - the {@link ZedAnalyzerContext} as created during the run
	 */
	ZedAnalyzerContext analyzerContext();
}
