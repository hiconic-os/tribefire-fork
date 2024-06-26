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
package com.braintribe.gwt.ioc.gme.client.resources;

import org.vectomatic.dom.svg.ui.ExternalSVGResource.Validated;
import org.vectomatic.dom.svg.ui.SVGResource;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface CustomizationResources extends ClientBundle {
	
	public static final CustomizationResources INSTANCE = ((CustomizationResourcesFactory) GWT.create(CustomizationResourcesFactory.class)).getResources();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/Show-Log_16x16.png")
	public ImageResource log();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/bwUserManagement.png")
	public ImageResource userManagement();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/bwKey.png")
	public ImageResource key();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/bwMaintenanceTasks.png")
	public ImageResource maintenanceTasks();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/bwVerification.png")
	public ImageResource verification();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/clear.gif")
	public ImageResource clear();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/logo.png")
	public ImageResource logo();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/logoBlack.png")
	public ImageResource logoBlack();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/UploadOrange_32x32.png")
	public ImageResource upload();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/UploadOrange_16x16.png")
	public ImageResource uploadSmall();
	//public ImageResource close();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/user_empty.png")
	public ImageResource user();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/addOrange16x16.png")
	public ImageResource addOrange();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/addOrange32x32.png")
	public ImageResource addOrangeBig();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/More_32x32.png")
	public ImageResource defaultActionIcon();
	@Source("com/braintribe/gwt/gxt/gxtresources/images/Settings_16x16.png")
	public ImageResource settings();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/bubble.png")
	public ImageResource bubble();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/tf-logo.svg") @Validated(validated = false)
	SVGResource  tfLogo();	
	
	@Source ("customization.gss")
	public CustomizationCss css();
	
//	@Source ("customizationQueryModelEditor.css")
//	public QueryModelEditorCss customizationQueryModelEditorCss();

}
