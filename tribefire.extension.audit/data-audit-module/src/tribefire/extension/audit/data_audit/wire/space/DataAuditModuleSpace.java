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
package tribefire.extension.audit.data_audit.wire.space;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.meta.GmMetaModel;
import com.braintribe.model.processing.deployment.api.ExpertContext;
import com.braintribe.model.processing.deployment.api.binding.DenotationBindingBuilder;
import com.braintribe.model.processing.session.api.managed.ManagedGmSession;
import com.braintribe.wire.api.annotation.Import;
import com.braintribe.wire.api.annotation.Managed;

import tribefire.extension.audit.processing.AuditAspect;
import tribefire.extension.audit.processing.ManipulationRecordCreator;
import tribefire.module.api.InitializerBindingBuilder;
import tribefire.module.wire.contract.TribefireModuleContract;
import tribefire.module.wire.contract.TribefireWebPlatformContract;

/**
 * This module's javadoc is yet to be written.
 */
@Managed
public class DataAuditModuleSpace implements TribefireModuleContract {

	private static final String CORTEX_MODEL_GLOBAL_ID = "model:tribefire.cortex:tribefire-cortex-model";
	private static final String DATA_AUDIT_DEPLOYMENT_MODEL_GLOBAL_ID = "model:tribefire.extension.audit:data-audit-deployment-model";
	
	@Import
	private TribefireWebPlatformContract tfPlatform;

	//
	// Initializers
	//
	
	@Override
	public void bindInitializers(InitializerBindingBuilder bindings) {
		bindings.bind(c -> {
			ManagedGmSession session = c.getSession();
			GmMetaModel cortexAccessModel = session.getEntityByGlobalId(CORTEX_MODEL_GLOBAL_ID);
			GmMetaModel dataAuditDeploymentModel = session.getEntityByGlobalId(DATA_AUDIT_DEPLOYMENT_MODEL_GLOBAL_ID);
			cortexAccessModel.getDependencies().add(dataAuditDeploymentModel);
		});
	}
	
	//
	// Deployables
	//

	@Override
	public void bindDeployables(DenotationBindingBuilder bindings) {
		bindings.bind(tribefire.extension.audit.model.deployment.AuditAspect.T) //
			.component(tfPlatform.binders().accessAspect()) //
			.expertFactory(this::auditAspect);
	}
	
	
	@Managed
	private AuditAspect auditAspect(ExpertContext<tribefire.extension.audit.model.deployment.AuditAspect> expertContext) {
		tribefire.extension.audit.model.deployment.AuditAspect deployable = expertContext.getDeployable(); 
		AuditAspect bean = new AuditAspect();
		
		IncrementalAccess auditAccess = deployable.getAuditAccess();
		
		if (auditAccess != null) {
			bean.setAuditSessionProvider(() -> tfPlatform.systemUserRelated().sessionFactory().newSession(auditAccess.getExternalId()));
		}
		
		bean.setActive(true);
		bean.setUntrackedRoles(deployable.getUntrackedRoles());
		bean.setUserRolesProvider(tfPlatform.requestUserRelated().userRolesSupplier());
		bean.setManipulationRecordCreator(recordCreator());
		return bean;
	}
	
	@Managed
	private ManipulationRecordCreator recordCreator() {
		ManipulationRecordCreator bean = new ManipulationRecordCreator();
		return bean;
	}

}
