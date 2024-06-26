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

import java.util.function.Supplier;

import com.braintribe.gwt.gme.constellation.client.ExplorerConstellation;
import com.braintribe.gwt.gme.verticaltabpanel.client.VerticalTabElement;
import com.braintribe.gwt.gmview.client.GmContentView;
import com.braintribe.gwt.ioc.client.Required;

import com.google.gwt.user.client.ui.Widget;

/**
 * Provider used for getting the current {@link GmContentView} within the {@link ExplorerConstellation}
 * @author michel.docouto
 *
 */
public class CurrentContentViewProvider implements Supplier<GmContentView> {
	
	private ExplorerConstellation explorerConstellation;
	
	/**
	 * Configures the required {@link ExplorerConstellation} for checking which is the current view.
	 */
	@Required
	public void setExplorerConstellation(ExplorerConstellation explorerConstellation) {
		this.explorerConstellation = explorerConstellation;
	}

	@Override
	public GmContentView get() throws RuntimeException {
		VerticalTabElement element = explorerConstellation.getVerticalTabPanel().getSelectedElement();
		Widget widget = element.getWidget();
		
		if (widget instanceof GmContentView)
			return (GmContentView) widget;
		
		return null;
	}

}
