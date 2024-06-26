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

public interface SearchStatsEntry extends StandardIdentifiable {

	final EntityType<SearchStatsEntry> T = EntityTypes.T(SearchStatsEntry.class);

	long getQueryCount();
	void setQueryCount(long queryCount);

	long getQueryTimeInMillis();
	void setQueryTimeInMillis(long queryTimeInMillis);

	long getQueryCurrent();
	void setQueryCurrent(long queryCurrent);

	long getFetchCount();
	void setFetchCount(long fetchCount);

	long getFetchTimeInMillis();
	void setFetchTimeInMillis(long fetchTimeInMillis);

	long getFetchCurrent();
	void setFetchCurrent(long fetchCurrent);

	long getScrollCount();
	void setScrollCount(long scrollCount);

	long getScrollTimeInMillis();
	void setScrollTimeInMillis(long scrollTimeInMillis);

	long getScrollCurrent();
	void setScrollCurrent(long scrollCurrent);

	long getSuggestCount();
	void setSuggestCount(long suggestCount);

	long getSuggestTimeInMillis();
	void setSuggestTimeInMillis(long suggestTimeInMillis);

	long getSuggestCurrent();
	void setSuggestCurrent(long suggestCurrent);

}
