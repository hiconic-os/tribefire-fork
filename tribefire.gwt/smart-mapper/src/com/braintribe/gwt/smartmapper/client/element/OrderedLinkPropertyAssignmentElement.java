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

import java.util.HashSet;
import java.util.Set;

import com.braintribe.gwt.smartmapper.client.PropertyAssignmentInput;
import com.braintribe.gwt.smartmapper.client.PropertyAssignmentPropertyInput;
import com.braintribe.gwt.smartmapper.client.experts.AbstractMappingElementsProvider;
import com.braintribe.gwt.smartmapper.client.experts.AbstractMappingElementsProvider.MappingElementKind;
import com.braintribe.gwt.smartmapper.client.experts.LinkPropertyElementsProvider;
import com.braintribe.gwt.smartmapper.client.experts.LinkPropertyElementsProvider.Mode;
import com.google.gwt.user.client.ui.FlowPanel;

public class OrderedLinkPropertyAssignmentElement extends LinkPropertyAssignmentElement{

	FlowPanel indexWrapper;
	
	PropertyAssignmentPropertyInput linkIndexInput;
	
	LinkPropertyElementsProvider linkIndexPropertyProvider = new LinkPropertyElementsProvider(MappingElementKind.properties, Mode.linkIndex);
	
	@Override
	public Set<PropertyAssignmentInput> getInputElements() {
		if(inputElements == null){
			inputElements = super.getInputElements();
			inputElements.add(getLinkIndexInput());
		}
		return inputElements;
	}
	
	@Override
	public Set<AbstractMappingElementsProvider> getTypesOrPropertiesProviders() {
		Set<AbstractMappingElementsProvider> providers = new HashSet<AbstractMappingElementsProvider>(super.getTypesOrPropertiesProviders());
		providers.add(linkIndexPropertyProvider);
		return providers;
	}
	
	@Override
	public FlowPanel getLinkPanel() {
		if(linkPanel == null){
			linkPanel = super.getLinkPanel();
			linkPanel.add(getLinkIndexInput());
//			linkPanel.add(PropertyAssignmentTypeInput.clear(getLinkIndexInput()));
		}
		return linkPanel;
	}
	
	public PropertyAssignmentPropertyInput getLinkIndexInput() {
		if(linkIndexInput == null){
			linkIndexInput = new PropertyAssignmentPropertyInput();
			linkIndexInput.setPropertyNameOfAssignment("linkIndex");
			linkIndexInput.getElement().setAttribute("placeholder", "linkIndex");
			linkIndexInput.setPropertiesProvider(linkIndexPropertyProvider);
		}
		return linkIndexInput;
	}

}
