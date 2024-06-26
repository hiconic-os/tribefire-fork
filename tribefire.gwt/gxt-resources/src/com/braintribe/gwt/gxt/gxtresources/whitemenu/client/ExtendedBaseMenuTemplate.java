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
package com.braintribe.gwt.gxt.gxtresources.whitemenu.client;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.theme.base.client.menu.MenuBaseAppearance.BaseMenuTemplate;
import com.sencha.gxt.theme.base.client.menu.MenuBaseAppearance.MenuStyle;

/**
 * Extension of the {@link BaseMenuTemplate} for exposing the menu class.
 * @author michel.docouto
 *
 */
public interface ExtendedBaseMenuTemplate extends BaseMenuTemplate {
	
	@Override
	@XTemplate(source = "Menu.html")
    SafeHtml template(MenuStyle style, String ignoreClass);

}
