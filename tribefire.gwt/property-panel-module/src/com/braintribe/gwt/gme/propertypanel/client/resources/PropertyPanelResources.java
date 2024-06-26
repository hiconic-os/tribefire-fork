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
package com.braintribe.gwt.gme.propertypanel.client.resources;

import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.ui.SVGResource.Validated;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource.Import;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.theme.base.client.grid.RowExpanderDefaultAppearance.RowExpanderStyle;

public interface PropertyPanelResources extends ClientBundle {
	
	public static final PropertyPanelResources INSTANCE = GWT.create(PropertyPanelResources.class);
	
	@Import({RowExpanderStyle.class})
	@Source ("propertyPanel.gss")
	public PropertyPanelCss css();
	
	@Source ("Apply_16x16.png")
	public ImageResource apply();
	@Source ("Back_16x16.png")
	public ImageResource back();
	ImageResource bullet();
	@Source ("Cancel_16x16.png")
	public ImageResource cancel();
	ImageResource changeExisting();
	ImageResource checked();
	ImageResource checkNull();
	ImageResource clear();
	ImageResource clearString();
	ImageResource collapsed();
	ImageResource collapsedArrow();
	ImageResource defaultIcon();
	ImageResource dropdown();
	ImageResource expanded();
	ImageResource expandedArrow();
	@Source("Front_16x16.png")
	public ImageResource front();
	@Source("Add_16x16.png")
	public ImageResource instantiate();
	ImageResource loading();
	@Source ("locked-32.png")
	ImageResource lock();
	ImageResource nullIcon();
	@Source("Open_16x16.png")
	public ImageResource open();
	@Source("Run_16x16.png")
	public ImageResource run();
	ImageResource smallarrow();
	ImageResource unchecked();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/exclamation.svg") @Validated(validated = false)
	SVGResource exclamation();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/info.svg") @Validated(validated = false)
	SVGResource info();
	

}
