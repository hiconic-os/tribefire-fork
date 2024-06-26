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
package com.braintribe.gwt.genericmodelgxtsupport.client.field.color;

import com.braintribe.gwt.genericmodelgxtsupport.client.LocalizedText;
import com.braintribe.gwt.gmview.client.EntityFieldDialog;
import com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client.ClosableWindow;
import com.braintribe.gwt.logging.client.ErrorDialog;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.processing.session.api.persistence.PersistenceGmSession;
import com.braintribe.model.style.Color;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

public class ColorPickerWindow extends ClosableWindow implements EntityFieldDialog<Color> {
	
	private Color color;
	private ColorPicker colorPicker;
	private com.braintribe.gwt.genericmodelgxtsupport.client.field.color.Color colorFromPicker;
	
	public ColorPickerWindow() {
		setHeading(LocalizedText.INSTANCE.chooseColor());
		setSize("403px", "328px");
		setResizable(false);
		setClosable(false);
		setModal(true);
		
		colorPicker = new ColorPicker();
		
		final TextButton cancelButton = new TextButton(LocalizedText.INSTANCE.cancel());
		final TextButton okButton = new TextButton(LocalizedText.INSTANCE.ok());
		
		SelectHandler selectHandler = event -> {
			if (event.getSource() == cancelButton) {
				colorFromPicker = null;
				ColorPickerWindow.super.hide();
				return;
			}
			
			colorFromPicker = new com.braintribe.gwt.genericmodelgxtsupport.client.field.color.Color();
			try {
				colorFromPicker.setRGB(colorPicker.getRGBColor());
			} catch (Exception e) {
				ErrorDialog.show("Error while setting color value.", e);
				e.printStackTrace();
			}
			ColorPickerWindow.super.hide();
		};
		
		okButton.addSelectHandler(selectHandler);
		cancelButton.addSelectHandler(selectHandler);
		
		add(colorPicker);
		addButton(okButton);
		addButton(cancelButton);
	}
	
	@Override
	public void configureGmSession(PersistenceGmSession gmSession) {
		//NOP
	}
	
	@Override
	public void hide() {
		colorFromPicker = null;
		super.hide();
	}
	
	@Override
	public void setEntityValue(Color color) {
		this.color = color;
		if (color.getRed() != null && color.getGreen() != null && color.getBlue() != null) {
			try {
				colorPicker.setRGB(color.getRed(), color.getGreen(), color.getBlue());
			} catch (Exception e) {
				ErrorDialog.show("Error while setting color value.", e);
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void performManipulations() {
		getManipulations();
	}
	
	public Color getColor() {
		performManipulations();
		return this.color;
	}
	
	private Manipulation getManipulations() {
		if (this.color != null && this.colorFromPicker != null) {
			this.color.setRed(this.colorFromPicker.getRed());
			this.color.setGreen(this.colorFromPicker.getGreen());
			this.color.setBlue(this.colorFromPicker.getBlue());
		}
		return null;
	}
	
	@Override
	public boolean hasChanges() {
		if (this.colorFromPicker != null && (this.color.getRed() == null || this.color.getRed() != this.colorFromPicker.getRed() || this.color.getGreen() == null
				|| this.color.getGreen() != this.colorFromPicker.getGreen() || this.color.getBlue() == null || this.color.getBlue() != this.colorFromPicker.getBlue())) {
			return true;
		}
		return false;
	}

	@Override
	public void setIsFreeInstantiation(Boolean isFreeInstantiation) {
		// NOP		
	}

}
