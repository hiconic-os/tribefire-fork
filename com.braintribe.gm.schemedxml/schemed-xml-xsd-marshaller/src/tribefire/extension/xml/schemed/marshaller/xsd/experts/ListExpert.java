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
import tribefire.extension.xml.schemed.model.xsd.List;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.SimpleType;

public class ListExpert extends AbstractSchemaExpert {

	public static List read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		
		List list = List.T.create();
		attach( list, declaringSchema);
		
		Map<QName,String> attributes = readAttributes(reader);		
		list.setSimpleTypeReference(  QNameExpert.parse(attributes.get( new QName( ITEM_TYPE))));
		
		readAnyAttributes( list.getAnyAttributes(), attributes, ID, ITEM_TYPE);
		
		reader.next();
		
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read( declaringSchema, reader);
							list.setAnnotation(annotation);
							list.getNamedItemsSequence().add(annotation);
						break;
						case SIMPLE_TYPE:
							SimpleType simpleType = SimpleTypeExpert.read(declaringSchema, reader);
							list.setSimpleType( simpleType);
							list.getNamedItemsSequence().add( simpleType);
							break;							
						default:
							skip(reader);									
					}										
					break;				
				}
				case XMLStreamConstants.END_ELEMENT : {
					return list;
				}
				default: 
					break;
				}
			reader.next();
		}
		return list;
	}
	
	public static void write( XMLStreamWriter writer, Namespace namespace, List list) throws XMLStreamException {
		if (list == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + LIST : LIST);
		tribefire.extension.xml.schemed.model.xsd.QName simpleTypeReference = list.getSimpleTypeReference();
		if (simpleTypeReference != null) {
			writer.writeAttribute( ITEM_TYPE, QNameExpert.toString( simpleTypeReference));
		}
		writeAnyAttributes(writer, list.getAnyAttributes());
		
		for (GenericEntity ge : list.getNamedItemsSequence()) {
			if (ge instanceof Annotation) {
				AnnotationExpert.write(writer, namespace, (Annotation) ge);
			}
			else if (ge instanceof SimpleType) {
				SimpleTypeExpert.write(writer, namespace, (SimpleType) ge);
			}
			else {
				throw new IllegalStateException("unknown type [" + ge.getClass() + "] encountered");
			}
		}
		
		writer.writeEndElement();
	}
}
