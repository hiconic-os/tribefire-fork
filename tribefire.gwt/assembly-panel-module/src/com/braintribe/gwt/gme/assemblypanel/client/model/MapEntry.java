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
package com.braintribe.gwt.gme.assemblypanel.client.model;

import com.braintribe.model.generic.reflection.GenericModelType;

public class MapEntry {
	
	private Object key;
	private Object object;
	private String keyString;
	private GenericModelType keyElementType;
	private boolean representsKey;
	
	public MapEntry(Object key, Object object, String keyString, GenericModelType keyElementType, boolean representsKey) {
		this.key = key;
		this.object = object;
		this.keyString = keyString;
		this.keyElementType = keyElementType;
		this.representsKey = representsKey;
	}

	public Object getKey() {
		return key;
	}

	public void setKey(Object key) {
		this.key = key;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	public String getKeyString() {
		return keyString;
	}

	public void setKeyString(String keyString) {
		this.keyString = keyString;
	}

	public GenericModelType getKeyElementType() {
		return keyElementType;
	}

	public void setKeyElementType(GenericModelType keyElementType) {
		this.keyElementType = keyElementType;
	}
	
	public boolean getRepresentsKey() {
		return representsKey;
	}
	
	public void setRepresentsKey(boolean representsKey) {
		this.representsKey = representsKey;
	}

}
