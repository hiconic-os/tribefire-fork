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

import com.braintribe.model.generic.manipulation.ChangeValueManipulation;
import com.braintribe.model.generic.manipulation.Manipulation;
import com.braintribe.model.generic.manipulation.Owner;
import com.braintribe.model.generic.manipulation.PropertyManipulation;

public class ResultEntry {
	
	public String name;
	public String desc;
	public String propertyName;
	public String message;
	public String note;
	public int size;
	private List<Manipulation> listManipulation = new ArrayList<>();
	
	public ResultEntry(String name, String desc, String message, String note, String propertyName, int size) {
		this.name = name;
		this.desc = desc;
		this.message = message;
		this.note = note;
		this.propertyName = propertyName;
		this.size = size;
	}

	public List<Manipulation> getListManipulation() {
		return listManipulation;
	}
	
	public String getOriginalValue() {
		String originalValue = null;
		
		for (Manipulation manipulation : listManipulation) {
			if (manipulation == null)
				continue;
			
			if (!(manipulation instanceof PropertyManipulation))
				continue;
			
			Owner owner = ((PropertyManipulation) manipulation).getOwner();
			
			if (owner == null)
				continue;
			
			if (!owner.getPropertyName().equals(propertyName))
				continue;
			
			Manipulation inverseManipulation = manipulation.getInverseManipulation(); 
			if (inverseManipulation != null && inverseManipulation instanceof ChangeValueManipulation) {
				originalValue = String.valueOf(((ChangeValueManipulation) inverseManipulation).getNewValue());				
				break;
			}
		}
		
		return originalValue;
	}
}
