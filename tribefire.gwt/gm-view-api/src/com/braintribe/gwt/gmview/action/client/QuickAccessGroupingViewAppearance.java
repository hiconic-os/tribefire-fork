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
package com.braintribe.gwt.gmview.action.client;

import com.braintribe.gwt.gxt.gxtresources.flatgroupingview.client.GroupingViewFlatAppearance.FlatGroupingViewStyle;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.CssResource.Import;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.sencha.gxt.core.client.XTemplates;
import com.sencha.gxt.theme.base.client.grid.GroupingViewDefaultAppearance;
import com.sencha.gxt.widget.core.client.grid.GridView.GridStateStyles;
import com.sencha.gxt.widget.core.client.grid.GroupingView.GroupingData;

public class QuickAccessGroupingViewAppearance extends GroupingViewDefaultAppearance {
	
	public interface QuickAccessHeaderTemplate extends XTemplates, GroupHeaderTemplate<Object> {
		@Override
		@XTemplate("<div class='propertyGroupRuler'>{groupInfo.value}</div>")
		public SafeHtml renderGroupHeader(GroupingData<Object> groupInfo);
	}
	
	private GroupHeaderTemplate<?> headerTemplate = GWT.create(QuickAccessHeaderTemplate.class);
	
	public interface QuickAccessFlatGroupingViewStyle extends FlatGroupingViewStyle {
		String quickAccessPanel();
	}
	
	public interface QuickAccessGroupingViewFlatResources extends GroupingViewResources {

		@Import(GridStateStyles.class)
		@Override
		@Source({"com/sencha/gxt/theme/base/client/grid/GroupingView.gss", "QuickAccessFlatGroupingView.gss"})
		QuickAccessFlatGroupingViewStyle style();
	}
	
	public QuickAccessGroupingViewAppearance() {
		super(GWT.<QuickAccessGroupingViewFlatResources>create(QuickAccessGroupingViewFlatResources.class));
		setHeaderTemplate(headerTemplate);
	}

}
