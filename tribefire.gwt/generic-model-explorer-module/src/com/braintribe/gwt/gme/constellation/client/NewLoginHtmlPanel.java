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
package com.braintribe.gwt.gme.constellation.client;

import com.braintribe.gwt.gme.cssresources.client.FavIconCssLoader;
import com.braintribe.gwt.gme.cssresources.client.TitleCssLoader;
import com.braintribe.gwt.gme.uitheme.client.UiThemeCssLoader;
import com.braintribe.gwt.htmlpanel.client.HtmlPanel;

public class NewLoginHtmlPanel extends HtmlPanel{
	
	private String accessId;
    private UiThemeCssLoader uiThemeCssLoader;
    private FavIconCssLoader favIconCssLoader;
    private TitleCssLoader titleCssLoader;

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}
	
	public void setUiThemeLoader(UiThemeCssLoader uiThemeCssLoader) {
		this.uiThemeCssLoader = uiThemeCssLoader;
		if ((this.uiThemeCssLoader != null) && (this.accessId != null)) {
			this.uiThemeCssLoader.loadUiThemeCss(this.accessId);
		}
	}

	public void setFavIconLoader(FavIconCssLoader favIconCssLoader) {
		this.favIconCssLoader = favIconCssLoader;
		if ((this.favIconCssLoader != null) && (this.accessId != null)) {
			this.favIconCssLoader.loadFavIcon(this.accessId);
		}
	}
	
	public void setTitleLoader(TitleCssLoader titleCssLoader) {
		this.titleCssLoader = titleCssLoader;
		if ((this.titleCssLoader != null) && (this.accessId != null)) {
			this.titleCssLoader.loadTitle(this.accessId);
		}
	}		
}
