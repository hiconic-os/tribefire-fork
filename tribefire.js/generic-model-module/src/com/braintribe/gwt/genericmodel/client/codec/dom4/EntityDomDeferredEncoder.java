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
package com.braintribe.gwt.genericmodel.client.codec.dom4;

import com.braintribe.codec.CodecException;
import com.braintribe.model.generic.GenericEntity;
import com.braintribe.model.generic.pr.AbsenceInformation;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.google.gwt.xml.client.Document;
import com.google.gwt.xml.client.Element;

public class EntityDomDeferredEncoder extends DeferredProcessor {
	public GenericEntity entity;
	public String refId;
	private TypeInfo typeInfo;
	private DomEncodingContext context;

	public EntityDomDeferredEncoder(DomEncodingContext context, TypeInfo typeInfo, GenericEntity entity, String refId) {
		this.context = context;
		this.entity = entity;
		this.refId = refId;
		this.typeInfo = typeInfo;
	}
	
	@Override
	public boolean continueProcessing() throws CodecException {
		Document document = context.getDocument();
		Element entityElement = document.createElement("E");
		
		entityElement.setAttribute("t", typeInfo.as);
		entityElement.setAttribute("id", refId);
		
		GenericEntity _entity = this.entity;
		
		context.visit(_entity);

		for (Property property: ((EntityType<?>)typeInfo.type).getProperties()) {
			Object value = property.get(_entity);
			
			Element propertyElement = null;
			
			if (value == null) {
				AbsenceInformation absenceInformation = property.getAbsenceInformation(_entity);
				if (absenceInformation != null && context.shouldWriteAbsenceInformation()) {
					propertyElement = encodeAbsenceInformation(context, absenceInformation);
				} 
			}
			else {
				propertyElement = context.encodeValue(property.getType(),  value, true);
			}
			
			if (propertyElement != null) {
				propertyElement.setAttribute("p", property.getName());
				entityElement.appendChild(propertyElement);
			}
		}
		
		context.appendToPool(entityElement);
		
		return false;
	}
	
	
	private static Element encodeAbsenceInformation(DomEncodingContext context, AbsenceInformation absenceInformation) throws CodecException {
		Document document = context.getDocument();
		Element element = document.createElement("a");
		if (!context.isSimpleAbsenceInformation(absenceInformation)) {
			String text = context.lookupQualifiedId(absenceInformation);
			element.appendChild(document.createTextNode(text));
		}
		return element;
	}

}
