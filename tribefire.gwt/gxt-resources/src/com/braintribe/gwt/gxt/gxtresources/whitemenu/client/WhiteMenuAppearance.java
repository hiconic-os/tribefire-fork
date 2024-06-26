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

import com.google.gwt.core.client.GWT;
import com.sencha.gxt.theme.blue.client.menu.BlueMenuAppearance;

public class WhiteMenuAppearance extends BlueMenuAppearance {
	
	public interface WhiteMenuStyle extends BlueMenuStyle {
		//NOP
    }
	
	public interface whiteMenuResources extends BlueMenuResources {
		
        @Override
        @Source({"com/sencha/gxt/theme/base/client/menu/Menu.gss", "com/sencha/gxt/theme/blue/client/menu/BlueMenu.gss", "WhiteMenu.gss"})
        WhiteMenuStyle style();
		
    }
	
	public WhiteMenuAppearance() {
		super(GWT.<whiteMenuResources>create(whiteMenuResources.class), GWT.<BaseMenuTemplate> create(ExtendedBaseMenuTemplate.class));
	}

}
