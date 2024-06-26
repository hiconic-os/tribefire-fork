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
package com.braintribe.gwt.metadataeditor.client.view;

import com.braintribe.gwt.gme.propertypanel.client.PropertyPanelGroupingViewAppearance;
import com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client.ExtendedGroupingView;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NodeList;

public class MetaDataEditorResolutionGroupView extends ExtendedGroupingView<MetaDataEditorResolutionModel> {

	public MetaDataEditorResolutionGroupView() {
		super(new PropertyPanelGroupingViewAppearance());
	}
	
	public void collapseGroup(String groupToCollapse) {
		NodeList<Element> groups = getGroups();
		for (int i = 0, len = groups.getLength(); i < len; i++) {
			com.google.gwt.dom.client.Element group = groups.getItem(i);
			NodeList<Element> divs = group.getElementsByTagName("div");
			for (int j = 0; j < divs.getLength(); j++) {
				Element div = divs.getItem(j);
				if ("propertyGroupRuler".equals(div.getClassName())) {
					String groupName = div.getInnerText();
					if (groupToCollapse.equals(groupName)) {
						toggleGroup(group, false);
						return;
					}
					break;
				}
			}
		}
	}
	
}
