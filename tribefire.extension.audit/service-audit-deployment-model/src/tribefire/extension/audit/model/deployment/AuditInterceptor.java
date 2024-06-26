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
package tribefire.extension.audit.model.deployment;

import java.util.Set;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.extensiondeployment.ServiceAroundProcessor;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.annotation.meta.Mandatory;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface AuditInterceptor extends ServiceAroundProcessor {
	EntityType<AuditInterceptor> T = EntityTypes.T(AuditInterceptor.class);
	
	String auditAccess = "auditAccess"; 
	String untrackedRoles = "untrackedRoles"; 
	
	@Mandatory
	@Description("The access in which the ServiceAuditRecords are to be stored.")
	IncrementalAccess getAuditAccess();
	void setAuditAccess(IncrementalAccess auditAccess);
	
	Set<String> getUntrackedRoles();
	void setUntrackedRoles(Set<String> untrackedRoles);
}
