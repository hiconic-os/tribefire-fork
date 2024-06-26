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
package com.braintribe.gwt.gme.constellation.client.gima.field;

import com.braintribe.gwt.aceeditor.client.GmScriptEditorDialog;
import com.braintribe.gwt.gme.constellation.client.GIMADialog;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.event.HideEvent;
import com.sencha.gxt.widget.core.client.grid.editing.AbstractGridEditing;

public class GIMAScriptEditorDialog extends GmScriptEditorDialog {

	private GIMAScriptEditorView gimaView;
	private GIMADialog gimaDialog;
	
	@Override
	public void show() {
		AbstractGridEditing<?> parentGridEditing = getParentGridEditing();
		gimaDialog = parentGridEditing == null ? null : getParentGimaDialog(parentGridEditing.getEditableGrid());
		
		if (gimaDialog == null || !gimaDialog.isVisible()) {
			super.updateContainer();
			super.show();
			return;
		}

		//initSettings();
		gimaDialog.addEntityFieldView(this.getCaption(), prepareGIMAView(gimaDialog));
	}
	
	@Override
	public void applyChanges() {
		if (gimaDialog != null) {			
			applyChanges = true;
			fireEvent(new HideEvent());
		} else {
			super.applyChanges();
			super.hide();
		}
	}	
	
	@Override
	public void cancelChanges() {
		if (gimaDialog != null)
			cancelChanges = true;
		else
			super.cancelChanges();
	}

	private GIMAScriptEditorView prepareGIMAView(GIMADialog gimaDialog) {
		if (gimaView != null) {
			Widget widget = getView();
			if (widget.getParent() != gimaView)
				gimaView.add(getView());
			gimaView.configureGimaDialog(gimaDialog);
			return gimaView;
		}
		
		gimaView = new GIMAScriptEditorView(this, gimaDialog);
		return gimaView;
	}

	private GIMADialog getParentGimaDialog(Widget widget) {
		if (widget == null)
			return null;
		
		if (widget instanceof GIMADialog)
			return (GIMADialog) widget;
		
		return getParentGimaDialog(widget.getParent());
	}
		
	@Override
	public boolean canFireDialog() {
		AbstractGridEditing<?> parentGridEditing = getParentGridEditing();
		GIMADialog gimaDialog = parentGridEditing == null ? null : getParentGimaDialog(parentGridEditing.getEditableGrid());
		
		return (gimaDialog != null);
	}	
}
