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
package com.braintribe.gwt.gxt.gxtresources.flatgroupingview.client;

import org.vectomatic.dom.svg.ui.SVGResource;
import org.vectomatic.dom.svg.ui.SVGResource.Validated;

import com.google.gwt.resources.client.CssResource.Import;
import com.sencha.gxt.theme.base.client.grid.GroupingViewDefaultAppearance;
import com.sencha.gxt.widget.core.client.grid.GridView.GridStateStyles;

public class GroupingViewFlatAppearance extends GroupingViewDefaultAppearance {
	
	public interface FlatGroupingViewStyle extends GroupingViewStyle {
		//NOP
	}
	
	public interface GroupingViewFlatResources extends GroupingViewResources {

		@Source ("com/braintribe/gwt/gxt/gxtresources/images/arrow-up.svg") @Validated(validated = false)
		SVGResource arrowUp();
		
		@Source ("com/braintribe/gwt/gxt/gxtresources/images/arrow-down.svg") @Validated(validated = false)
		SVGResource arrowDown();
		
		@Source ("com/braintribe/gwt/gxt/gxtresources/images/arrow-right.svg") @Validated(validated = false)
		SVGResource arrowRight();
		
		@Import(GridStateStyles.class)
		@Override
		@Source({"com/sencha/gxt/theme/base/client/grid/GroupingView.gss", "FlatGroupingView.gss"})
		FlatGroupingViewStyle style();
	}

}
