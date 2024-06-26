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
package com.braintribe.gwt.processdesigner.client.display;

import java.util.List;

import com.braintribe.gwt.gmview.client.GmSelectionSupport;
import com.braintribe.model.descriptive.HasDescription;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.path.ModelPath;

public class ProcessElementDescriptionElement extends ProcessDesignerStatusBarElement{
	
	public ProcessElementDescriptionElement() {
		
	}

	@Override
	public void noticeManipulation(Manipulation manipulation) {
		//NOP
	}

	@Override
	public void onSelectionChanged(GmSelectionSupport gmSelectionSupport) {
		List<ModelPath> selection = gmSelectionSupport.getCurrentSelection();
		if(selection != null && selection.size() == 1){
			for(ModelPath modelPath : selection){
				Object object = modelPath.last().getValue();
				if(object instanceof HasDescription){
					setName(((HasDescription)object).getDescription());
				}
			}
		}else
			setName("");
		
	}

	@Override
	public void handleDipose() {
		//NOP
	}

	@Override
	public void configure() {
		//NOP
	}
	
}
