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

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.text.shared.SafeHtmlRenderer;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;

public class ClickableComboBoxCell<T> extends ComboBoxCell<T> {
	
	private TriggerClickListener triggerClickListener;

	public ClickableComboBoxCell(ListStore<T> store, LabelProvider<? super T> labelProvider, SafeHtmlRenderer<T> renderer) {
		super(store, labelProvider, renderer);
	}
	
	public void setTriggerClickListener(TriggerClickListener triggerClickListener) {
		this.triggerClickListener = triggerClickListener;
	}

	@Override
	public void onTriggerClick(Context context, XElement parent, NativeEvent event, T value, ValueUpdater<T> updater) {
		if (triggerClickListener == null || !triggerClickListener.onTriggerClicked())
			super.onTriggerClick(context, parent, event, value, updater);
	}

	/*
	 * Overriding this so the edition is finished if the user selects something (of course, only if the selection is valid).
	 */
	@Override
	protected void onViewClick(XElement parent, NativeEvent event, boolean focus, boolean takeSelected) {
		super.onViewClick(parent, event, focus, takeSelected);
		
		Element elem = getListView().findElement((Element) event.getEventTarget().cast());
		if (elem != null || takeSelected)
			Scheduler.get().scheduleDeferred(() -> triggerBlur(lastContext, lastParent, lastValue, lastValueUpdater));
	}
	
	@FunctionalInterface
	public interface TriggerClickListener {
		/**
		 * Fired when the trigger is clicked. If true is returned by this, then the event is cancelled and the default trigger click is not handled.
		 */
		public boolean onTriggerClicked();
	}

}
