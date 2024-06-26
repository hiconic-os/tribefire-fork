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
package com.braintribe.gwt.gmview.action.client;

import java.util.List;

import com.braintribe.common.lcd.Pair;
import com.braintribe.model.generic.GenericEntity;

public class EntitiesProviderResult {
	
	private List<GenericEntity> entities;
	private List<Pair<GenericEntity, String>> entityAndDisplayList;
	private int offset;
	private boolean hasMore;
	
	public EntitiesProviderResult(List<GenericEntity> entities, int offset, boolean hasMore) {
		this.entities = entities;
		this.offset = offset;
		this.hasMore = hasMore;
	}
	
	public EntitiesProviderResult(int offset, boolean hasMore, List<Pair<GenericEntity, String>> entityAndDisplayList) {
		this.offset = offset;
		this.hasMore = hasMore;
		this.entityAndDisplayList = entityAndDisplayList;
	}
	
	public List<GenericEntity> getEntities() {
		return entities;
	}
	
	public List<Pair<GenericEntity, String>> getEntityAndDisplayList() {
		return entityAndDisplayList;
	}
	
	public void setEntities(List<GenericEntity> entities) {
		this.entities = entities;
	}
	
	public int getOffset() {
		return offset;
	}
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	public boolean isHasMore() {
		return hasMore;
	}
	
	public void setHasMore(boolean hasMore) {
		this.hasMore = hasMore;
	}
	

}
