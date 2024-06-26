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

import java.util.List;

import com.braintribe.gwt.aceeditor.client.GmScriptEditorDialog;
import com.braintribe.gwt.gme.constellation.client.GIMADialog;
import com.braintribe.gwt.gme.constellation.client.LocalizedText;
import com.braintribe.gwt.gme.constellation.client.gima.ButtonConfiguration;
import com.braintribe.gwt.gme.constellation.client.resources.ConstellationResources;
import com.google.gwt.core.client.Scheduler;
import com.sencha.gxt.cell.core.client.ButtonCell.ButtonScale;
import com.sencha.gxt.cell.core.client.ButtonCell.IconAlign;
import com.sencha.gxt.widget.core.client.button.TextButton;

public class GIMAScriptEditorView extends GIMAEntityFieldView<GmScriptEditorDialog>{

	private TextButton mainButton;
	private GmScriptEditorDialog dialog;

	public GIMAScriptEditorView(GmScriptEditorDialog dialog, GIMADialog gimaDialog) {
		super(dialog, gimaDialog);
		this.dialog = dialog;
	}

	@Override
	public void handleCancel() {
		dialog.cancelChanges();
		//Scheduler.get().scheduleDeferred(() -> gimaDialog.handleHideOrBack(false, false));
	}
	
	private void handleBack() {
		Scheduler.get().scheduleDeferred(() -> {		
			dialog.applyChanges();
			gimaDialog.handleHideOrBack(false, false);
		});
	}
	
	@Override
	public TextButton getMainButton() {		
		if (mainButton != null)
			return mainButton;
		
		if (!dialog.isReadOnly()) {		
			mainButton = new TextButton(LocalizedText.INSTANCE.apply());
			mainButton.setToolTip(LocalizedText.INSTANCE.applyAllDescription());
			mainButton.addSelectHandler(event -> handleBack());
			mainButton.setIcon(ConstellationResources.INSTANCE.finish());
			mainButton.addStyleName(GIMADialog.GIMA_MAIN_BUTTON);
		} else {
			mainButton = new TextButton(LocalizedText.INSTANCE.back());
			mainButton.setToolTip(LocalizedText.INSTANCE.backTypeDescription());			
			mainButton.addSelectHandler(event -> handleCancel());
			mainButton.setIcon(ConstellationResources.INSTANCE.back());
			mainButton.addStyleName(GIMADialog.GIMA_MAIN_BUTTON);			
		}
		mainButton.setScale(ButtonScale.LARGE);
		mainButton.setIconAlign(IconAlign.TOP);
		
		return mainButton;
	}

	@Override
	public List<ButtonConfiguration> getAdditionalButtons() {
		return null;
	}
	
}
