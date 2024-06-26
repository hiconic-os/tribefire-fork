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
package com.braintribe.gwt.gxt.gxtresources.whitecolumnheader.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.sencha.gxt.theme.base.client.grid.ColumnHeaderDefaultAppearance;

public class WhiteColumnHeaderAppearance extends ColumnHeaderDefaultAppearance {
	
	public interface WhiteColumnHeaderStyles extends DefaultColumnHeaderStyles {
		//NOP
    }
	
	public interface whiteColumnHeaderResources extends ColumnHeaderResources {
        @Source("com/braintribe/gwt/gxt/gxtresources/images/blackMenu.png")
        ImageResource blackMenu();		
		
        @Override
        @Source({"com/sencha/gxt/theme/base/client/grid/ColumnHeader.gss", "WhiteColumnHeader.gss"})
        WhiteColumnHeaderStyles style();
		
    }
	
	public WhiteColumnHeaderAppearance() {
		super(GWT.<whiteColumnHeaderResources>create(whiteColumnHeaderResources.class));
	}

}
