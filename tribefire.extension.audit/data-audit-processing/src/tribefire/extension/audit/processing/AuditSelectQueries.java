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
package tribefire.extension.audit.processing;

import java.util.List;
import java.util.Set;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.value.PersistentEntityReference;
import com.braintribe.model.processing.query.building.SelectQueries;
import com.braintribe.model.query.From;
import com.braintribe.model.query.SelectQuery;

class AuditSelectQueries extends SelectQueries {
	static SelectQuery queryEntities(String typeSignature, Set<PersistentEntityReference> references, List<String> preserveProperties) {
		
		From entity = source(typeSignature, "e");
		
		SelectQuery query = from(entity) //
			.select( //
				property(entity, GenericEntity.id), // 
				property(entity, GenericEntity.partition) //
			) //
			.where( //
				and( //
					eq(entitySignature(entity), typeSignature), //
					in(entity, references) //
				) //
			);
		
		preserveProperties.stream() //
			.map(p -> property(entity, p)) //
			.forEach(query::select);
		
		return query;
	}
}