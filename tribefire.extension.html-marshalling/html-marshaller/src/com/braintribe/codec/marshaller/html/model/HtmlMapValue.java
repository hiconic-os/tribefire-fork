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

import java.util.Map;

import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.EntityTypes;

/**
 * @author Christina Wilpernig
 * @author Neidhart.Orlich
 */

public interface HtmlMapValue extends HtmlValue {

	final EntityType<HtmlMapValue> T = EntityTypes.T(HtmlMapValue.class);
	
	Map<HtmlValue, HtmlValue> getEntries();
	void setEntries(Map<HtmlValue, HtmlValue> entries);
	
	String getKeyTypeSignature();
	void setKeyTypeSignature(String keyTypeSignature);
	
	String getValueTypeSignature();
	void setValueTypeSignature(String valueTypeSignature);
	
	@Override
	default RenderType renderType() {
		return RenderType.map;
	}

	@Override
	default boolean linkable() {
		return !getEntries().isEmpty();
	}	
}
