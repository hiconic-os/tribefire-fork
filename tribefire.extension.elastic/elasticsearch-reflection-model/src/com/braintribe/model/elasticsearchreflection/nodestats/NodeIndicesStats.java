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
package com.braintribe.model.elasticsearchreflection.nodestats;

import com.braintribe.model.generic.StandardIdentifiable;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

public interface NodeIndicesStats extends StandardIdentifiable {

	final EntityType<NodeIndicesStats> T = EntityTypes.T(NodeIndicesStats.class);

	StoreStats getStore();
	void setStore(StoreStats store);

	DocsStats getDocs();
	void setDocs(DocsStats docs);

	IndexingStats getIndexingStats();
	void setIndexingStats(IndexingStats indexingStats);

	GetStats getGet();
	void setGet(GetStats get);

	SearchStats getSearch();
	void setSearch(SearchStats search);

	MergeStats getMerge();
	void setMerge(MergeStats merge);

	RefreshStats getRefresh();
	void setRefresh(RefreshStats refresh);

	FlushStats getFlush();
	void setFlush(FlushStats flush);

	FieldDataStats getFieldData();
	void setFieldData(FieldDataStats fieldData);

	QueryCacheStats getQueryCache();
	void setQueryCache(QueryCacheStats queryCache);

	RequestCacheStats getRequestCache();
	void setRequestCache(RequestCacheStats requestCache);

	CompletionStats getCompletion();
	void setCompletion(CompletionStats completion);

	SegmentsStats getSegments();
	void setSegments(SegmentsStats segments);

}
