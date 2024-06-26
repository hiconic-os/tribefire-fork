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
package com.braintribe.gwt.gme.constellation.client.resources;

import com.google.gwt.resources.client.CssResource;

public interface ConstellationCss extends CssResource {
	
	//External definitions (in the public constellation.css can be added here if they are important in the code).
	public static String EXTERNAL_HIGHLIGHT_BUTTON = "constellationHighlightButton";
	public static String EXTERNAL_TOOL_BAR_SELECTED = "constellationToolBarSelected";
	
	String bannerImage();
	String centeredText();
	String explorerConstellationCenterBackground();
	String greyBorder();
	String graySmallText();
	String mandatoryLabel();
	String propertyNameLabel();
	String queryConstellationNorth();
	String separatorMargin();
	String toolBarElement();
	String toolBarElementImage();
	String toolBarElementText();
	String toolBarParentStyle();
	String toolBarStyle();
	String toolTip();

}
