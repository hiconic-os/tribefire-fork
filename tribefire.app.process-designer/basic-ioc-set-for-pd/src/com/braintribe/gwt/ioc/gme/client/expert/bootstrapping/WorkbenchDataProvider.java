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
package com.braintribe.gwt.ioc.gme.client.expert.bootstrapping;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gmview.client.ModelEnvironmentSetListener;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.gwt.utils.client.FastMap;
import com.braintribe.model.accessapi.ModelEnvironment;
import com.braintribe.model.processing.session.api.persistence.ModelEnvironmentDrivenGmSession;
import com.braintribe.model.workbench.KnownWorkenchPerspective;
import com.braintribe.model.workbench.WorkbenchPerspective;

import com.google.gwt.core.client.Scheduler;

/**
 * Provider responsible for providing a {@link Future} of a {@link WorkbenchPerspective} for the given {@link KnownWorkenchPerspective}.
 * @author michel.docouto
 *
 */
public class WorkbenchDataProvider implements Function<KnownWorkenchPerspective, Future<WorkbenchPerspective>>, ModelEnvironmentSetListener {
	
	private final Map<String, Future<WorkbenchPerspective>> workbenchPerspectivesMap = new FastMap<>();
	private ModelEnvironmentDrivenGmSession gmSession;
	
	/**
	 * Configures the required {@link ModelEnvironmentDrivenGmSession} used for taking the {@link ModelEnvironment}.
	 */
	@Required
	public void setGmSession(ModelEnvironmentDrivenGmSession gmSession) {
		this.gmSession = gmSession;
	}

	@Override
	public Future<WorkbenchPerspective> apply(KnownWorkenchPerspective knownWorkbenchPerspective) {
		String perspectiveString = knownWorkbenchPerspective.toString();
		Future<WorkbenchPerspective> future = workbenchPerspectivesMap.get(perspectiveString);
		if (future != null)
			return future;
		
		future = new Future<>();
		workbenchPerspectivesMap.put(perspectiveString, future);
		loadPerspective(knownWorkbenchPerspective, future);
		return future;
	}
	
	@Override
	public void onModelEnvironmentSet() {
		workbenchPerspectivesMap.clear();
	}
	
	private void loadPerspective(KnownWorkenchPerspective knownWorkbenchPerspective, Future<WorkbenchPerspective> future) {
		ModelEnvironment modelEnvironment = gmSession.getModelEnvironment();
		List<WorkbenchPerspective> perspectives = modelEnvironment.getPerspectives();
		for (WorkbenchPerspective perspective : perspectives) {
			if (knownWorkbenchPerspective.toString().equals(perspective.getName())) {
				returnDeferred(perspective, future);
				return;
			}
		}
		
		returnDeferred(null, future);
	}
	
	private void returnDeferred(WorkbenchPerspective perspective, Future<WorkbenchPerspective> future) {
		Scheduler.get().scheduleDeferred(() -> future.onSuccess(perspective));
	}

}