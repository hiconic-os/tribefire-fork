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
package tribefire.extension.xml.schemed.marshaller.xsd.experts;

import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.braintribe.model.generic.GenericEntity;

import tribefire.extension.xml.schemed.marshaller.commons.QNameExpert;
import tribefire.extension.xml.schemed.model.xsd.Annotation;
import tribefire.extension.xml.schemed.model.xsd.Attribute;
import tribefire.extension.xml.schemed.model.xsd.AttributeGroup;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class AttributeGroupExpert extends AbstractSchemaExpert {
	
	public static AttributeGroup read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		// wind to next event
		AttributeGroup group = AttributeGroup.T.create();
		attach(group, declaringSchema);
		
		Map<QName, String> attributes = readAttributes(reader);
		group.setName( attributes.get( new QName(NAME)));
		
		group.setId( attributes.get( new QName( ID)));
		
		String elementReference = attributes.get( new QName(REF));
		group.setRef(QNameExpert.parse(elementReference));
		
		readAnyAttributes( group.getAnyAttributes(), attributes, ID, NAME, REF);
		
		reader.next();
		
		while (reader.hasNext()) {
			
			switch (reader.getEventType()) {
			
				case XMLStreamConstants.START_ELEMENT :												
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case ATTRIBUTE:
							Attribute attribute2 = AttributeExpert.read( declaringSchema, reader);
							group.getAttributes().add( attribute2);
							group.getNamedItemsSequence().add( attribute2);
							break;
						case ATTRIBUTE_GROUP:
							AttributeGroup attributeGroup = AttributeGroupExpert.read( declaringSchema, reader);
							group.getAttributeGroups().add( attributeGroup);
							group.getNamedItemsSequence().add( attributeGroup);
							break;											
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read(declaringSchema, reader);
							group.setAnnotation(annotation);
							group.getNamedItemsSequence().add( annotation);
							break;
						default:
							skip(reader);
							break;
					}				
				break;
				
				case XMLStreamConstants.END_ELEMENT : {
					return group;
				}		
			
				default: 
					break;
			}
			reader.next();
		}
		return group;
	}
		
	public static void write( XMLStreamWriter writer, Namespace namespace, AttributeGroup group) throws XMLStreamException {
		if (group == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + ATTRIBUTE_GROUP : ATTRIBUTE_GROUP);

		String name = group.getName();
		if (name != null) {
			writer.writeAttribute( NAME, name);
		}
		else {
			tribefire.extension.xml.schemed.model.xsd.QName elementReference = group.getRef();
			if (elementReference != null) {
				writer.writeAttribute( REF, QNameExpert.toString(elementReference));
			}
		}
		for (GenericEntity ge : group.getNamedItemsSequence()) {
			if (ge instanceof Attribute) {
				AttributeExpert.write(writer, namespace, (Attribute) ge);
			}
			else if (ge instanceof AttributeGroup) {
				AttributeGroupExpert.write(writer, namespace, (AttributeGroup) ge);
				
			}
			else {
				throw new IllegalStateException("unknown type [" + ge.getClass() + "] encountered");
			}
		}
		writer.writeEndElement();
	}
}
