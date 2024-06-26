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
package tribefire.extension.drools.pd.wire.space;

import com.braintribe.model.deployment.Deployable;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;
import com.braintribe.wire.api.space.WireSpace;

import tribefire.extension.drools.integration.api.DroolsContainer;
import tribefire.extension.drools.integration.impl.BasicDroolsContainerFactory;
import tribefire.extension.drools.model.pd.DroolsCondition;
import tribefire.extension.drools.model.pd.HasRuleSheet;
import tribefire.extension.drools.pd.impl.RuleCompiler;
import tribefire.extension.drools.pe.DroolsConditionProcessor;
import tribefire.extension.drools.pe.DroolsTransitionProcessor;
import tribefire.module.wire.contract.ResourceProcessingContract;

@Managed
public class DeployablesSpace implements WireSpace {
	
	@Import
	private ResourceProcessingContract resourceProcessing;
	
	@Managed
	public DroolsTransitionProcessor transitionProcessor(ExpertContext<tribefire.extension.drools.model.pd.DroolsTransitionProcessor> context) {
		DroolsTransitionProcessor bean = new DroolsTransitionProcessor();
		bean.setDroolsContainer(droolsContainer(context));
		return bean;
	}
	
	@Managed 
	public DroolsConditionProcessor conditionProcessor(ExpertContext<DroolsCondition> context) {
		DroolsConditionProcessor bean = new DroolsConditionProcessor();
		bean.setDroolsContainer(droolsContainer(context));
		return bean;
	}
	
	@Managed
	private <T extends HasRuleSheet & Deployable> DroolsContainer droolsContainer(ExpertContext<T> context) {
		return ruleCompiler().compile(context.getDeployable());
	}
	
	@Managed
	private RuleCompiler ruleCompiler() {
		RuleCompiler bean = new RuleCompiler();
		bean.setContainerFactory(containerFactory());
		return bean;
	}
	
	@Managed
	private BasicDroolsContainerFactory containerFactory() {
		BasicDroolsContainerFactory bean = new BasicDroolsContainerFactory();
		return bean;
	}
	
}
