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
package com.braintribe.gwt.gmview.util.client;

import com.braintribe.model.generic.reflection.GenericModelType;
import com.braintribe.model.generic.reflection.GenericModelTypeReflection;

public class GMTypeInstanceBean {
	private static Long ID_COUNTER = 0l;
	
	private GenericModelType genericModelType;
	private Object instance;
	private boolean handleInstantiation;
	private Long id;
	
	public GMTypeInstanceBean(GenericModelType genericModelType, Object instance) {
		setId(ID_COUNTER++);
		this.genericModelType = genericModelType;
		this.instance = instance;
	}
	
	public GMTypeInstanceBean(int index) {
		setId(ID_COUNTER++);
		this.genericModelType = GenericModelTypeReflection.TYPE_INTEGER;
		this.instance = index;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setHandleInstantiation(boolean handleInstantiation) {
		this.handleInstantiation = handleInstantiation;
	}
	
	public boolean isHandleInstantiation() {
		return handleInstantiation;
	}
	
	public GenericModelType getGenericModelType() {
		return genericModelType;
	}
	
	public void setGenericModelType(GenericModelType genericModelType) {
		this.genericModelType = genericModelType;
	}
	
	public Object getInstance() {
		return instance;
	}
	
	public void setInstance(Object instance) {
		this.instance = instance;
	}
	
}
