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
package com.braintribe.gwt.gme.notification.client;

import com.braintribe.model.command.Command;
import com.braintribe.model.generic.reflection.ScalarType;

public class CommandModel {
	
	private String id;
	private String name;
	
	public CommandModel(Command command) {
		Object idObject = command.getId();
		
		if (idObject == null) {
			idObject = (long) command.hashCode();
			command.setId(idObject);
		}
		
		ScalarType type = (ScalarType) command.entityType().getIdProperty().getType().getActualType(idObject);
		id = type.instanceToString(idObject);
		
		name = command.getName();
		if (name == null)
			name = command.entityType().getShortName();
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
