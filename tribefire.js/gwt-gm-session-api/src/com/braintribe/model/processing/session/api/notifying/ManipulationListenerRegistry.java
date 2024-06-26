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
package com.braintribe.model.processing.session.api.notifying;

import com.braintribe.model.generic.GmCoreApiInteropNamespaces;
import com.braintribe.model.generic.tracking.ManipulationListener;
import com.braintribe.model.processing.session.api.notifying.js.JsManipulationListener;

import jsinterop.annotations.JsIgnore;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsType;

@JsType(namespace=GmCoreApiInteropNamespaces.manipulation)
public interface ManipulationListenerRegistry {

	@JsIgnore
	void add(ManipulationListener listener);

	@JsIgnore
	void addFirst(ManipulationListener listener);

	@JsIgnore
	void remove(ManipulationListener listener);
	
	@JsMethod(name="add")
	default void _add(Object listener) {
		if (!(listener instanceof JsManipulationListener)) {
			add((ManipulationListener) listener);
			return;
		}
		
		add(manipulation -> ((JsManipulationListener) listener).noticeManipulation(manipulation));
	}

	@JsMethod(name="addFirst")
	default void _addFirst(Object listener) {
		if (!(listener instanceof JsManipulationListener)) {
			addFirst((ManipulationListener) listener);
			return;
		}
		
		addFirst(manipulation -> ((JsManipulationListener) listener).noticeManipulation(manipulation));
	}

	@JsMethod(name="remove")
	default void _remove(Object listener) {
		if (!(listener instanceof JsManipulationListener)) {
			remove((ManipulationListener) listener);
			return;
		}
		
		remove(manipulation -> ((JsManipulationListener) listener).noticeManipulation(manipulation));
	}
}
