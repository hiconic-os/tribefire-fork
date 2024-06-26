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

import com.braintribe.gwt.gxt.gxtresources.whitebutton.client.WhiteButtonTableFrameResources;
import com.google.gwt.core.client.GWT;
import com.sencha.gxt.cell.core.client.TextButtonCell;
import com.sencha.gxt.widget.core.client.button.TextButton;

/**
 * This implementation of the TextButton is prepared for being disabled. It won't have the square border around it.
 * Buttons that need to be disabled, must use this implementation. The reason is, GXT's BlueStyles is not public.
 * @author michel.docouto
 *
 */
public class FixedTextButton extends TextButton {
	
	public FixedTextButton() {
		super();
		disabledStyle = ((WhiteButtonTableFrameResources) GWT.create(WhiteButtonTableFrameResources.class)).style().disabledStyle();
	}
	
	public FixedTextButton(String text) {
		super(text);
		disabledStyle = ((WhiteButtonTableFrameResources) GWT.create(WhiteButtonTableFrameResources.class)).style().disabledStyle();
	}
	
	public FixedTextButton(TextButtonCell cell, String text) {
		super(cell, text);
		disabledStyle = ((WhiteButtonTableFrameResources) GWT.create(WhiteButtonTableFrameResources.class)).style().disabledStyle();
	}

}