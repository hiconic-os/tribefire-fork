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
import com.braintribe.model.extensiondeployment.AccessAspect;
import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@Description("AuditAspect tracks lifecycle and property manipulations in the IncrementalAccess it is assigned to. "
		+ "It uses the Audited, AuditedPreserved, Unaudited metadata to determine the actual requirement for tracking on certain types and properties.")
public interface AuditAspect extends AccessAspect {
	EntityType<AuditAspect> T = EntityTypes.T(AuditAspect.class);
	
	String auditAccess = "auditAccess"; 
	String untrackedRoles = "untrackedRoles"; 
	
	@Description("The access in which the ManipulationRecords are to be stored. "
			+ "If not given the ManipulationRecords will be stored in the IncrementalAccess that is being audited.")
	IncrementalAccess getAuditAccess();
	void setAuditAccess(IncrementalAccess auditAccess);
	
	Set<String> getUntrackedRoles();
	void setUntrackedRoles(Set<String> untrackedRoles);
	
}
