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
package com.braintribe.gwt.gxt.gxtresources.whitewindow.client;

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.theme.base.client.frame.NestedDivFrame;
import com.sencha.gxt.theme.base.client.panel.FramedPanelBaseAppearance;
import com.sencha.gxt.theme.base.client.widget.HeaderDefaultAppearance;
import com.sencha.gxt.theme.blue.client.window.BlueWindowAppearance.BlueHeaderResources;
import com.sencha.gxt.theme.blue.client.window.BlueWindowAppearance.BlueHeaderStyle;
import com.sencha.gxt.theme.blue.client.window.BlueWindowAppearance.BlueWindowDivFrameResources;
import com.sencha.gxt.theme.blue.client.window.BlueWindowAppearance.BlueWindowDivFrameStyle;
import com.sencha.gxt.theme.blue.client.window.BlueWindowAppearance.BlueWindowResources;
import com.sencha.gxt.theme.blue.client.window.BlueWindowAppearance.BlueWindowStyle;
import com.sencha.gxt.widget.core.client.Window.WindowAppearance;

public class WhiteWindowAppearance extends FramedPanelBaseAppearance implements WindowAppearance {
	
	public interface WhiteWindowDivFrameStyle extends BlueWindowDivFrameStyle {
		//NOP
	}
	
	public interface WhiteWindowDivFrameResources extends BlueWindowDivFrameResources {
		
		@Override
        @Source({"com/sencha/gxt/theme/base/client/frame/NestedDivFrame.gss", "com/sencha/gxt/theme/blue/client/window/BlueWindowDivFrame.gss", "WhiteWindowDivFrame.gss"})
        WhiteWindowDivFrameStyle style();
		
	}
	
	public interface WhiteHeaderResources extends BlueHeaderResources {
	    @Override
		@Source({"com/sencha/gxt/theme/blue/client/window/BlueWindowHeader.gss", "WhiteWindowHeader.gss"})
	    BlueHeaderStyle style();
	  }
	
	private BlueWindowStyle blueStyle;
	
	public WhiteWindowAppearance() {
		this((BlueWindowResources) GWT.create(BlueWindowResources.class));
	}

	public WhiteWindowAppearance(BlueWindowResources resources) {
		super(resources, GWT.<FramedPanelTemplate> create(FramedPanelTemplate.class), new NestedDivFrame(
				GWT.<WhiteWindowDivFrameResources> create(WhiteWindowDivFrameResources.class)));

		blueStyle = resources.style();
	}
	
	@Override
	public String ghostClass() {
		return blueStyle.ghost();
	}
	
	@Override
	public HeaderDefaultAppearance getHeaderAppearance() {
		return new HeaderDefaultAppearance(GWT.<WhiteHeaderResources> create(WhiteHeaderResources.class));
	}
	
}
