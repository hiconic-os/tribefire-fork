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
import com.braintribe.model.generic.GenericEntity;

public class EntityNode extends StaxNode {
	private static final char[] endEmptyElement = "'/>".toCharArray();
	private static final char[] endElement = "</E>".toCharArray();
	private static final char[] startElement1 = "<E id='".toCharArray();
	private static final char[] startElement2 = "'>".toCharArray();
	public EntityNode next;
	private String[] propertyNames;
	private ValueStaxNode[] propertyValues;
	private int size;
	private String id;
	private GenericEntity entity;
	
	public EntityNode(GenericEntity entity, String id, String[] propertyNames, ValueStaxNode[] propertyValues, int size) {
		super();
		this.entity = entity;
		this.id = id;
		this.propertyNames = propertyNames;
		this.propertyValues = propertyValues;
		this.size = size;
	}

	
	@Override
	public void write(Writer writer, PrettinessSupport prettinessSupport, int indent) throws IOException, MarshallException {
		prettinessSupport.writeLinefeed(writer, indent);
		
		writer.write(startElement1);
		StringNode.writeEscapedAttribute(writer, id);
		
		if (size > 0) {
			writer.write(startElement2);
			
			int propertyIndent = indent + 1;
			for (int i = 0; i < size; i++) {
				String propertyName = propertyNames[i];
				ValueStaxNode valueNode = propertyValues[i];
				prettinessSupport.writeLinefeed(writer, propertyIndent);
				valueNode.write(writer, prettinessSupport, propertyName, propertyIndent);
			}
			prettinessSupport.writeLinefeed(writer, indent);
			writer.write(endElement);
		}
		else 
			writer.write(endEmptyElement);
	}
	
	public GenericEntity getEntity() {
		return entity;
	}
}
