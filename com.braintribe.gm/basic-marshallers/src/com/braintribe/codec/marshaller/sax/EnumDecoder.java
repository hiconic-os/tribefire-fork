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
package com.braintribe.codec.marshaller.sax;

import org.xml.sax.Attributes;

import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.model.generic.GMF;
import com.braintribe.model.generic.reflection.EnumType;

class EnumDecoder extends ScalarValueDecoder {
	private String type;
	@Override
	public void begin(DecodingContext context, Attributes attributes)
			throws MarshallException {
		this.type = attributes.getValue("type");
	}
	@Override
	protected Object decode(DecodingContext context, String text) throws MarshallException {
		EnumType enumType = GMF.getTypeReflection().getType(type);
		return enumType.getInstance(text);
	}
}
