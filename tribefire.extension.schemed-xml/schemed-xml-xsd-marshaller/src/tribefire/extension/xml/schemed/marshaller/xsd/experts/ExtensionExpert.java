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

import tribefire.extension.xml.schemed.model.xsd.All;
import tribefire.extension.xml.schemed.model.xsd.Annotation;
import tribefire.extension.xml.schemed.model.xsd.Attribute;
import tribefire.extension.xml.schemed.model.xsd.AttributeGroup;
import tribefire.extension.xml.schemed.model.xsd.Choice;
import tribefire.extension.xml.schemed.model.xsd.Extension;
import tribefire.extension.xml.schemed.model.xsd.Group;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.Sequence;

public class ExtensionExpert extends AbstractSchemaExpert {

	public static Extension read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {
		
		Extension extension = Extension.T.create();
		attach(extension, declaringSchema);
		
		Map<QName, String> attributes = readAttributes(reader);		
		extension.setBase(attributes.get(new QName(BASE)));
		
		readAnyAttributes( extension.getAnyAttributes(), attributes, ID, BASE);
		
		reader.next();
		
		while (reader.hasNext()) {
			
			switch (reader.getEventType()) {
			
				case XMLStreamConstants.START_ELEMENT :												
					String tag = reader.getName().getLocalPart();
					switch (tag) {						
						case ALL:
							All all = AllExpert.read( declaringSchema, reader);
							extension.setAll( all);
							extension.getNamedItemsSequence().add( all);
							break;
						case GROUP:
							Group group2 = GroupExpert.read( declaringSchema, reader);
							extension.setGroup( group2);
							extension.getNamedItemsSequence().add( group2);
							break;
						case CHOICE:						
							Choice choice = ChoiceExpert.read( declaringSchema, reader);							
							extension.setChoice( choice);
							extension.getNamedItemsSequence().add( choice);
							break;
						case SEQUENCE:						
							Sequence sequence = SequenceExpert.read( declaringSchema, reader);
							extension.setSequence( sequence);							
							extension.getNamedItemsSequence().add( sequence);
							break;
						case ATTRIBUTE:
							Attribute read = AttributeExpert.read(declaringSchema, reader);
							extension.getAttributes().add( read);
							extension.getNamedItemsSequence().add( read);
							break;
						case ATTRIBUTE_GROUP:
							AttributeGroup attributeGroup = AttributeGroupExpert.read( declaringSchema, reader);
							extension.getAttributeGroups().add( attributeGroup);
							extension.getNamedItemsSequence().add( attributeGroup);
							break;
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read( declaringSchema, reader);
							extension.setAnnotation(annotation);
							extension.getNamedItemsSequence().add( annotation);
							break;
						case ANY:					
							default:
								skip(reader);
							break;
					}				
				break;
				
				case XMLStreamConstants.END_ELEMENT : {
					return extension;
				}		
			
				default: 
					break;
			}
			reader.next();
		}
		return extension;
	}

	
	public static void write(XMLStreamWriter writer, Namespace namespace, Extension extension) throws XMLStreamException{		
		if (extension == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + EXTENSION : EXTENSION);
		writer.writeAttribute(BASE, extension.getBase());
		// 
	
		for (GenericEntity ge : extension.getNamedItemsSequence()) {
			if (ge instanceof Sequence) {
				SequenceExpert.write(writer, namespace, (Sequence) ge);				
			}
			else if (ge instanceof Choice) {
				ChoiceExpert.write(writer, namespace, (Choice) ge);				
			}
			else if (ge instanceof All) {
				AllExpert.write(writer, namespace, (All) ge);
			}
			else if (ge instanceof Group) {
				GroupExpert.write(writer, namespace, (Group) ge);
			}			
			else if (ge instanceof Attribute) {
				AttributeExpert.write(writer, namespace, (Attribute) ge);				
			}
			else if (ge instanceof AttributeGroup) {
				AttributeGroupExpert.write(writer, namespace, (AttributeGroup) ge);								
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
