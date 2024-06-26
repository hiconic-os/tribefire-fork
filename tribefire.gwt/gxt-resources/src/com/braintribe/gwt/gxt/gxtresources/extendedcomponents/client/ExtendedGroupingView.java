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

import com.google.gwt.dom.client.Element;
import com.google.gwt.safehtml.shared.SafeHtml;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.core.client.GXT;
import com.sencha.gxt.widget.core.client.grid.GroupingView;

/**
 * Extends the Grouping view by adding a public group css class to the group and adding a style to the selection.
 * 
 * @author michel.docouto
 *
 */
public class ExtendedGroupingView<M> extends GroupingView<M> {
	
	public ExtendedGroupingView() {
		super();
	}
	
	public ExtendedGroupingView(GroupingViewAppearance groupAppearance) {
		super(groupAppearance);
	}

	@Override
	protected void onRowSelect(int rowIndex) {
		super.onRowSelect(rowIndex);
		Element row = getRow(rowIndex);
		if (row != null)
			row.addClassName("x-grid3-row-selected");
	}

	@Override
	protected void onRowDeselect(int rowIndex) {
		super.onRowDeselect(rowIndex);
		Element row = getRow(rowIndex);
		if (row != null)
			row.removeClassName("x-grid3-row-selected");
	}

	@Override
	protected void renderGroup(SafeHtmlBuilder buf, GroupingData<M> g, SafeHtml renderedRows) {
		String groupClass = getGroupingAppearance().style().group() + " gxtGroup";
		String bodyClass = "";
		if (g.isCollapsed()) {
			groupClass += " " + getGroupingAppearance().style().groupCollapsed();
			bodyClass = getGroupingAppearance().style().bodyCollapsed();
		}
		String headClass = getGroupingAppearance().style().groupHead();
		final SafeHtml groupHtml;
		String cellClasses = headClass + " " + styles.cell() + " " + states.cell();
		if (selectable) {
			groupHtml = (tpls.tr(groupClass,
					tpls.tdWrap(cm.getColumnCount(), cellClasses, styles.cellInner() + " " + states.cellInner(), renderGroupHeader(g))));
		} else {
			String innerCellClasses = styles.cellInner() + " " + states.cellInner() + " " + styles.noPadding();
			if (GXT.isIE())
				groupHtml = (tpls.tr(groupClass, tpls.tdWrapUnselectable(cm.getColumnCount(), cellClasses, innerCellClasses, renderGroupHeader(g))));
			else
				groupHtml = (tpls.tr(groupClass, tpls.tdWrap(cm.getColumnCount(), cellClasses, innerCellClasses, renderGroupHeader(g))));

		}
		buf.append(groupHtml);

		buf.append(tpls.tr(bodyClass, tpls.tdWrap(cm.getColumnCount(), "", "", renderedRows)));
	}

}
