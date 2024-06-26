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
package com.braintribe.gwt.genericmodelgxtsupport.client.field;

import com.braintribe.gwt.genericmodelgxtsupport.client.resources.GMGxtSupportResources;
import com.braintribe.gwt.gxt.gxtresources.extendedtrigger.client.ClickableTriggerField;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.widget.core.client.form.DateField;
import com.sencha.gxt.widget.core.client.form.TriggerField;

public class ClickableDateField extends DateField implements ClickableTriggerField {
	
	public ClickableDateField() {
		super(new ClickableDateCell());
	}
	
	@Override
	public ImageResource getImageResource() {
		return GMGxtSupportResources.INSTANCE.calendar();
	}

	@Override
	public TriggerField<?> getTriggerField() {
		return this;
	}
	
	@Override
	public void fireTriggerClick(NativeEvent event) {
		((ClickableDateCell) getCell()).onTriggerClick(createContext(), getElement(), event, getValue(), valueUpdater);
	}

}
