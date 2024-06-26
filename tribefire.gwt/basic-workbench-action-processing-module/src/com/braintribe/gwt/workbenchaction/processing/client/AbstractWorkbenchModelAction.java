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
package com.braintribe.gwt.workbenchaction.processing.client;

import java.util.List;
import java.util.function.Supplier;

import com.braintribe.gwt.gmview.client.ModelAction;
import com.braintribe.model.generic.path.ModelPath;
import com.braintribe.model.generic.pr.criteria.matching.StandardMatcher;
import com.braintribe.model.generic.value.Variable;
import com.braintribe.model.processing.vde.evaluator.VDE;
import com.braintribe.model.processing.vde.evaluator.api.VdeEvaluationMode;
import com.braintribe.model.processing.vde.evaluator.api.aspects.UserNameAspect;
import com.braintribe.model.processing.vde.evaluator.api.aspects.VariableProviderAspect;
import com.braintribe.model.processing.workbench.action.api.WorkbenchModelAction;

public abstract class AbstractWorkbenchModelAction extends ModelAction implements WorkbenchModelAction {
	
	private StandardMatcher matcher;
	private Supplier<String> userNameProvider = ()-> null;
	
	public void setUserNameProvider(Supplier<String> userNameProvider) {
		this.userNameProvider = userNameProvider;
	}
	
	private StandardMatcher getMatcher() {
		if (matcher == null) {
			matcher = new StandardMatcher();
			matcher.setCheckOnlyProperties(false);
			matcher.setCriterion(getInplaceContextCriterion());
			matcher.setPropertyValueComparisonResolver(this::evaluate);
		}
		
		return matcher;
	}
	
	@Override
	protected void updateVisibility() {
		if (getInplaceContextCriterion() == null) {
			setHidden(false);
			return;
		}
		
		if (modelPaths == null || (modelPaths.size() != 1 && !getMultiSelectionSupport())) {
			setHidden(true);
			return;
		}
		
		for (List<ModelPath> selection : modelPaths) {
			boolean selectionMatches = false;
			for (ModelPath modelPath : selection) {
				if (getMatcher().matches(modelPath.asTraversingContext())) {
					selectionMatches = true;
					break;
				}
			}
			
			if (!selectionMatches) {
				setHidden(true);
				return;
			}
		}
		
		setHidden(false);
	}
	
	private Object evaluate(Object object) {
		//@formatter:off
		return VDE.evaluate()
				.withEvaluationMode(VdeEvaluationMode.Preliminary)
				.with(UserNameAspect.class, userNameProvider)
				.with(VariableProviderAspect.class, Variable::getDefaultValue)
				.forValue(object);
		//@formatter:on
	}
	
}
