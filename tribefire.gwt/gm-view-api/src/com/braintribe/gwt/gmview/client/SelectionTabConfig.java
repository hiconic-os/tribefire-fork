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
package com.braintribe.gwt.gmview.client;

import com.braintribe.model.query.EntityQuery;

public class SelectionTabConfig {

	private EntityQuery entityQuery;
	private String tabName;

	public SelectionTabConfig(EntityQuery entityQuery) {
		this(entityQuery, null);
	}

	public SelectionTabConfig(EntityQuery entityQuery, String tabName) {
		super();
		this.tabName = tabName;
		this.entityQuery = entityQuery;
	}

	public EntityQuery getEntityQuery() {
		return this.entityQuery;
	}

	public void setEntityQuery(EntityQuery entityQuery) {
		this.entityQuery = entityQuery;
	}

	public String getTabName() {
		return this.tabName;
	}

	public void setTabName(String tabName) {
		this.tabName = tabName;
	}
}
