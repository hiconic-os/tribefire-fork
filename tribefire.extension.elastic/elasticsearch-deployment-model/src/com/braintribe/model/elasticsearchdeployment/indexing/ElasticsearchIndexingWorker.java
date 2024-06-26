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
package com.braintribe.model.elasticsearchdeployment.indexing;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.elasticsearchdeployment.IndexedElasticsearchConnector;
import com.braintribe.model.extensiondeployment.Worker;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.annotation.meta.DeployableComponent;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

@DeployableComponent
public interface ElasticsearchIndexingWorker extends Worker {

	final EntityType<ElasticsearchIndexingWorker> T = EntityTypes.T(ElasticsearchIndexingWorker.class);

	String access = "access";
	String elasticsearchConnector = "elasticsearchConnector";
	String threadCount = "threadCount";

	void setAccess(IncrementalAccess access);
	IncrementalAccess getAccess();

	void setElasticsearchConnector(IndexedElasticsearchConnector elasticsearchConnector);
	IndexedElasticsearchConnector getElasticsearchConnector();

	void setThreadCount(Integer threadCount);
	@Initializer("2")
	Integer getThreadCount();

	void setQueueSize(Integer queueSize);
	@Initializer("1000")
	Integer getQueueSize();

}
