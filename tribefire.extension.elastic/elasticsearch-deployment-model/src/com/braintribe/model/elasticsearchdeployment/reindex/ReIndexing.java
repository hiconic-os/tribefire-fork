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
package com.braintribe.model.elasticsearchdeployment.reindex;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.query.EntityQuery;
import com.braintribe.model.resource.Resource;

public interface ReIndexing extends StandardIdentifiable {

	final EntityType<ReIndexing> T = EntityTypes.T(ReIndexing.class);

	String access = "access";
	String query = "query";
	String reIndexingStatus = "reIndexingStatus";
	String message = "message";
	String report = "report";
	String indexedEntities = "indexedEntities";
	String duration = "duration";

	void setAccess(IncrementalAccess access);
	IncrementalAccess getAccess();

	/**
	 * The query to specify which data should be indexed
	 */
	void setQuery(EntityQuery query);
	EntityQuery getQuery();

	void setReIndexingStatus(ReIndexingStatus reIndexingStatus);
	ReIndexingStatus getReIndexingStatus();

	String getMessage();
	void setMessage(String message);

	Resource getReport();
	void setReport(Resource report);

	void setIndexableEntities(Integer indexedEntities);
	Integer getIndexableEntities();

	void setDuration(Long duration);
	Long getDuration();
}
