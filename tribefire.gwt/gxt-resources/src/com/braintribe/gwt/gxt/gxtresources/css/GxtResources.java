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
package com.braintribe.gwt.gxt.gxtresources.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource.Import;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.theme.base.client.grid.RowExpanderDefaultAppearance.RowExpanderStyle;

public interface GxtResources extends ClientBundle {
	
	public static final GxtResources INSTANCE = (GxtResources) GWT.create(GxtResources.class);
	
	@Import({RowExpanderStyle.class})
	@Source ("com/braintribe/gwt/gxt/gxtresources/css/gxtResource.gss")
	public GxtResourceCss css();
	
	//Image Source *****************************************************************************************************************************************
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/validation-tick-small.png")
	ImageResource apply();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/Multiline_16x16.png")
	ImageResource multiLine();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/edit.png")
	public ImageResource edit();
	

	/* SVGResource example
	@Source("com/braintribe/gwt/gxt/gxtresources/images/svg/arrow-drop-up-silver.svg") @Validated(validated = false)
	SVGResource  arrowDropUpSilverSvg();
	*/
	
}
