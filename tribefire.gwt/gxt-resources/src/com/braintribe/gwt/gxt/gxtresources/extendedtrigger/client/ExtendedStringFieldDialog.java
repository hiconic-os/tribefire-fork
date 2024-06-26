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
package com.braintribe.gwt.gxt.gxtresources.extendedtrigger.client;

import com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client.ClosableWindow;
import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;
import com.sencha.gxt.widget.core.client.event.ShowEvent;
import com.sencha.gxt.widget.core.client.event.ShowEvent.ShowHandler;
import com.sencha.gxt.widget.core.client.form.TextArea;

/**
 * Dialog to be displayed when using the {@link ExtendedStringField}
 * @author michel.docouto
 *
 */
public class ExtendedStringFieldDialog extends ClosableWindow {
	
	private TextArea textArea;
	private boolean applyChanges = false;
	
	public ExtendedStringFieldDialog() {
		this.setSize("400px", "350px");
		this.setHeading(LocalizedText.INSTANCE.multiLineEditor());
		this.setModal(true);
		//this.setOnEsc(false);
		
		this.addShowHandler(new ShowHandler() {
			@Override
			public void onShow(ShowEvent event) {
				applyChanges = false;
			}
		});
		
		textArea = new TextArea();
		this.add(textArea);
		this.addButton(prepareCloseButton());
	}
	
	private TextButton prepareCloseButton() {
		TextButton closeButton = new TextButton(LocalizedText.INSTANCE.apply());
		closeButton.setToolTip(LocalizedText.INSTANCE.applyDescription());
		closeButton.addSelectHandler(new SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				applyChanges = true;
				ExtendedStringFieldDialog.super.hide();
			}
		});
		
		return closeButton;
	}
	
	public String getString() {
		return textArea.getValue();
	}
	
	public void setString(String string) {
		textArea.setValue(string);
	}
	
	public boolean isApplyChanges() {
		return applyChanges;
	}
	
	@Override
	public void hide() {
		applyChanges = false;
		
		super.hide();
	}

}
