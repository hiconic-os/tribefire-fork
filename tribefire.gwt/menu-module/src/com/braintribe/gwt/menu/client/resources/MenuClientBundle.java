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
package com.braintribe.gwt.menu.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface MenuClientBundle extends ClientBundle {
	
	public static final MenuClientBundle INSTANCE = GWT.create(MenuClientBundle.class);
	
	@Source ("Logout_16x16.png")
	public ImageResource logout();
	@Source ("bwLogout.png")
	public ImageResource logoutHover();
	@Source ("Settings_16x16.png")
	public ImageResource settings();
	@Source ("Settings_16x16.png")
	public ImageResource settingsHover();
	@Source ("Settings_32x32.png")
	public ImageResource settingsBig();
	@Source ("Refresh_16x16.png")
	public ImageResource refresh();
	@Source ("Refresh_32x32.png")
	public ImageResource refreshHover();
	@Source ("User_16x16.png")
	public ImageResource user();
	@Source ("User_16x16.png")
	public ImageResource userHover();
	
	@Source ("menuBarStyle.gss")
	public MenuCss css();

}
