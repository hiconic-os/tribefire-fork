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
import com.braintribe.model.generic.proxy.ProxyContext;
import com.braintribe.model.generic.proxy.ProxyValue;
import com.braintribe.model.generic.reflection.EntityType;
import com.braintribe.model.generic.reflection.Property;
import com.google.gwt.xml.client.Element;
import com.google.gwt.xml.client.Node;

public class EntityDomDeferredDecoder extends DeferredProcessor {

	private EntityType<?> entityType;
	public GenericEntity entity;
	public String refId;
	public TypeInfo typeInfo;
	public Element element;
	public DomDecodingContext context;
	
	public EntityDomDeferredDecoder(DomDecodingContext context, EntityType<?> entityType, Element element) {
		this.context = context;
		this.entityType = entityType;
		this.element = element;
	}
	
	@Override
	public boolean continueProcessing() throws CodecException {
		String id = element.getAttribute("id");
		EntityRegistration registration = context.acquireEntity(id);
		GenericEntity _entity = registration.entity;
		
		Node node = element.getFirstChild();
		ProxyContext proxyContext = context.getProxyContext();
		while (node != null) {
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element propertyElement = (Element)node;
				String propertyName = propertyElement.getAttribute("p");
				Property property = entityType.findProperty(propertyName);
				
				if (property != null) {
					if (propertyElement.getTagName().equals("a")) {
						AbsenceInformation absenceInformation = decodeAbsenceInforamtion(context, propertyElement);
						property.setAbsenceInformation(_entity, absenceInformation);
					}
					else {
						Object value = context.decodeValue(propertyElement);
						if (proxyContext == null) {
							property.setDirectUnsafe(_entity, value);
						}
						else {
							if (value instanceof ProxyValue) {
								proxyContext.deferPropertyAssignment(_entity, property, (ProxyValue)value);
							}
							else {
								property.setDirectUnsafe(_entity, value);
							}
						}
					}
				}
				else {
					if (context.isPropertyLenient())
						throw new CodecException("unkown property " + propertyName + " for type " + entityType);
				}
			}
			node = node.getNextSibling();
		}
		
		return false;
	}
	
	private static AbsenceInformation decodeAbsenceInforamtion(DomDecodingContext context, Element element) throws CodecException {
		String text = DomDecodingContextImpl.getTextContent(element);
		if (text.isEmpty())
			return context.getAbsenceInformationForMissingProperties();
		else
			return (AbsenceInformation)context.acquireEntity(text).entity;
	}
	
}
