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
package com.braintribe.gwt.gme.propertypanel.client;

import com.braintribe.gwt.gxt.gxtresources.flatgroupingview.client.GroupingViewFlatAppearance.GroupingViewFlatResources;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.theme.base.client.grid.GroupingViewDefaultAppearance;
import com.sencha.gxt.widget.core.client.grid.GroupingView.GroupingData;

public class PropertyPanelGroupingViewAppearance extends GroupingViewDefaultAppearance {
	
	/*public interface PropertyPanelGroupingViewHeaderTemplate extends XTemplates, GroupHeaderTemplate<Object> {
		@Override
		@XTemplate("<div class='propertyGroupRuler'>{groupInfo.value}</div>")
		public SafeHtml renderGroupHeader(GroupingData<Object> groupInfo);
	}*/
	
	//private GroupHeaderTemplate<Object> headerTemplate = GWT.create(PropertyPanelGroupingViewHeaderTemplate.class);
	
	public PropertyPanelGroupingViewAppearance() {
		super(GWT.<GroupingViewFlatResources>create(GroupingViewFlatResources.class));
		setHeaderTemplate(new GroupHeaderTemplate<Object>() {
			@Override
			public SafeHtml renderGroupHeader(GroupingData<Object> groupInfo) {
				StringBuilder sb = new StringBuilder("<div class='propertyGroupRuler'>");
				sb.append((String) groupInfo.getValue());
				sb.append("</div>");
				return SafeHtmlUtils.fromTrustedString(sb.toString());
			}
		});
	}

}
