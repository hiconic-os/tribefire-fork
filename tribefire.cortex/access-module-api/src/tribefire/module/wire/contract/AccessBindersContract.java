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
package tribefire.module.wire.contract;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.extensiondeployment.access.AccessRequestProcessor;
import com.braintribe.model.processing.deployment.api.ComponentBinder;
import com.braintribe.wire.api.space.WireSpace;

/**
 * @author peter.gazdik
 */
public interface AccessBindersContract extends WireSpace {

	ComponentBinder<IncrementalAccess, com.braintribe.model.access.IncrementalAccess> incrementalAccess();

	ComponentBinder<AccessRequestProcessor, com.braintribe.model.processing.accessrequest.api.AccessRequestProcessor<?, ?>> accessRequestProcessor();

	ComponentBinder<com.braintribe.model.extensiondeployment.AccessAspect, com.braintribe.model.processing.aop.api.aspect.AccessAspect> accessAspect();

}