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
package com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client;

import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.NativeEvent;
import com.sencha.gxt.cell.core.client.SplitButtonCell;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.widget.core.client.event.BeforeSelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent;

/**
 * Fixed a problem that the ArrowSelectEvent was fired even when the button had no menu configured.
 * @author michel.docouto
 *
 */
public class FixedSplitButtonCell extends SplitButtonCell {
	
	public FixedSplitButtonCell() {
		super();
	}
	
	public FixedSplitButtonCell(ButtonCellAppearance<String> appearance) {
		super(appearance);
	}
	
	@Override
	protected void onClick(Context context, XElement p, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
		if (menu != null)
			super.onClick(context, p, value, event, valueUpdater);
		else
			fixedOnClick(context, event);
	}
	
	protected void fixedOnClick(Context context, NativeEvent event) {
		event.preventDefault();

		if (!isDisableEvents() && fireCancellableEvent(context, new BeforeSelectEvent(context)))
			fireEvent(context, new SelectEvent(context));
	}

}
