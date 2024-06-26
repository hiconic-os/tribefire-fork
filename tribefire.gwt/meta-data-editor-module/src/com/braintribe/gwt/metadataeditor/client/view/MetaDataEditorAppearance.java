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

import com.braintribe.gwt.gxt.gxtresources.extendedcomponents.client.ExtendedListViewDefaultResources;
import com.braintribe.gwt.metadataeditor.client.resources.MetaDataEditorResources;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.dom.XElement;
import com.sencha.gxt.theme.base.client.listview.ListViewCustomAppearance;
import com.sencha.gxt.theme.base.client.listview.ListViewDefaultAppearance.ListViewDefaultStyle;

public class MetaDataEditorAppearance extends ListViewCustomAppearance<MetaDataEditorModel> {

	protected static final ListViewDefaultStyle style = MetaDataEditorResources.INSTANCE.listViewDefaultStyle();

	public MetaDataEditorAppearance() {
		super("." + style.item(), style.over(), style.sel());
	}

	@Override
	public void render(SafeHtmlBuilder builder) {
		builder.appendHtmlConstant("<div class=\"" + style.view() + "\" x-type=\"mde-view\"></div>");
	}

	@Override
	public void renderItem(SafeHtmlBuilder builder, SafeHtml content) {
		builder.appendHtmlConstant("<div class=\"" + style.item() + " " + ExtendedListViewDefaultResources.GME_LIST_VIEW_ITEM + "\" x-type=\"mde-item\">");
		builder.append(content);
		builder.appendHtmlConstant("</div>");
	}
	
	@Override
	public void onSelect(XElement item, boolean select) {
		super.onSelect(item, select);
		item.setClassName(ExtendedListViewDefaultResources.GME_LIST_VIEW_SEL, select);
	}
}
