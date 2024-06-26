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
package com.braintribe.gwt.validationui.client;

import java.util.ArrayList;
import java.util.List;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.manipulation.Manipulation;

public class EntityEntry {
	
	private static Long lastId = 0l;
	
	public GenericEntity entity;
	public boolean persistent;
	public String entityTypeShortName;
	public String entitySelectiveInformation;
	public List<ResultEntry> results;
	public Long id;
	private List<Manipulation> listManipulation = new ArrayList<>();
	
	public EntityEntry(GenericEntity entity, boolean persistent, String entityTypeShortName, String entitySelectiveInformation, List<ResultEntry> results) {
		this.entity = entity;
		this.persistent = persistent;
		this.entityTypeShortName = entityTypeShortName;
		this.entitySelectiveInformation = entitySelectiveInformation;
		this.results = results;
		id = lastId++;
	}

	public List<Manipulation> getListManipulation() {
		return listManipulation;
	}
}
