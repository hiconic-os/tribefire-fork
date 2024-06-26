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
package com.braintribe.gwt.gme.templateevaluation.client.expert;

import java.util.List;

import com.braintribe.gwt.async.client.Future;
import com.braintribe.gwt.gme.constellation.client.TransientGmSession;
import com.braintribe.gwt.gmview.client.InstantiatedEntityListener;
import com.braintribe.gwt.gmview.client.InstantiationData;
import com.braintribe.gwt.ioc.client.Required;
import com.braintribe.model.folder.Folder;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.path.RootPathElement;
import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.session.GmSession;
import com.braintribe.model.processing.template.evaluation.TemplateEvaluationContext;
import com.braintribe.model.processing.template.evaluation.TemplateEvaluationException;
import com.braintribe.model.processing.workbench.action.api.WorkbenchActionContext;
import com.braintribe.model.workbench.TemplateInstantiationAction;

@SuppressWarnings("unusable-by-js")
public class TemplateInstantationActionHandler extends TemplateBasedActionHandler<TemplateInstantiationAction> {
	
	private InstantiatedEntityListener listener;
	private TransientGmSession transientGmSession;
	private WorkbenchActionContext<TemplateInstantiationAction> workbenchActionContext;
	
	@Required
	public void setTransientGmSession(TransientGmSession transientGmSession) {
		this.transientGmSession = transientGmSession;
	}
	
	public void setListener(InstantiatedEntityListener listener) {
		this.listener = listener;
	}
	
	@Override
	protected Future<TemplateEvaluationContext> getTemplateEvaluationContext(WorkbenchActionContext<TemplateInstantiationAction> workbenchActionContext) {
		boolean isTransient = workbenchActionContext.getWorkbenchAction().getTransient();
		if (!isTransient)
			return super.getTemplateEvaluationContext(workbenchActionContext);
		
		this.workbenchActionContext = workbenchActionContext;
		
		return super.getTemplateEvaluationContext(new TransientWorkbenchActionContext());
	}
	
	@Override
	public Future<Boolean> handleEvaluatedTemplate(Object evaluatedObject, WorkbenchActionContext<TemplateInstantiationAction> workbenchActionContext)
			throws TemplateEvaluationException {
		if (evaluatedObject == null)
			return new Future<>(true);
		
		Object value = evaluatedObject;
		GenericModelType type = GMF.getTypeReflection().getType(value);
		RootPathElement rootPathElement = new RootPathElement(type, value);
		if (listener != null) {
			boolean isTransient = workbenchActionContext.getWorkbenchAction().getTransient();
			listener.onEntityInstantiated(new InstantiationData(rootPathElement, !isTransient, true, null, false, isTransient));
		}
//			if (parentPanel instanceof ExplorerConstellation)
//				((ExplorerConstellation) parentPanel).onEntityInstantiated(rootPathElement);
//			else if (parentPanel instanceof SelectionConstellation && value instanceof GenericEntity)
//				((SelectionConstellation) parentPanel).fireEntitySelected((GenericEntity) value);
		return new Future<>(true);
	}
	
	@Override
	public Future<Boolean> checkIfPerformPossible(WorkbenchActionContext<TemplateInstantiationAction> workbenchActionContext) {
		return new Future<>(true);
	}
	
	@Override
	public boolean getCloneToPersistenceSession() {
		return true;
	}
	
	@Override
	public boolean getUseEvaluation() {
		return true;
	}

	@SuppressWarnings("unusable-by-js")
	private class TransientWorkbenchActionContext implements WorkbenchActionContext<TemplateInstantiationAction> {
		@Override
		public TemplateInstantiationAction getWorkbenchAction() {
			return workbenchActionContext.getWorkbenchAction();
		}
		
		@Override
		public Object getPanel() {
			return workbenchActionContext.getPanel();
		}
		
		@Override
		public List<ModelPath> getModelPaths() {
			return workbenchActionContext.getModelPaths();
		}
		
		@Override
		public GmSession getGmSession() {
			return transientGmSession;
		}
		
		@Override
		public Folder getFolder() {
			return workbenchActionContext.getFolder();
		}
	}

}
