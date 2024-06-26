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

import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

import com.braintribe.model.generic.GenericEntity;

import tribefire.extension.xml.schemed.marshaller.commons.QNameExpert;
import tribefire.extension.xml.schemed.model.xsd.Annotation;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.SimpleType;
import tribefire.extension.xml.schemed.model.xsd.Union;

public class UnionExpert extends AbstractSchemaExpert {

	public static Union read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		
		Union union = Union.T.create();
		attach(union, declaringSchema);
		
		Map<QName,String> attributes = readAttributes(reader);	
		String typesAsString = attributes.get( new QName( MEMBER_TYPES));
		if (typesAsString != null && typesAsString.length() > 0) {
			String []  types = typesAsString.split( " ");
			for (String type : types) {
				union.getSimpleTypeReferences().add(  QNameExpert.parse( type));					
			}
		}
		
		readAnyAttributes( union.getAnyAttributes(), attributes, ID, MEMBER_TYPES);
		
		reader.next();
		
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case SIMPLE_TYPE :
							SimpleType simpleType = SimpleTypeExpert.read( declaringSchema, reader);
							union.getSimpleTypes().add( simpleType);
							union.getNamedItemsSequence().add(simpleType);
							break;
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read(declaringSchema, reader);
							union.setAnnotation(annotation);
							union.getNamedItemsSequence().add(annotation);
							break;
						default:
						skip(reader);									
					}										
					break;				
				}
				case XMLStreamConstants.END_ELEMENT : {
					return union;
				}
				default: 
					break;
				}
			reader.next();
		}
		return union;
	}
	
	public static void write( XMLStreamWriter writer, Namespace namespace, Union union) throws XMLStreamException {
		if (union == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + UNION : UNION);
		
		StringBuilder builder = new StringBuilder();
		List<tribefire.extension.xml.schemed.model.xsd.QName> simpleTypeReferences = union.getSimpleTypeReferences();
		if (simpleTypeReferences.size() > 0) {
			for (tribefire.extension.xml.schemed.model.xsd.QName qname : simpleTypeReferences) {
				if (builder.length() > 0) {
					builder.append( " ");
				}
				builder.append( QNameExpert.toString(qname));
			}
			writer.writeAttribute( MEMBER_TYPES, builder.toString());
		}
		writeAnyAttributes(writer, union.getAnyAttributes());
		for (GenericEntity ge : union.getSimpleTypes()) {
			if (ge instanceof SimpleType) {
				SimpleTypeExpert.write(writer, namespace, (SimpleType) ge);				
			}
			else if (ge instanceof Annotation) {
				AnnotationExpert.write(writer, namespace, (Annotation) ge);
			}
			else {
				throw new IllegalStateException("unknown type [" + ge.getClass() + "] encountered");
			}
		}		
		writer.writeEndElement();
	}
}
