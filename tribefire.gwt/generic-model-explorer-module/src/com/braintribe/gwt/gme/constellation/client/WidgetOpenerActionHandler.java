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
package com.braintribe.gwt.gme.constellation.client;

import java.util.function.Supplier;

import com.braintribe.gwt.gmview.util.client.GMEIconUtil;
import com.braintribe.model.generic.i18n.LocalizedString;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionHandler;
import com.braintribe.model.workbench.WidgetOpenerAction;
import com.braintribe.utils.i18n.I18nTools;
import com.google.gwt.user.client.ui.Widget;

public class WidgetOpenerActionHandler implements WorkbenchActionHandler<WidgetOpenerAction> {

	@Override
	public void perform(WorkbenchActionContext<WidgetOpenerAction> workbenchActionContext) {
		ExplorerConstellation explorerConstellation = getParentPanel(workbenchActionContext.getPanel());
		if (explorerConstellation != null) {
			WidgetOpenerAction widgetOpenerAction = workbenchActionContext.getWorkbenchAction();
		
			Supplier<Widget> widgetProvider = (Supplier<Widget>) widgetOpenerAction.getWidgetProvider();
			explorerConstellation.maybeCreateVerticalTabElement(workbenchActionContext, getName(workbenchActionContext),
					widgetOpenerAction.getDescription(), widgetProvider, GMEIconUtil.getSmallIcon(workbenchActionContext), null, false);
		}
	}
	
	private String getName(WorkbenchActionContext<WidgetOpenerAction> context) {
		LocalizedString displayName = context.getWorkbenchAction().getDisplayName();
		if (displayName != null)
			return I18nTools.getLocalized(displayName);
		
		return context.getWorkbenchAction().getName();
	}
	
	public static ExplorerConstellation getParentPanel(Object panel) {
		if (panel instanceof ExplorerConstellation)
			return (ExplorerConstellation) panel;
		else if (panel instanceof Widget)
			return getParentPanel(((Widget) panel).getParent());
		
		return null;
	}

}
