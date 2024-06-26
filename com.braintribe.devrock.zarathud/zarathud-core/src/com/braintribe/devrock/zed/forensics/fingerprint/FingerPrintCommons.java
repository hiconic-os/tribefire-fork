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
package com.braintribe.devrock.zed.forensics.fingerprint;

import java.util.Collection;
import java.util.function.Predicate;

import com.braintribe.devrock.zed.forensics.fingerprint.filter.FingerPrintFilter;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.findings.IssueType;

/**
 * 
 * @author pit
 */
public interface FingerPrintCommons {

	/**
	 * finds whether a {@link FingerPrint} with the corresponding {@link ForensicsFindingCode} exist in the passed collection
	 * @param fingerprints - the {@link FingerPrint}s to traverse 
	 * @param code - the {@link IssueType} to look for 
	 * @return - true if a (single) {@link FingerPrint} with the passed {@link ForensicsFindingCode} exists in the fingerprints passed
	 */
	default boolean hasPrintOfIssue( Collection<FingerPrint> fingerprints, IssueType code) {
		Predicate<FingerPrint> filter = new FingerPrintFilter( "issue:" + code.name());
		return fingerprints.stream().filter(filter).findAny().isPresent();
	}
	
	/**
	 * finds whether at least one {@link FingerPrint} in the collection matches the filter
	 * @param fingerprints - the {@link FingerPrint}s to traverse
	 * @param filter - the filter to apply 
	 * @return - true if at least on FingerPrint matches
	 */
	default boolean hasPrintOfIssue( Collection<FingerPrint> fingerprints, Predicate<FingerPrint> filter) {			
		return fingerprints.stream().filter(filter).findAny().isPresent();
	}
	
	
}
