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
package com.braintribe.model.processing.elasticsearch.status;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.StandardStringIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface WorkerStatus extends StandardStringIdentifiable {

	EntityType<WorkerStatus> T = EntityTypes.T(WorkerStatus.class);

	String getIndex();
	void setIndex(String index);

	GenericEntity getWorkerIdentification();
	void setWorkerIdentification(GenericEntity workerIdentification);

	int getQueueSize();
	void setQueueSize(int queueSize);

	int getEnqueued();
	void setEnqueued(int enqueued);

	int getWorkerCount();
	void setWorkerCount(int workerCount);

	int getActiveWorker();
	void setActiveWorker(int activeWorker);

	long getMaxActiveRuntime();
	void setMaxActiveRuntime(long maxActiveRuntime);

	int getIndexedPackagesCount();
	void setIndexedPackagesCount(int indexedPackagesCount);

	int getIndexedEntitiesCount();
	void setIndexedEntitiesCount(int indexedEntitiesCount);

}
