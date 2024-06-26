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
package com.braintribe.gwt.gme.assemblypanel.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface AssemblyPanelResources extends ClientBundle {
	
	public static final AssemblyPanelResources INSTANCE = GWT.create(AssemblyPanelResources.class);
	
	@Source ("assemblyPanel.gss")
	public AssemblyPanelCss css();
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/blackMenu2.png")
	public ImageResource blackMenu();

	@Source ("com/braintribe/gwt/gxt/gxtresources/images/changeExisting.png")
	public ImageResource changeExisting();

	@Source ("com/braintribe/gwt/gxt/gxtresources/images/checked.png")
	public ImageResource checked();

	@Source ("com/braintribe/gwt/gxt/gxtresources/images/checkNull.png")
	public ImageResource checkNull();

	@Source ("com/braintribe/gwt/gxt/gxtresources/images/clear.gif")
	public ImageResource clear();
	
	@Source ("com/braintribe/gwt/gxt/gxtresources/images/clearString.png")
	public ImageResource clearString();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/Clipboard_16x16.png")
	public ImageResource clipboard();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/Clipboard_32x32.png")
	public ImageResource clipboardBig();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/nullAction.png")	
	public ImageResource nullAction();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/nullIcon.png")		
	public ImageResource nullIcon();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/Open_16x16.png")
	public ImageResource open();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/radioUnchecked.png")	
	public ImageResource radio();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/radioChecked.png")		
	public ImageResource radioChecked();
	
	@Source("com/braintribe/gwt/gxt/gxtresources/images/smallarrow.png")		
	public ImageResource smallarrow();

	@Source("com/braintribe/gwt/gxt/gxtresources/images/unchecked.png")		
	public ImageResource unchecked();
}
