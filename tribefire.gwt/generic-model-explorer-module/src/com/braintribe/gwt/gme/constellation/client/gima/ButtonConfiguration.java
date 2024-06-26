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
package com.braintribe.gwt.gme.constellation.client.gima;

 import com.sencha.gxt.widget.core.client.button.TextButton;

 /**
 * Configuration for {@link GIMAView} buttons position.
 * @author michel.docouto
 *
 */
public class ButtonConfiguration {

 	private TextButton button;
	private boolean afterCancel;

 	public ButtonConfiguration(TextButton button) {
		this.button = button;
	}

 	/**
	 * @param afterCancel - Configures whether the button should be placed after the cancel button. Defaults to false.
	 */
	public ButtonConfiguration(TextButton button, boolean afterCancel) {
		this.button = button;
		this.afterCancel = afterCancel;
	}

 	public TextButton getButton() {
		return button;
	}

 	public void setButton(TextButton button) {
		this.button = button;
	}

 	public boolean isAfterCancel() {
		return afterCancel;
	}

 	public void setAfterCancel(boolean afterCancel) {
		this.afterCancel = afterCancel;
	}
}