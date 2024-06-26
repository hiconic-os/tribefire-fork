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
import tribefire.extension.xml.schemed.model.xsd.Choice;
import tribefire.extension.xml.schemed.model.xsd.Element;
import tribefire.extension.xml.schemed.model.xsd.Group;
import tribefire.extension.xml.schemed.model.xsd.Namespace;
import tribefire.extension.xml.schemed.model.xsd.Schema;
import tribefire.extension.xml.schemed.model.xsd.Sequence;

public class ChoiceExpert extends AbstractSchemaExpert {
	
	public static Choice read( Schema declaringSchema, XMLStreamReader reader) throws XMLStreamException {	
		Choice choice = Choice.T.create();
		attach(choice, declaringSchema);
	
		Map<QName, String> attributes = readAttributes(reader);		
		
		String value = attributes.get( new QName(MIN_OCCURS));
		if (value != null) {
			choice.setMinOccurs(Integer.valueOf(value));
			choice.setMinOccursSpecified(true);
		}
	
		value = attributes.get( new QName(MAX_OCCURS));
		if (value != null) {
			if (value.equalsIgnoreCase( UNBOUNDED)) {
				choice.setMaxOccurs(-1);
				choice.setMaxOccursSpecified(true);
			}
			else {
			choice.setMaxOccurs(Integer.valueOf(value));
			}
		}
				
		reader.next();
		
		while (reader.hasNext()) {
			
			switch (reader.getEventType()) {
			
				case XMLStreamConstants.START_ELEMENT :												
					String tag = reader.getName().getLocalPart();
					switch (tag) {
						case ELEMENT:	
							Element element = ElementExpert.read( declaringSchema, reader);
							choice.getElements().add( element);		
							choice.getNamedItemsSequence().add( element);
							break;						
						case GROUP:
							Group group = GroupExpert.read( declaringSchema, reader);
							choice.getGroups().add( group);
							choice.getNamedItemsSequence().add( group);
							break;
						case CHOICE:							
							Choice choice2 = ChoiceExpert.read( declaringSchema, reader);
							choice.getChoices().add(choice2);							
							choice.getNamedItemsSequence().add( choice2);
							break;
						case SEQUENCE:							
						Sequence sequence2 = SequenceExpert.read( declaringSchema, reader);
							choice.getSequences().add(sequence2);
							choice.getNamedItemsSequence().add( sequence2);
							break;
						case ANNOTATION:
							Annotation annotation = AnnotationExpert.read( declaringSchema, reader);
							choice.setAnnotation(annotation);				
							choice.getNamedItemsSequence().add( annotation);
							break;
						case ANY:					
							default:
								skip(reader);
							break;
					}				
				break;				
				case XMLStreamConstants.END_ELEMENT : {
					return choice;
				}		
			
				default: 
					break;
			}
			reader.next();
		}
	
		return choice;
	}

	
	public static void write(XMLStreamWriter writer, Namespace namespace, Choice choice) throws XMLStreamException{
		if (choice == null)
			return;
		String prefix = namespace.getPrefix();
		writer.writeStartElement( prefix != null ? prefix + ":" + CHOICE : CHOICE);
		
		// write attributes
		int min = choice.getMinOccurs();
		if (min != 1 || choice.getMinOccursSpecified()) {
			writer.writeAttribute( MIN_OCCURS, "" + min);
		}
		int max = choice.getMaxOccurs();
		if (max < 0 ) {
			writer.writeAttribute( MAX_OCCURS, UNBOUNDED);
		}
		else if (max != 1 || choice.getMaxOccursSpecified()) {
			writer.writeAttribute( MAX_OCCURS, "" + max);
		}
		
		for (GenericEntity ge : choice.getNamedItemsSequence()) {
			if (ge instanceof Element) {
				ElementExpert.write(writer, namespace, (Element) ge);				
			}
			else if (ge instanceof Choice) {			
				ChoiceExpert.write(writer, namespace, (Choice) ge);
			}
			else if (ge instanceof Sequence) {
				SequenceExpert.write(writer, namespace, (Sequence) ge);				
			}
			else if (ge instanceof All) {
				AllExpert.write(writer, namespace, (All) ge);
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
