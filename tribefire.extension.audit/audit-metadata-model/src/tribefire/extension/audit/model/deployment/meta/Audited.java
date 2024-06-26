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
package tribefire.extension.audit.model.deployment.meta;

import com.braintribe.model.generic.annotation.meta.Description;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.meta.data.ExplicitPredicate;
import com.braintribe.model.meta.data.UniversalMetaData;

@Description("The predicate metadata Audited can be applied on entity types to record lifecycle manipulations and on properties to record property manipulations. "
		+ "It can be deactivated with its erasure Unaudited. Use AuditedPreserved to record previous values as well.")
public interface Audited extends ExplicitPredicate, UniversalMetaData {
	EntityType<Audited> T = EntityTypes.T(Audited.class);
}
