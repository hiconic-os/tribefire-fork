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
package com.braintribe.gwt.genericmodelgxtsupport.client.field;

import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.theme.base.client.listview.ListViewDefaultAppearance;

/**
 * Extension of the {@link ListViewDefaultAppearance} for exporting a new class in the items.
 * @author michel.docouto
 */
public class ClickableComboBoxListViewAppearance<M> extends ListViewDefaultAppearance<M> {

	@Override
	public void renderItem(SafeHtmlBuilder sb, SafeHtml content) {
		sb.appendHtmlConstant("<div class='" + style.item() + " gmeComboItem'>");
		sb.append(content);
		sb.appendHtmlConstant("</div>");
	}
	
}
