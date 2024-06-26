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
package com.braintribe.codec.marshaller.html.model;

import java.util.List;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * @author Christina Wilpernig
 * @author Neidhart.Orlich
 */

public interface HtmlListValue extends HtmlValue {

	final EntityType<HtmlListValue> T = EntityTypes.T(HtmlListValue.class);
	
	List<HtmlValue> getElements();
	void setElements(List<HtmlValue> elements);
	
	RenderType getRenderType();
	void setRenderType(RenderType renderType);
	
	String getElementTypeSignature();
	void setElementTypeSignature(String elementTypeSignature);

	@Override
	default RenderType renderType() {
		return getRenderType();
	}

	@Override
	default boolean linkable() {
		return !getElements().isEmpty();
	}
	
}
