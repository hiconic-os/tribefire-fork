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
package com.braintribe.model.generic.reflection;

import java.util.Collection;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.mdec.ModelDeclaration;
import com.braintribe.model.meta.Weavable;

import jsinterop.annotations.JsType;

@JsType(namespace = GmCoreApiInteropNamespaces.reflection)
@SuppressWarnings("unusable-by-js")
public interface Model {

	ModelDeclaration getModelArtifactDeclaration();

	Collection<? extends Model> getDependencies();

	<M extends Weavable> M getMetaModel();

	Collection<Class<?>> getDeclaredJavaTypes();

	boolean isRootModel();

	default String name() {
		return getModelArtifactDeclaration().getName();
	}
	
	default String globalId() {
		return modelGlobalId(getModelArtifactDeclaration().getName());
	}
	
	static String modelGlobalId(String modelName) {
		return "model:" + modelName;
	}

}
