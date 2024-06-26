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

import java.util.ArrayList;
import java.util.List;

import com.braintribe.model.generic.GenericEntity;

public class DifferentiationReason {
	private GenericEntity focus;
	private List<String> reasons = new ArrayList<>();
	
	public DifferentiationReason( GenericEntity focus, String reason) {
		this.focus = focus;
		this.reasons.add(reason);
	}
	public DifferentiationReason( GenericEntity focus, List<String> reasons) {
		this.focus = focus;
		this.reasons.addAll(reasons);
	}
	
	public GenericEntity getFocus() {
		return focus;
	}
	public void setFocus(GenericEntity focus) {
		this.focus = focus;
	}
	
	public List<String> getReasons() {
		return reasons;
	}
	public void setReasons(List<String> reasons) {
		this.reasons = reasons;
	}
	
	public String asString() {
		StringBuilder sb = new StringBuilder();
		
		String focusAsString = focus.getGlobalId();
		sb.append( focusAsString);
		
		for (String reason : reasons) {
			sb.append( "\n");
			sb.append( "\t" + reason);
		}
		return sb.toString();
	}
}
