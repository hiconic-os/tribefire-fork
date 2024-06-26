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
package com.braintribe.gwt.gmview.ddsarequest.client.confirmation;

import java.util.List;

import com.braintribe.gwt.gmview.ddsarequest.client.LocalizedText;
import com.braintribe.model.extensiondeployment.meta.ConfirmationMouseClick;
import com.braintribe.model.meta.data.constraint.Confirmation;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.event.SelectEvent.SelectHandler;

/**
 * Extension of the MessageBox for handling {@link Confirmation}.
 * @author michel.docouto
 *
 */
public class ConfirmationMessageBox extends MessageBox {

	private boolean altKeyPressed;
	private boolean shiftKeyPressed;
	private boolean ctrlKeyPressed;
	private TextButton buttonPressed;
	private String okDisplay;
	private String cancelDisplay;
	private ConfirmationMouseClick mouseClick;
	private SelectHandler handler = event -> onButtonPressed((TextButton) event.getSource());
	
	public ConfirmationMessageBox(String messageText, String okDisplay, String cancelDisplay, ConfirmationMouseClick mouseClick) {
		super(LocalizedText.INSTANCE.confirmation(), messageText);
		this.okDisplay = okDisplay;
		this.cancelDisplay = cancelDisplay;
		this.mouseClick = mouseClick;
	}
	
	@Override
	protected String getText(PredefinedButton button) {
		if (PredefinedButton.OK.equals(button))
			return okDisplay == null ? LocalizedText.INSTANCE.ok() : okDisplay;
			
		return cancelDisplay == null ? LocalizedText.INSTANCE.cancel() : cancelDisplay;
	}
	
	//Overriding to check click event on the button cell, this is the last place where the NativeEvent is seen, and I can check the keys
	@Override
	protected void createButtons() {
		if (mouseClick == null || mouseClick.equals(ConfirmationMouseClick.none)) {
			super.createButtons();
			return;
		}
		
		Widget focusWidget = getFocusWidget();
		boolean focus = focusWidget == null || (getButtonBar().getWidgetIndex(focusWidget) != -1);
		getButtonBar().clear();

		List<PredefinedButton> buttons = getPredefinedButtons();
		for (int i = 0; i < buttons.size(); i++) {
			PredefinedButton b = buttons.get(i);
			TextButton tb = new TextButton(new TextButtonCell() {
				@Override
				protected void onClick(Context context, XElement p, String value, NativeEvent event, ValueUpdater<String> valueUpdater) {
					shiftKeyPressed = event.getShiftKey();
					ctrlKeyPressed = event.getCtrlKey();
					altKeyPressed = event.getAltKey();
					super.onClick(context, p, value, event, valueUpdater);
				}
			}, getText(b));
			tb.setItemId(b.name());
			tb.addSelectHandler(handler);
			if (i == 0 && focus)
				setFocusWidget(tb);
			addButton(tb);
		}
	}
	
	@Override
	protected void onButtonPressed(TextButton textButton) {
		buttonPressed = textButton;
		super.onButtonPressed(textButton);
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public void hide() {
		if (mouseClick == null || mouseClick.equals(ConfirmationMouseClick.none)) {
			super.hide();
			return;
		}
		
		PredefinedButton predefinedButton = buttonPressed == null ? null : getPredefinedButton(buttonPressed);
		if (predefinedButton == null)
			return;
		
		if (predefinedButton.equals(PredefinedButton.OK)) {
			switch (mouseClick) {
				case alt:
					if (!altKeyPressed)
						return;
					break;
				case ctrl:
					if (!ctrlKeyPressed)
						return;
					break;
				case shift:
					if (!shiftKeyPressed)
						return;
					break;
			}
		}
		
		super.hide();
	}

}
