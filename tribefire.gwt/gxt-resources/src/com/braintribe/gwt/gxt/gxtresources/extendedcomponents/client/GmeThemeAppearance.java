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
package com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.sencha.gxt.core.client.resources.StyleInjectorHelper;
import com.sencha.gxt.core.client.resources.ThemeStyles.Styles;
import com.sencha.gxt.core.client.resources.ThemeStyles.ThemeAppearance;

/**
 * {@link ThemeAppearance} for extending GXT Blue theme.
 * @author michel.docouto
 *
 */
public class GmeThemeAppearance implements ThemeAppearance {

	static interface Bundle extends ClientBundle {

		@Source({ "com/sencha/gxt/theme/base/client/BaseTheme.gss", "com/sencha/gxt/theme/blue/client/BlueTheme.gss", "gmeTheme.gss" })
		GmeStyles css();
	}

	interface GmeStyles extends Styles {
		String borderColor();

		String borderColorLight();

		String backgroundColorLight();
	}

	private Bundle bundle;
	private GmeStyles style;

	@Override
	public Styles style() {
		return style;
	}

	public GmeThemeAppearance() {
	    this.bundle = GWT.create(Bundle.class);
	    this.style = bundle.css();

	    StyleInjectorHelper.ensureInjected(this.style, true);
	  }

	@Override
	public String borderColor() {
		return style.borderColor();
	}

	@Override
	public String borderColorLight() {
		return style.borderColorLight();
	}

	@Override
	public String backgroundColorLight() {
		return style.backgroundColorLight();
	}

}
