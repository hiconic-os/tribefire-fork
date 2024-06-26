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
package com.braintribe.model.generic.path;

import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.path.api.IRootModelPathElement;
import com.braintribe.model.generic.reflection.GenericModelType;

import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsType;

@JsType(namespace = ModelPath.MODEL_PATH_NAMESPACE)
@SuppressWarnings("unusable-by-js")
public class RootPathElement extends ModelPathElement implements IRootModelPathElement {

	@JsIgnore
	public RootPathElement(GenericEntity value) {
		this(value.entityType(), value);
	}
	
	@JsConstructor
	public RootPathElement(GenericModelType type, Object value) {
		super(type, value);
	}
	
	@Override
	public ModelPathElementType getPathElementType() {
		return ModelPathElementType.Root;
	}
	
	@Override
	public RootPathElement copy() {
		return new RootPathElement(getType(), getValue());
	}

	@Override
	public com.braintribe.model.generic.path.api.ModelPathElementType getElementType() {
		return com.braintribe.model.generic.path.api.ModelPathElementType.Root;
	}
}
