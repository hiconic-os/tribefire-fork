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
package com.braintribe.gwt.gmview.action.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface GmViewActionResources extends ClientBundle {
	
	public static final GmViewActionResources INSTANCE = GWT.create(GmViewActionResources.class);
	
	@Source ("quickAccess.gss")
	public QuickAccessCss css();
	
	@Source ("New_16x16.png")
	public ImageResource add();
	@Source ("New_32x32.png")
	public ImageResource addBig();
	@Source ("Back_16x16.png")
	public ImageResource back();
	@Source ("Cancel_16x16.png")
	public ImageResource cancel();
	@Source ("Assign_16x16.png")
	public ImageResource changeInstance();
	@Source ("Assign_32x32.png")
	public ImageResource changeInstanceBig();
	@Source ("Remove_16x16.png")
	public ImageResource clearCollection();
	@Source ("Remove_32x32.png")
	public ImageResource clearCollectionBig();
	@Source ("Clipboard_16x16.png")
	public ImageResource clipboard();
	@Source ("Clipboard_32x32.png")
	public ImageResource clipboardBig();
	@Source ("Close_16x16.png")
	public ImageResource close();
	@Source ("Delete_16x16.png")
	public ImageResource delete();
	@Source ("Delete_32x32.png")
	public ImageResource deleteBig();
	@Source ("Download_16x16.png")
	public ImageResource download();
	@Source ("Download_32x32.png")
	public ImageResource downloadBig();
	@Source ("Edit_16x16.png")
	public ImageResource edit();
	@Source ("Edit_32x32.png")
	public ImageResource editBig();
	@Source ("Front_16x16.png")
	public ImageResource front();
	@Source ("Open_16x16.png")
	public ImageResource open();
	@Source ("Open_32x32.png")
	public ImageResource openBig();
	@Source ("More_16x16.png")
	public ImageResource defaultActionIconSmall();
	//public ImageResource defaultActionIconMedium();
	@Source ("More_32x32.png")
	public ImageResource defaultActionIconLarge();
	@Source ("SwitchTo_16x16.png")
	public ImageResource globe();
	@Source ("SwitchTo_32x32.png")
	public ImageResource globeBig();
	public ImageResource list();
	public ImageResource map();
	@Source ("New_16x16.png")
	public ImageResource newInstance();
	@Source ("New_32x32.png")
	public ImageResource newInstanceBig();
	@Source ("Remove_16x16.png")
	public ImageResource nullAction();
	@Source ("Remove_32x32.png")
	public ImageResource nullActionBig();
	@Source ("OK_16x16.png")
	public ImageResource ok();
	@Source ("OK_32x32.png")
	public ImageResource okBig();
	@Source ("Magnifier_16x16.png")
	public ImageResource query();
	@Source ("Remove_16x16.png")
	public ImageResource remove();
	@Source ("Remove_32x32.png")
	public ImageResource removeBig();
	@Source ("Refresh_16x16.png")
	public ImageResource refresh();
	@Source ("Refresh_32x32.png")
	public ImageResource refreshBig();
	@Source ("Simple_16x16.png")
	public ImageResource simple();
	public ImageResource set();
	@Source ("View_16x16.png")
	public ImageResource view();
	@Source ("View_32x32.png")
	public ImageResource viewBig();

	@Source("Multiline_16x16.png")
	public ImageResource multiLine();

}
