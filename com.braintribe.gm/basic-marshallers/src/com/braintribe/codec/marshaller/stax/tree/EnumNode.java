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
package com.braintribe.codec.marshaller.stax.tree;

import java.io.IOException;
import java.io.Writer;

import com.braintribe.codec.marshaller.api.MarshallException;
import com.braintribe.codec.marshaller.stax.PrettinessSupport;
import com.braintribe.codec.marshaller.stax.TypeInfo;

public class EnumNode extends ValueStaxNode {
	private TypeInfo typeInfo;
	private Enum<?> value;
	
	public EnumNode(TypeInfo typeInfo, Enum<?> value) {
		super();
		this.typeInfo = typeInfo;
		this.value = value;
	}

	@Override
	public void write(Writer writer, PrettinessSupport prettinessSupport, int indent) throws IOException, MarshallException {
		writer.write("<e>");
		writer.write(typeInfo.as);
		writer.write('.');
		writer.write(value.toString());
		writer.write("</e>");
	}
	
	@Override
	public void write(Writer writer, PrettinessSupport prettinessSupport, String propertyName, int indent) throws IOException, MarshallException {
		writer.write("<e p='");
		writer.write(propertyName);
		writer.write("'>");
		writer.write(typeInfo.as);
		writer.write('.');
		writer.write(value.toString());
		writer.write("</e>");
	}
}
