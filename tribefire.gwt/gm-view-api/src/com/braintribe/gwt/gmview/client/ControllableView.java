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
package com.braintribe.gwt.gmview.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * Interface which must be implemented by dialogs which contain toolBars with buttons that can be all disabled.
 * This is used to have a better mask option, other than just masking the whole thing.
 *
 */
public interface ControllableView {
	
	public void enableComponents();
	
	public void disableComponents();
	
	public static ControllableView getParentControllableView(Widget widget) {
		if (widget instanceof ControllableView)
			return (ControllableView) widget;
		
		if (widget != null)
			return getParentControllableView(widget.getParent());
		
		return null;
	}

}
