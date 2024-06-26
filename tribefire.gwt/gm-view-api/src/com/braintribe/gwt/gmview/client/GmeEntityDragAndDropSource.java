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
package com.braintribe.gwt.gmview.client;

import static com.braintribe.utils.lcd.CollectionTools2.isEmpty;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.braintribe.gwt.gmview.action.client.ActionPerformanceContext;
import com.braintribe.gwt.gmview.action.client.ActionPerformanceListener;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.value.Variable;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.workbench.TemplateBasedAction;
import com.google.gwt.user.client.ui.Widget;

/**
 * Interface for all {@link GenericEntity} DnD sources.
 * @author michel.couto
 *
 */
public interface GmeEntityDragAndDropSource {
	
	public List<TemplateBasedAction> getTemplateActions();
	public WorkbenchActionContext<TemplateBasedAction> getDragAndDropWorkbenchActionContext();
	public Widget getView();
	
	public default void markParentAsReloadPending(Widget widget) {
		ActionPerformanceListener listener = getActionPerformanceListener(widget);
		if (listener == null)
			return;
		
		ActionPerformanceContext context = new ActionPerformanceContext();
		context.setParentWidget(getView());
		listener.onAfterPerformAction(context);
	}
	
	public default ActionPerformanceListener getActionPerformanceListener(Widget widget) {
		if (widget == null)
			return null;
		
		if (widget instanceof ActionPerformanceListener)
			return (ActionPerformanceListener) widget;
		
		return getActionPerformanceListener(widget.getParent());
	}
	
	/** Checks if any of the given actions is valid for the given context. */
	public static boolean isAnyActionValid(List<TemplateBasedAction> actions, Object drop, GmeEntityDragAndDropSource source) {
		if (!isEmpty(actions) && drop instanceof GenericEntity) {
			WorkbenchActionContext<TemplateBasedAction> workbenchActionContext = source.getDragAndDropWorkbenchActionContext();
			GenericEntity _drop = (GenericEntity) drop;
			for (TemplateBasedAction templateAction : actions) {
				workbenchActionContext.setWorkbenchAction(templateAction);

				List<Variable> visibleVariables = TemplateSupport.getVisibleVariables(templateAction.getTemplate());
				Variable actionVariable = getValidVariable(visibleVariables, _drop);
				if (actionVariable != null)
					return true;
			}
		}

		return false;
	}

	/**
	 * Handles the drop event.
	 */
	public static Map<TemplateBasedAction, Variable> handleDrop(GenericEntity dropEntity, List<TemplateBasedAction> actions,
			WorkbenchActionContext<TemplateBasedAction> workbenchActionContext) {

		Map<TemplateBasedAction, Variable> availableActionsMap = new LinkedHashMap<>();
		if (actions == null || workbenchActionContext == null)
			return availableActionsMap;

		for (TemplateBasedAction templateAction : actions) {
			workbenchActionContext.setWorkbenchAction(templateAction);

			List<Variable> visibleVariables = TemplateSupport.getVisibleVariables(templateAction.getTemplate());

			Variable actionVariable = getValidVariable(visibleVariables, dropEntity);
			if (actionVariable != null)
				availableActionsMap.put(templateAction, actionVariable);
		}

		return availableActionsMap;
	}

	public static Variable getValidVariable(List<Variable> variables, GenericEntity entity) {
		for (Variable v : variables) {
			GenericModelType varType = GMF.getTypeReflection().findType(v.getTypeSignature());
			if (varType != null && varType.isEntity() && varType.isValueAssignable(entity))
				return v;
		}
		
		return null;
	}
	
}
