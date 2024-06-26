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
package com.braintribe.utils.collection.impl;

import com.braintribe.common.attribute.AttributeContext;
import com.braintribe.common.attribute.TypeSafeAttribute;

public class AttributeContextBuilderImpl extends AbstractAttributeContextBuilder {

	private final MapAttributeContext newContext;

	public AttributeContextBuilderImpl(MapAttributeContext newContext) {
		this.newContext = newContext;
	}

	@Override
	public <A extends TypeSafeAttribute<? super V>, V> void setAttribute(Class<A> attribute, V value) {
		newContext.setAttribute(attribute, value);
	}

	@Override
	public AttributeContext build() {
		newContext.seal();
		return newContext;
	}

}