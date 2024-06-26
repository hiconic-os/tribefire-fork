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
package com.braintribe.gwt.gm.storage.expert.impl.wb;

import com.braintribe.gwt.gm.storage.api.ColumnData;
import com.braintribe.gwt.gm.storage.expert.api.QueryStorageInput;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;
import com.braintribe.model.query.Query;

public interface WbQueryStorageInput extends QueryStorageInput {

	final EntityType<WbQueryStorageInput> T = EntityTypes.T(WbQueryStorageInput.class);

	// @formatter:off
	Query getQuery();
	void setQuery(Query query);

	String getQueryString();
	void setQueryString(String queryString);
	
	ColumnData getColumnData();
	void setColumnData(ColumnData columnData);
	// @formatter:on

}
