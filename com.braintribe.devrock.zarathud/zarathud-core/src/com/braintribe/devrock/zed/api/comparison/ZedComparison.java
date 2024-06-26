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
package com.braintribe.devrock.zed.api.comparison;

import com.braintribe.zarathud.model.data.Artifact;
import com.braintribe.zarathud.model.forensics.FingerPrint;

/**
 * compares two artifacts
 * 
 * @author pit
 */
public interface ZedComparison {

	/**
	 * compares two {@link Artifact} and creates {@link FingerPrint} for each comparison issues 
	 * @param base - the base {@link Artifact}
	 * @param toCompare - the {@link Artifact} to compare to
	 * @return - true if no differences were found, false if so. 
	 */
	boolean compare( Artifact base, Artifact toCompare);
	
	/**
	 * @return - the {@link ComparisonContext} created and filled-up during process 
	 */
	ComparisonContext getComparisonContext();
}
