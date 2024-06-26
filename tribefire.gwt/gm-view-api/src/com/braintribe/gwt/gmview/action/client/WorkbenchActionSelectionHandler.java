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

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.util.client.GMEMetadataUtil;
import com.braintribe.model.meta.data.prompt.Priority;
import com.braintribe.model.template.Template;
import com.braintribe.model.workbench.TemplateBasedAction;

/**
 * Handler that will deal with showing a view for selecting between multiple {@link TemplateBasedAction}s.
 * @author michel.docouto
 *
 */
public interface WorkbenchActionSelectionHandler {
	
	public static Comparator<TemplateBasedAction> priorityComparator = (o1, o2) -> {
		int priorityComparison;
		
		Double o1Priority = getPriority(o1);
		Double o2Priority = getPriority(o2);
		
		if (o1Priority == null && o2Priority == null)
			priorityComparison = 0;
		else if (o1Priority == null && o2Priority != null)
			priorityComparison = -1;
		else if (o1Priority != null && o2Priority == null)
			priorityComparison = 1;
		else
			priorityComparison = o2Priority.compareTo(o1Priority);
		
		return priorityComparison;
	};
	
	public static Double getPriority(TemplateBasedAction action) {
		Template template = action.getTemplate();
		if (template == null)
			return null;
		
		Priority priority = GMEMetadataUtil.getTemplateMetaData(template, Priority.T, null);
		return priority == null ? null : priority.getPriority();
	}
	
	public void handleActionSelection(List<TemplateBasedAction> actions, Future<TemplateBasedAction> future);
	
	/**
	 * Checks the given list of actions, and returns the one with the most priority (if a single one is the most prior one).
	 */
	public default TemplateBasedAction getPriorityAction(List<TemplateBasedAction> actions) {
		if (actions.size() < 2)
			return null;
		else if (actions.size() == 1)
			return actions.get(0);
		
		Collections.sort(actions, priorityComparator);
		
		TemplateBasedAction firstAction = actions.get(0);
		TemplateBasedAction secondAction = actions.get(1);
		
		Double firstPriority = getPriority(firstAction);
		Double secondPriority = getPriority(secondAction);
		
		return firstPriority == null ? null : (firstPriority.equals(secondPriority) ? null : firstAction);
	}

}
