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

import java.util.ArrayList;
import java.util.List;

import com.braintribe.gwt.gmview.action.client.ActionPerformanceContext;
import com.braintribe.gwt.gmview.action.client.ActionPerformanceListener;

public abstract class PerformanceListenerBoundModelAction extends ModelAction {

	private List<ActionPerformanceListener> actionPerformanceListeners = new ArrayList<ActionPerformanceListener>();
	
	public void addActionPeformanceListener(ActionPerformanceListener listener) {
		actionPerformanceListeners.add(listener);
	}
	
	public void removeActionPerformanceListener(ActionPerformanceListener listener) {
		actionPerformanceListeners.remove(listener);
	}
	
	public void setActionPerformanceListeners(List<ActionPerformanceListener> actionPerformanceListeners) {
		this.actionPerformanceListeners = actionPerformanceListeners;
	}
	
	public void fireOnBeforePerformAction(ActionPerformanceContext actionPerformanceContext) {
		for (ActionPerformanceListener listener : actionPerformanceListeners) {
			listener.onBeforePerformAction(actionPerformanceContext);
		}
	}
	
	public void fireOnAfterPerformAction(ActionPerformanceContext actionPerformanceContext) {
		for (ActionPerformanceListener listener : actionPerformanceListeners) {
			listener.onAfterPerformAction(actionPerformanceContext);
		}
	}

}
