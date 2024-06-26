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
package com.braintribe.model.processing.xmi.converter.coding.differentiator;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * the {@link ModelDifferentiatorContext} is used as collection-point of the different findings during differentiation
 * @author pit
 *
 */
public class ModelDifferentiatorContext {
	private boolean isDiffering;
	private List<DifferentiationReason> reasons = new ArrayList<>();
	private String accumulatedOldDifferentiations;
	private Date date = new Date();
	private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");

	/**
	 * @return - true if the model has changed 
	 */
	public boolean isDiffering() {
		return isDiffering;
	}
	public void setDiffering(boolean isDiffering) {
		this.isDiffering = isDiffering;
	}

	/**
	 * @return - a {@link List} of {@link DifferentiationReason} why the model is changed
	 */
	public List<DifferentiationReason> getReasons() {
		return reasons;
	}
	public void setReasons(List<DifferentiationReason> reasons) {
		this.reasons = reasons;
	}
	
	/**
	 * @return - accumulated differnentations from the XMI
	 */
	public String getAccumulatedOldDifferentiations() {
		return accumulatedOldDifferentiations;
	}
	public void setAccumulatedOldDifferentiations(String accumulatedOldDifferentiations) {
		this.accumulatedOldDifferentiations = accumulatedOldDifferentiations;
	}
	
	/**
	 * @return - all accumulated {@link DifferentiationReason} collated into a {@link String}
	 */
	public String asString() {
		if (!isDiffering) {
			if (accumulatedOldDifferentiations == null) {
				return "";
			}
			else {
				return accumulatedOldDifferentiations;
			}
		}
		StringBuilder sb = new StringBuilder();
		// 
		sb.append( "changed detected on [" + date() + "]:");
		for (DifferentiationReason reason : reasons) {
			sb.append("\n");
			sb.append( reason.asString());
		}
		
		if (accumulatedOldDifferentiations != null) {
			sb.append("\n");
			sb.append( accumulatedOldDifferentiations);			
		}
		
		return sb.toString();
		
	}
	public String date() { 
		return format.format(date);
	}
	
	
	
	
}
