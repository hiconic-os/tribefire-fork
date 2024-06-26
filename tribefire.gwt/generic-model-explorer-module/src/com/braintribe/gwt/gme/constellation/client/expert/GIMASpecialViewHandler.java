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
package com.braintribe.gwt.gme.constellation.client.expert;

import java.util.function.BiConsumer;

import com.braintribe.gwt.gmview.client.EditEntityActionListener;
import com.braintribe.model.generic.path.ModelPath;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.user.client.ui.Widget;

/**
 * Handler responsible for firing the {@link EditEntityActionListener} for handling opening a GIMA.
 * @author michel.docouto
 *
 */
public class GIMASpecialViewHandler implements BiConsumer<Widget, ModelPath> {

	@Override
	public void accept(Widget currentView, ModelPath modelPath) {
		Scheduler.get().scheduleDeferred(() -> {
			EditEntityActionListener listener = getEditEntityActionListener(currentView);
			if (listener != null)
				listener.onEditEntity(modelPath);
		});
	}
	
	private EditEntityActionListener getEditEntityActionListener(Widget widget) {
		if (widget instanceof EditEntityActionListener)
			return (EditEntityActionListener) widget;
		else if (widget.getParent() != null)
			return getEditEntityActionListener(widget.getParent());
		
		return null;
	}

}
