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
package com.braintribe.model.processing.smart.query.planner.splitter;

import static com.braintribe.utils.lcd.CollectionTools2.newMap;

import java.util.Map;

import com.braintribe.model.accessdeployment.IncrementalAccess;
import com.braintribe.model.query.From;
import com.braintribe.model.query.SelectQuery;

public class DisambiguatedQuery {

	public final SelectQuery query;
	public final SelectQuery originalQuery;
	public final Map<From, IncrementalAccess> fromMapping = newMap();

	public DisambiguatedQuery(SelectQuery query, SelectQuery originalQuery) {
		this.query = query;
		this.originalQuery = originalQuery;
	}

}