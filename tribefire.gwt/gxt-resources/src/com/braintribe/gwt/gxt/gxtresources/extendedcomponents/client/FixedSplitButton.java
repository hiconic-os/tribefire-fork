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
import com.sencha.gxt.widget.core.client.button.SplitButton;

/**
 * This implementation of the SplitButton is prepared for being disabled. It won't have the square border around it.
 * SplitButtons that need to be disabled, must use this implementation. The reason is, GXT's BlueStyles is not public.
 * Also, the click on the arrow area has been fixed. It is treated as a normal click if the menu is null.
 * @author michel.docouto
 *
 */
public class FixedSplitButton extends SplitButton {
	
	public FixedSplitButton() {
		super(new FixedSplitButtonCell());
		disabledStyle = ((WhiteButtonTableFrameResources) GWT.create(WhiteButtonTableFrameResources.class)).style().disabledStyle();
	}
	
	public FixedSplitButton(String text) {
		super(new FixedSplitButtonCell(), text);
		disabledStyle = ((WhiteButtonTableFrameResources) GWT.create(WhiteButtonTableFrameResources.class)).style().disabledStyle();
	}
	
	public FixedSplitButton(FixedSplitButtonCell cell) {
		super(cell);
		disabledStyle = ((WhiteButtonTableFrameResources) GWT.create(WhiteButtonTableFrameResources.class)).style().disabledStyle();
	}

}
