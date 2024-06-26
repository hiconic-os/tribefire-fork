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

public interface IndexingStatsEntry extends StandardIdentifiable {

	final EntityType<IndexingStatsEntry> T = EntityTypes.T(IndexingStatsEntry.class);

	long getIndexCount();
	void setIndexCount(long indexCount);

	long getIndexTimeInMillis();
	void setIndexTimeInMillis(long indexTimeInMillis);

	long getIndexCurrent();
	void setIndexCurrent(long indexCurrent);

	long getIndexFailedCount();
	void setIndexFailedCount(long indexFailedCount);

	long getDeleteCount();
	void setDeleteCount(long deleteCount);

	long getDeleteTimeInMillis();
	void setDeleteTimeInMillis(long deleteTimeInMillis);

	long getDeleteCurrent();
	void setDeleteCurrent(long deleteCurrent);

	long getNoopUpdateCount();
	void setNoopUpdateCount(long noopUpdateCount);

	long getThrottleTimeInMillis();
	void setThrottleTimeInMillis(long throttleTimeInMillis);

	boolean getIsThrottled();
	void setIsThrottled(boolean isThrottled);

}
