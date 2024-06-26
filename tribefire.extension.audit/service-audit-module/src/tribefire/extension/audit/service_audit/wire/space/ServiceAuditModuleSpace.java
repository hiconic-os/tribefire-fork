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
package tribefire.extension.audit.service_audit.wire.space;

import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.audit.processing.ServiceAuditInterceptor;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class ServiceAuditModuleSpace implements TribefireModuleContract {

	@Import
	private TribefireWebPlatformContract tfPlatform;

	//
	// Deployables
	//

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(tribefire.extension.audit.model.deployment.AuditInterceptor.T) //
			.component(tfPlatform.binders().serviceAroundProcessor()) //
			.expertFactory(this::auditInterceptor);
	}
	
	private ServiceAuditInterceptor auditInterceptor(ExpertContext<tribefire.extension.audit.model.deployment.AuditInterceptor> context) {
		ServiceAuditInterceptor bean = new ServiceAuditInterceptor();
		
		bean.setAuditAccessId(context.getDeployable().getAuditAccess().getExternalId());
		bean.setModelAccessoryFactory(tfPlatform.requestUserRelated().modelAccessoryFactory());
		bean.setMarshallerRegistry(tfPlatform.marshalling().registry());
		bean.setSystemSessionFactory(tfPlatform.systemUserRelated().sessionFactory());
		bean.setSystemEvaluator(tfPlatform.systemUserRelated().evaluator());
		
		return bean;
	}

}
