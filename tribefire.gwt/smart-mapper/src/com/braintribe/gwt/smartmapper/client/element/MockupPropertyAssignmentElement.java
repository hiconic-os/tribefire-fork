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
package com.braintribe.gwt.smartmapper.client.element;

import java.util.Collections;
import java.util.Set;

import com.braintribe.gwt.smartmapper.client.PropertyAssignmentInput;
import com.braintribe.gwt.smartmapper.client.experts.AbstractMappingElementsProvider;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class MockupPropertyAssignmentElement extends PropertyAssignmentElement{

	private Label placeHolder;
		
	@Override
	public void handleAdaption() {
		//NOP
	}

	@Override
	public void handleRender() {
		getPlaceHolder().setText("...");
	}

	@Override
	public Widget getWidget() {
		return getPlaceHolder();
	}

	@Override
	public void handleDisposal() {
		//NOP
	}

	@Override
	public void handleInitialisation() {
		//NOP
	}	
	
	@Override
	public void handleNoticeManipulation() {
		//NOP
	}
	
	@Override
	public void validate() {
		//NOP
	}
	
	@Override
	public Set<PropertyAssignmentInput> getInputElements() {
		return Collections.emptySet();
	}
	
	@Override
	public Set<AbstractMappingElementsProvider> getTypesOrPropertiesProviders() {
		return Collections.emptySet();
	}
	
	public Label getPlaceHolder() {
		if(placeHolder == null){
			placeHolder = new Label("...");
		}
		return placeHolder;
	}

}
