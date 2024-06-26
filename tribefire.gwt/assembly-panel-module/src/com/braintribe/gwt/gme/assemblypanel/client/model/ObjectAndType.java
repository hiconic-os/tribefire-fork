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

public class ObjectAndType {
	private Object object;
	private GenericModelType type;
	private int depth;
	private boolean mapAsList = false;
	private boolean isRoot = false;
	private Integer maxSize;
	
	public Object getObject() {
		return object;
	}
	
	public void setObject(Object object) {
		this.object = object;
	}
	
	public <X extends GenericModelType> X getType() {
		return (X) type;
	}
	
	public void setType(GenericModelType type) {
		this.type = type;
	}
	
	public int getDepth() {
		return depth;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public boolean isMapAsList() {
		return mapAsList;
	}
	
	public void setMapAsList(boolean mapAsList) {
		this.mapAsList = mapAsList;
	}
	
	public boolean isRoot() {
		return isRoot;
	}
	
	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}
	
	public Integer getMaxSize() {
		return maxSize;
	}
	
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
}
