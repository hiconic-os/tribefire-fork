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
package com.braintribe.gwt.metadataeditor.client.listeners;

import com.braintribe.gwt.gmview.client.GmSelectionListener;
import com.braintribe.gwt.gmview.client.GmSelectionSupport;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.event.RowClickEvent;
import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;

/**
 * Management of GmSelectionListener and its delegates.
 * 
 */
public class SelectionListeners extends AbstractListeners<GmSelectionListener> implements SelectionHandler<Widget>, RowClickHandler {

	private final GmSelectionSupport gmSelectionSupport;

	public SelectionListeners(GmSelectionSupport gmSelectionSupport) {
		super();
		this.gmSelectionSupport = gmSelectionSupport;
	}

	@Override
	protected void execute(GmSelectionListener sl) {
		sl.onSelectionChanged(this.gmSelectionSupport);
	}

	@Override
	public void onSelection(SelectionEvent<Widget> event) {
		fireListeners();
	}

	@Override
	public void onRowClick(RowClickEvent event) {
		fireListeners();
	}

}
