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
package com.braintribe.devrock.zed.core.comparison;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import com.braintribe.devrock.zed.api.comparison.ComparisonContext;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.zarathud.model.data.AnnotationEntity;
import com.braintribe.zarathud.model.data.ZedEntity;
import com.braintribe.zarathud.model.forensics.FingerPrint;
import com.braintribe.zarathud.model.forensics.findings.ComparisonProcessFocus;

/**
 * basic internal context for all comparators
 * 
 * @author pit
 */
public class BasicComparisonContext implements ComparisonContext {	
	private Set<String> processed = new HashSet<>();
	private List<FingerPrint> fingerPrints = new ArrayList<>();
	
	private Stack<GenericEntity> processing = new Stack<>();
	private Stack<GenericEntity> comparing = new Stack<>();
	
	private Stack<ComparisonProcessFocus> focus = new Stack<>(); 
	
	@Override
	public void addProcessed( String name) {
		processed.add(name);
	}
	
	@Override
	public boolean isProcessed( String name) {
		return processed.contains(name);
	}
	
	@Override
	public void addFingerPrint( FingerPrint fp) {
		GenericEntity entitySource = fp.getEntitySource();
		if (entitySource != null) {
			if (entitySource instanceof ZedEntity) {
				ZedEntity ze = (ZedEntity) entitySource;
				if (ze instanceof AnnotationEntity == false && ze.getDefinedInTerminal() == false) {
					System.out.println("Fingerprint on entity not defined in terminal?");
					//return;
				}
			}
		}
		fingerPrints.add(fp);
	}
	
	@Override
	public List<FingerPrint> getFingerPrints() {
		return fingerPrints;
	}
	
	@Override
	public GenericEntity getCurrentEntity() {	
		return processing.isEmpty() ? null : processing.peek();	
	}

	@Override
	public void pushCurrentEntity(GenericEntity current) {	
		processing.push(current);		
	}

	@Override
	public void popCurrentEntity() {		
		processing.pop();			
	}
	
	@Override
	public GenericEntity getCurrentOther() {		
		return comparing.peek();
	}

	@Override
	public void pushCurrentOther(GenericEntity current) {
		comparing.push(current);
		
	}

	@Override
	public void popCurrentOther() {
		comparing.pop();
	}

	@Override
	public ComparisonProcessFocus getCurrentProcessFocus() {	
		return focus.peek();
	}		
	@Override
	public void pushCurrentProcessFocus(ComparisonProcessFocus focus) {
		this.focus.push(focus);
		
	}

	@Override
	public void popCurrentProcessFocus() {
		focus.pop();
		
	}
	
}
