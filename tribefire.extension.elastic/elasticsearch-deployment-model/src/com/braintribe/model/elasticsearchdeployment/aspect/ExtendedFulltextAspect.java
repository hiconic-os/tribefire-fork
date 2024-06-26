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
package com.braintribe.model.elasticsearchdeployment.aspect;

import com.braintribe.model.elasticsearchdeployment.IndexedElasticsearchConnector;
import com.braintribe.model.elasticsearchdeployment.indexing.ElasticsearchIndexingWorker;
import com.braintribe.model.extensiondeployment.AccessAspect;
import com.braintribe.model.generic.annotation.Initializer;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface ExtendedFulltextAspect extends AccessAspect {

	final EntityType<ExtendedFulltextAspect> T = EntityTypes.T(ExtendedFulltextAspect.class);

	void setElasticsearchConnector(IndexedElasticsearchConnector elasticsearchConnector);
	IndexedElasticsearchConnector getElasticsearchConnector();

	void setWorker(ElasticsearchIndexingWorker worker);
	ElasticsearchIndexingWorker getWorker();

	void setMaxFulltextResultSize(Integer maxFulltextResultSize);
	@Initializer("100")
	Integer getMaxFulltextResultSize();

	void setCascadingAttachment(boolean cascadingAttachment);
	@Initializer("true")
	boolean getCascadingAttachment();

	void setMaxResultWindow(Integer maxResultWindow);
	@Initializer("100000")
	Integer getMaxResultWindow();

	void setIgnoreUnindexedEntities(boolean ignoreUnindexedEntities);
	boolean getIgnoreUnindexedEntities();

}
