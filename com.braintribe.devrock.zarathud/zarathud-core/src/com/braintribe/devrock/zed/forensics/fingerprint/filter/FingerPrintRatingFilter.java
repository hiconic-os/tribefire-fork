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
package com.braintribe.devrock.zed.forensics.fingerprint.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.findings.IssueType;

/**
 * filter to {@link FingerPrint}s with one or more specific issue within streams. 
 * 
 * @author pit
 *
 */
public class FingerPrintRatingFilter implements Predicate<FingerPrint>{
	
	private List<String> codes = new ArrayList<>();
	
	/**
	 * @param codes - an array of {@link IssueType}s as {@link String}
	 */
	public FingerPrintRatingFilter(String ... codes) {
		if (codes != null) {
			this.codes.addAll( Arrays.asList( codes));
		}
	}
	
	
	/**
	 * @param codes - an array of {@link IssueType}s
	 */
	public FingerPrintRatingFilter(IssueType ... codes) {
		if (codes != null) {
			for (IssueType code : codes) {
				this.codes.add( code.name());			 
			}
		}
	}
	
	/**
	 * @param codes - an {@link List} of {@link IssueType}
	 */
	public FingerPrintRatingFilter(List<IssueType> codes) {		
		if (codes != null) {
			for (IssueType code : codes) {
				this.codes.add( code.name());			 
			}
		}
	}
	

	@Override
	public boolean test(FingerPrint t) {
		String code = t.getSlots().get("issue");		
		return codes.contains(code);
	}

	
}
