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
package com.braintribe.gwt.metadataeditor.client.view;

import com.braintribe.gwt.gxt.gxtresources.text.LocalizedText;
//import com.braintribe.gwt.htmlpanel.client.HtmlPanel;
import com.braintribe.gwt.metadataeditor.client.resources.MetaDataEditorResources;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.Window;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer.VerticalLayoutData;
import com.sencha.gxt.widget.core.client.form.CheckBox;
import com.sencha.gxt.widget.core.client.form.TextField;

public class MetaDataEditorSearchDialog extends Window {
	private TextField textField;
	private CheckBox checkbox;
	private Boolean valueIsSet = true;
	private Boolean showCheckBox = true;
	private String checkboxText = null;
	
	public MetaDataEditorSearchDialog(Boolean showCheckBox, String checkboxText) {
		this.showCheckBox = showCheckBox;
		this.checkboxText = checkboxText;
		getMenuWindow();
	}

	public Window getMenuWindow() {
		this.valueIsSet = false;
		this.setClosable(false);
		this.setAutoHide(true);
		this.setShadow(true);
		this.setHeaderVisible(false);
		this.setResizable(false);
		this.setStyleName("searchMetaDataEditorDialog");
		//this.addStyleName(MetaDataEditorResources.INSTANCE.constellationCss().autoSize());
		this.setBorders(true);
		//this.setBodyStyleName("searchMetaDataEditorDialogBody");
		this.setBodyStyleName(MetaDataEditorResources.INSTANCE.constellationCss().autoSize());
		
		this.textField = new TextField();
		this.textField.setEmptyText(LocalizedText.INSTANCE.enterTextToFilter());
		this.textField.setHeight("auto");
		this.textField.setVisible(true);
		this.textField.setStyleName("searchMetaDataTextFieldClass");
		
		this.textField.addKeyDownHandler(new KeyDownHandler() {			
			@Override
			public void onKeyDown(KeyDownEvent event) {
				if (event.getNativeKeyCode() == KeyCodes.KEY_ENTER) {
					valueIsSet = true;
					MetaDataEditorSearchDialog.this.hide();					
				}				
			}
		});
		
   	 	this.checkbox = new CheckBox();
   	 	this.checkbox.setBorders(false);
   	 	this.checkbox.setWidth("auto");
   	 	this.checkbox.setHeight("auto");
   	 	this.checkbox.setStyleName("searchMetaDataCheckBoxClass");
   	 	if (this.checkboxText != null)   	 		
   	 		this.checkbox.setBoxLabel(this.checkboxText);	
   	 	else	
   	 		this.checkbox.setBoxLabel(LocalizedText.INSTANCE.declaredTypesOnly());
   	 	
   	 	/*
   	 	this.checkbox.addChangeHandler(new ChangeHandler() {		
   	 		@Override
			public void onChange(ChangeEvent event) {
			   fireEvent(event);
			}
   	 	});
   	 	*/
   	    	 
   	 	this.checkbox.addValueChangeHandler(new ValueChangeHandler<Boolean>() {
		    @Override
		    public void onValueChange(ValueChangeEvent<Boolean> event) {
		    	fireEvent(event);
		    }
   	 	});
   	 	
		VerticalLayoutContainer layoutContainer = new VerticalLayoutContainer();
		layoutContainer.setStyleName("searchMetaDataLayout");
		layoutContainer.addStyleName(MetaDataEditorResources.INSTANCE.constellationCss().autoHeight());
					
		layoutContainer.add(this.textField, new VerticalLayoutData(344, 32, new Margins(3,3,3,3)));
		layoutContainer.add(this.checkbox, new VerticalLayoutData(344, 32, new Margins(3,3,3,3)));
		
		layoutContainer.setWidth(350);
		layoutContainer.setHeight("auto");
			
		this.setSize("auto", "auto");
		this.add(layoutContainer);

   	 	this.checkbox.setVisible(this.showCheckBox);
		
		//this.setWidth(layoutContainer.getOffsetWidth());
		//this.setHeight(60);
		return this;
	}
	
	public void setSearchText(String text) {
		if (this.textField != null)
			textField.setText(text);
	}

	public String getSearchText() {
		if (this.textField != null) 	
			return textField.getText();
		else 
			return null;
	}
	
	public Boolean getDeclaredOnly() {
		if (checkbox != null)
			return checkbox.getValue();
		else 
			return false;
	}
	
	public void setDeclaredOnly(Boolean value) {
		if (checkbox != null)
			checkbox.setValue(value);		
	}
	
	public Boolean isValueSet() {
		return valueIsSet;
	}
	
	public void focusTextField() {
		if (this.textField != null)
			this.textField.focus();		
	}
	
	public void setShowCheckBox(Boolean showCheckBox) {
		this.showCheckBox = showCheckBox;
		if (this.checkbox != null) {
			this.checkbox.setVisible(this.showCheckBox);
			this.doLayout();
		}
	}
	
	public void setCheckBoxText(String checkBoxText) {
		this.checkboxText = checkBoxText;
		if (this.checkbox != null) {
			this.checkbox.setBoxLabel(this.checkboxText);
			this.doLayout();
		}		
	}
}
