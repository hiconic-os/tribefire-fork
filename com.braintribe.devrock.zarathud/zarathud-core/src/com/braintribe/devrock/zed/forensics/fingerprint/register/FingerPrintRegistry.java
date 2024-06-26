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
package com.braintribe.devrock.zed.forensics.fingerprint.register;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.braintribe.devrock.zed.forensics.fingerprint.FingerPrintCommons;
import com.braintribe.devrock.zed.forensics.fingerprint.filter.FingerPrintFilter;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.zarathud.model.data.ZedEntity;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.findings.IssueType;

/**
 * contains collected {@link FingerPrint}, can be persisted and gives high-level access to the stored data  
 * @author pit
 *
 */
public class FingerPrintRegistry implements FingerPrintCommons {
	
	private List<FingerPrint> prints = new ArrayList<>();

	public List<FingerPrint> getPrints() {
		return prints;
	}
	public void setPrints(List<FingerPrint> prints) {
		this.prints = prints;
	}	
	
	/**
	 * finds whether a {@link FingerPrint} with the corresponding {@link ForensicsFindingCode} exist in the registry 
	 * @param code - the {@link IssueType} to look for amongst *all* stored prints
	 * @return - true if a (single) {@link FingerPrint} with the passed {@link ForensicsFindingCode} exists in the registry
	 */
	public boolean hasPrintOfIssue( IssueType code) {			
		return hasPrintOfIssue(prints, code);
	}
	
	 
	/**
	 * finds whether at least one {@link FingerPrint} in the registry matches the filter  
	 * @param filter - the filter to apply 
	 * @return - true if at least on FingerPrint matches 
	 */
	public boolean hasPrintOfIssue( Predicate<FingerPrint> filter) {
		return hasPrintOfIssue(prints, filter);
	}		
	
		
	/**
	 * @param filter - {@link Predicate} to filter {@link FingerPrint}, maybe an instance of {@link FingerPrintFilter}
	 * @return - a {@link List} of matching {@link FingerPrint}
	 */
	public List<FingerPrint> filter( Predicate<FingerPrint> filter) {
		return prints.stream().filter(filter).collect( Collectors.toList());
	}
		
	
	public Collection<GenericEntity> findMatchingSource( Collection<ZedEntity> population, FingerPrint fingerPrint) {
		return null;
	}
			
}
