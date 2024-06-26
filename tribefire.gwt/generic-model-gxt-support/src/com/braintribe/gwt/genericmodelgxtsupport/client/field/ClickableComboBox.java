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
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.sencha.gxt.cell.core.client.LabelProviderSafeHtmlRenderer;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.widget.core.client.form.ComboBox;
import com.sencha.gxt.widget.core.client.form.TriggerField;

public class ClickableComboBox<T> extends ComboBox<T> implements ClickableTriggerField {
	
	public ClickableComboBox(ListStore<T> store, LabelProvider<? super T> labelProvider) {
		super(new ClickableComboBoxCell<>(store, labelProvider, new LabelProviderSafeHtmlRenderer<T>(labelProvider)));
		setHideTrigger(true);
	}
	
	public ClickableComboBox(ListStore<T> store, LabelProvider<? super T> labelProvider, SafeHtmlRenderer<T> safeHtmlRenderer) {
		super(new ClickableComboBoxCell<>(store, labelProvider, safeHtmlRenderer));
		setHideTrigger(true);
	}

	@Override
	public ImageResource getImageResource() {
		return GMGxtSupportResources.INSTANCE.dropDown();
	}

	@Override
	public TriggerField<?> getTriggerField() {
		return this;
	}
	
	@Override
	public void fireTriggerClick(NativeEvent event) {
		((ClickableComboBoxCell<T>) getCell()).onTriggerClick(createContext(), getElement(), event, getValue(), valueUpdater);
	}

}
