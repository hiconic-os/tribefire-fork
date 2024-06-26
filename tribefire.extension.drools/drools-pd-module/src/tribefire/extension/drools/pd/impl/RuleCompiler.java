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
package tribefire.extension.drools.pd.impl;

import java.util.Objects;

import com.braintribe.cfg.Configurable;
import com.braintribe.cfg.Required;
import com.braintribe.model.deployment.Deployable;
import com.braintribe.model.resource.Resource;

import tribefire.extension.drools.integration.api.DroolsContainer;
import tribefire.extension.drools.integration.api.DroolsContainerFactory;
import tribefire.extension.drools.model.pd.HasRuleSheet;

public class RuleCompiler {
	private DroolsContainerFactory containerFactory;
	
	@Required
	@Configurable
	public void setContainerFactory(DroolsContainerFactory containerFactory) {
		this.containerFactory = containerFactory;
	}
	
	public <T extends HasRuleSheet & Deployable> DroolsContainer compile(T deployable) {
		Resource resource = Objects.requireNonNull(deployable.getRules(), "HasRuleSheet.resource must not be null");
		return containerFactory.open(resource::openStream, deployable.getExternalId());
	}
}
