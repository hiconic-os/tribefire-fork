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

import tribefire.extension.xml.schemed.model.xsd.Annotation;
import tribefire.extension.xml.schemed.model.xsd.ComplexContent;
import tribefire.extension.xml.schemed.model.xsd.ComplexContentRestriction;
import tribefire.extension.xml.schemed.model.xsd.Extension;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;

public class ComplexContentExpert extends AbstractSchemaExpert {
	
	public static ComplexContent read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		ComplexContent complexContent = ComplexContent.T.create();
		attach(complexContent, declaringSchema);
	
		Map<QName, String> attributes = readAttributes( reader);
		
		complexContent.setId( attributes.get( new QName(ID)));
		String mixedAsString = attributes.get( new QName( MIXED));
		if (mixedAsString != null) {
			complexContent.setMixed( Boolean.parseBoolean(mixedAsString));
			complexContent.setMixedSpecified(true);
			
		}
		readAnyAttributes( complexContent.getAnyAttributes(), attributes, ID, MIXED);
						
		reader.next();
		while (reader.hasNext()) {
			switch (reader.getEventType()) {
				case XMLStreamConstants.START_ELEMENT : {
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case RESTRICTION:
							ComplexContentRestriction contentRestriction = ComplexContentRestrictionExpert.read(declaringSchema, reader);
							complexContent.setRestriction( contentRestriction);
							complexContent.getNamedItemsSequence().add( contentRestriction);
							break;							
						case EXTENSION:
							Extension contentExtension = ExtensionExpert.read(declaringSchema, reader);
							complexContent.setExtension( contentExtension);
							complexContent.getNamedItemsSequence().add( contentExtension);
							break;
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read(declaringSchema, reader);
							complexContent.setAnnotation(annotation);
							complexContent.getNamedItemsSequence().add( annotation);
							break;
						default:
							skip(reader);
					}
				}
				break;
				case XMLStreamConstants.END_ELEMENT : {
					return complexContent;
				}
				default: 
					break;
				}
			reader.next();
		}
		return complexContent;
	}

	public static void write( XMLStreamWriter writer, Namespace namespace, ComplexContent suspect) throws XMLStreamException {
		if (suspect == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + COMPLEX_CONTENT : COMPLEX_CONTENT);
		
		if (suspect.getMixed() || suspect.getMixedSpecified()) {
			writer.writeAttribute( MIXED, Boolean.toString( suspect.getMixed()));
		}
		
		writeAnyAttributes(writer, suspect.getAnyAttributes());
		
		for (GenericEntity ge : suspect.getNamedItemsSequence()) {
			if (ge instanceof ComplexContentRestriction) {
				ComplexContentRestrictionExpert.write(writer, namespace, (ComplexContentRestriction) ge);			
			}
			else if (ge instanceof Extension) {
				ExtensionExpert.write(writer, namespace, (Extension) ge);				
			}
			else {
				throw new IllegalStateException("unknown type [" + ge.getClass() + "] encountered");
			}
		}
		writer.writeEndElement();
	}
}
